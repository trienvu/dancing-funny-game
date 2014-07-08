package com.funnygame.DancingHandl;

import java.util.List;

import com.funnygame.api.OnHtmlListener;
import com.funnygame.api.SongApi;
import com.funnygame.entity.Song;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends Activity implements OnHtmlListener {

	private Context mContext = this;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

//		SongApi.search("vi khi da yeu", mContext, this);
		
		SongApi.getLinkMp3("http://www.nhaccuatui.com/bai-hat/vi-khi-da-yeu-vu-duy-khanh.RyAuJUsF2l.html", null, new OnHtmlListener() {
			
			@Override
			public <T> void onHandler(int code, T t) {
				// TODO Auto-generated method stub
				
			}
		});

	}

	@Override
	public <T> void onHandler(int code, T t) {
		// TODO Auto-generated method stub
		switch (code) {
		case ON_COMPLETE:
			onComplete(code, t);
			break;

		case ON_ERROR:
			onError(code, t);
			break;

		case ON_START:
			onStart(code, t);
			break;
		}
	}

	private <T> void onComplete(int code, T t) {
		@SuppressWarnings("unchecked")
		List<Song> songs = (List<Song>) t;
		Toast.makeText(mContext, "Thành công, lấy được " + songs.size(),
				Toast.LENGTH_LONG).show();
	}

	private <T> void onError(int code, T t) {
		Toast.makeText(mContext, "Lỗi...	", Toast.LENGTH_LONG).show();
	}

	private <T> void onStart(int code, T t) {
		Toast.makeText(mContext, "Searching...	", Toast.LENGTH_LONG).show();
	}

}
