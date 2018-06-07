package com.dzj.enums;

public enum VideoEnum {
	SUCCESS(1),ILLEGAL(2);
	
	private Integer status;

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	private VideoEnum(Integer status) {
		this.status = status;
	}

	
	
	
	
}
