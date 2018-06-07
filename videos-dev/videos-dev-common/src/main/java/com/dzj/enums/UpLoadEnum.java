package com.dzj.enums;

public enum UpLoadEnum {
	VIDEO("video"),FACE("face");
	
	private String msg;

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	private UpLoadEnum(String msg) {
		this.msg = msg;
	}
	
	
	
}
