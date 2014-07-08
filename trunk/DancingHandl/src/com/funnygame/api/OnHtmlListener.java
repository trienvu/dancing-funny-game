package com.funnygame.api;

public interface OnHtmlListener {
	
	public int ON_COMPLETE = 1;
	public int ON_ERROR = 0;
	public int ON_START = 2;

	public <T> void onHandler(int code,T t);

}
