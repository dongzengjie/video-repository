package com.dzj.pojo.vo;

import com.dzj.pojo.Comments;

public class CommentVo extends Comments {

    private String faceImage;
    private String nickname;
    private String toNickname;
    private String timeAgoStr;
	public String getFaceImage() {
		return faceImage;
	}
	public void setFaceImage(String faceImage) {
		this.faceImage = faceImage;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getToNickname() {
		return toNickname;
	}
	public void setToNickname(String toNickname) {
		this.toNickname = toNickname;
	}
	public String getTimeAgoStr() {
		return timeAgoStr;
	}
	public void setTimeAgoStr(String timeAgoStr) {
		this.timeAgoStr = timeAgoStr;
	}
    
    
}
