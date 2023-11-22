package com.ssafy.enjoytrip.model;


import lombok.Getter;


@Getter
public class PhotoArticleDto {

	private int photoArticleId;
	
	private long likesCount;

	private String userId;

	private String content;

	private String createdTime;

	private String saveFolder;

	private String originalFile;

	private String saveFile;

	public PhotoArticleDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public PhotoArticleDto(int photoArticleId, long likesCount, String userId, String content, String createdTime,
			String saveFolder, String originalFile, String saveFile) {
		super();
		this.photoArticleId = photoArticleId;
		this.likesCount = likesCount;
		this.userId = userId;
		this.content = content;
		this.createdTime = createdTime;
		this.saveFolder = saveFolder;
		this.originalFile = originalFile;
		this.saveFile = saveFile;
	}

	public void setPhotoArticleId(int photoArticleId) {
		this.photoArticleId = photoArticleId;
	}

	public void setLikesCount(long likes) {
		this.likesCount = likesCount;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setCreatedTime(String createdTime) {
		this.createdTime = createdTime;
	}

	public void setSaveFolder(String saveFolder) {
		this.saveFolder = saveFolder;
	}

	public void setOriginalFile(String originalFile) {
		this.originalFile = originalFile;
	}

	public void setSaveFile(String saveFile) {
		this.saveFile = saveFile;
	}

	@Override
	public String toString() {
		return "PhotoArticleDto [photoArticleId=" + photoArticleId + ", userId=" + userId + ", content=" + content
				+ ", createdTime=" + createdTime + ", saveFolder=" + saveFolder + ", originalFile=" + originalFile
				+ ", saveFile=" + saveFile + "]";
	}

}
