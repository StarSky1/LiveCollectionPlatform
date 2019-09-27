package com.yj.service;

import com.yj.dao.UserMapper;
import com.yj.pojo.User;
import org.lf.utils.BaseProperties;
import org.lf.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import static com.yj.logger.Log4JConfigure.LOGERROR;
import static com.yj.logger.Log4JConfigure.LOGINFO;

@Service
public class UserService {
	@Autowired
	public UserMapper userMapper;
	private String salt="1314";
	
	public User getUserByUserName(String username){
		return userMapper.selectByUsername(username);
	}
	
	public User getUserById(int userId){
		return userMapper.selectByPrimaryKey(userId);
	}
	
	public boolean validateUser(String username,String password){
		User user=getUserByUserName(username);
		if(user==null) return false;
		if(StringUtils.toMD5(password+salt).equals(user.getUserPassword())){
			return true;
		}
		return false;
	}
	
	public boolean addUser(String username,String password,String phone,String email){
		User user=new User();
		user.setUserName(username);
		user.setUserNickname(username);
		user.setUserResume("这个人很懒，什么都没有留下。");
		user.setUserVideoLevel(1);
		user.setUserHeadImg("default.jpg");
		user.setUserPassword(StringUtils.toMD5(password+salt));
		user.setUserPhone(phone);
		user.setUserEmail(email);
		try {
			userMapper.save(user);
			return true;
		}catch (Exception e){
			LOGINFO.error("addUser error",e);
			LOGERROR.error("addUser error",e);
		}
		return false;
	}
	
	public boolean updatePassword(int userId,String password){
		User user=new User();
		user.setUserId(userId);
		user.setUserPassword(StringUtils.toMD5(password+salt));
		return updateUser(user);
	}
	
	public boolean updateLevel(int userId,int level){
		User user=new User();
		user.setUserId(userId);
		user.setUserVideoLevel(level);
		return updateUser(user);
	}
	
	public boolean updateUser(User user){
		try{
			userMapper.save(user);
			return true;
		}catch (Exception e){
			LOGINFO.error("updateUser error",e);
			LOGERROR.error("updateUser error",e);
		}
		return false;
	}
	
	public String uploadUserImg(int userId,MultipartFile file,String suffix,HttpSession session) {
		String root=session.getServletContext().getRealPath("/");
		System.out.println(root);
		User user=userMapper.selectByPrimaryKey(userId);
		String filename=user.getUserName()+suffix;
		File dest=new File(root+"/res/user_head_img",filename);
		File dest1=new File(BaseProperties.getProperty("userImgDir"),filename);
		try {
			file.transferTo(dest);
			copyFile(dest, dest1);
		} catch (IllegalStateException | IOException e) {
			e.printStackTrace();
			return "error";
		}
		user.setUserHeadImg(filename);
		session.setAttribute("currentUser", user);
		return filename;
	}
	
	public void copyFile(File fromFile,File toFile) throws IOException{
        FileInputStream ins = new FileInputStream(fromFile);
        FileOutputStream out = new FileOutputStream(toFile);
        byte[] b = new byte[1024];
        int n=0;
        while((n=ins.read(b))!=-1){
            out.write(b, 0, n);
        }
        
        ins.close();
        out.close();
    }
	
	public boolean validateUsername(String username){
		User user=userMapper.selectByUsername(username);
		if(user!=null) return false;
		else return true;
	}
	
	public boolean validatePhone(String phone){
		User user=userMapper.selectByPhone(phone);
		if(user!=null) return false;
		else return true;
	}
	
	public boolean validateEmail(String email){
		User user=userMapper.selectByEmail(email);
		if(user!=null) return false;
		else return true;
	}
	
}
