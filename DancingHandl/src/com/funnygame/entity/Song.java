package com.funnygame.entity;

public class Song {

	public static final int SONG_SDCARD = 0;
	public static final int SONG_NCT = 1;

	private int id;
	private String name;
	private String singer;
	private String path;
	private String views;
	private String uploader;

	private int type;

	public Song() {
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getViews() {
		return views;
	}

	public void setViews(String views) {
		this.views = views;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSinger() {
		return singer;
	}

	public void setSinger(String singer) {
		this.singer = singer;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getUploader() {
		return uploader;
	}

	public void setUploader(String uploader) {
		this.uploader = uploader;
	}

	@Override
	public String toString() {
		return "Song [id=" + id + ", name=" + name + ", singer=" + singer
				+ ", path=" + path + ", views=" + views + ", uploader="
				+ uploader + ", type=" + type + "]";
	}

}
