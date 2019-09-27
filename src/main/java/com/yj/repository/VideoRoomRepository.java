package com.yj.repository;

import com.yj.pojo.Video_room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface VideoRoomRepository extends JpaRepository<Video_room,String> {

    @Query(nativeQuery = true,value = "select * from `video_room` where 1=1 "+
            "and IF (?1 != '', `video_title` like concat('%',?1,'%') or `video_host_nickname` like concat('%',?1,'%'), 1=1) " +
            "and IF (?2 != '', `video_type`=?2, 1=1) " +
            "and IF (?3 != '', `video_platform`=?3, 1=1) " +
            "and `video_status`=1 " +
            "order by `video_number` desc " +
            "limit ?4,?5 "
    )
    List<Video_room> getVideoRoomList(String searchWord,String platform,String type,int start,int offset);

    @Query(nativeQuery = true,value = "select count(*) from `video_room` where 1=1 "+
            "and IF (?1 != '', `video_title` like concat('%',?1,'%') or `video_host_nickname` like concat('%',?1,'%'), 1=1) " +
            "and IF (?2 != '', `video_type`=?2, 1=1) " +
            "and IF (?3 != '', `video_platform`=?3, 1=1) " +
            "and `video_status`=1 "
    )
    int countVideoRoomList(String searchWord,String platform,String type);

    @Query(value = "SELECT b.* FROM `video_care` a,`video_room` b where a.userId=?1 and a.videoId=b.`video_id` " +
            "   order by b.`video_number` desc",nativeQuery = true)
    List<Video_room> selectVideoSourceByUserId(int userId);

}
