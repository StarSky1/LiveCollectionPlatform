package org.lf.utils;

/**
 * ajax请求返回值模型
 *
 */
public class AjaxResultModel {
	private int code;// 状态码
	private String msg;// 消息
	private Object data;// 数据

	public AjaxResultModel() {
		super();
	}

	public AjaxResultModel(int code, String msg, Object data) {
		this.code = code;
		this.msg = msg;
		this.data = data;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg == null ? null : msg.trim();
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

}
