package com.funnygame.entity;

public class Song {

	private int id;
	private String name;
	private String singer;
	private String url;
	private String views;
	private String uploader;

	public Song() {
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

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
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
				+ ", url=" + url + ", views=" + views + ", uploader="
				+ uploader + "]";
	} 

}
