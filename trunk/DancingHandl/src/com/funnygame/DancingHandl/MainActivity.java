package com.funnygame.DancingHandl;

import java.util.List;

import com.funnygame.api.OnHtmlListener;
import com.funnygame.api.SongApi;
import com.funnygame.entity.Song;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.widget.ProgressBar;
import android.widget.Toast;

public class MainActivity extends Activity implements OnHtmlListener {

	private Context mContext = this;

	private ProgressDialog mProgressBar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mProgressBar = new ProgressDialog(mContext);
		mProgressBar.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);


		// SongApi.search("vi khi da yeu", mContext, this);

		// SongApi.getLinkMp3("http://www.nhaccuatui.com/bai-hat/vi-khi-da-yeu-vu-duy-khanh.RyAuJUsF2l.html",
		// null, new OnHtmlListener() {
		//
		// @Override
		// public <T> void onHandler(int code, T t) {
		// // TODO Auto-generated method stub
		//
		// }
		// });

		// List<Song> songs = SongApi.getAllSongFromSdCard(mContext);
		// Toast.makeText(mContext, songs.size()+ "", Toast.LENGTH_LONG).show();

		//DemoDownload file
		OnHtmlListener handerDownloadFile = new OnHtmlListener() {

			@Override
			public <T> void onHandler(int code, T t) {
				// TODO Auto-generated method stub
				switch (code) {
				case ON_ERROR:
					mProgressBar.dismiss();
					Toast.makeText(mContext, "Download error",
							Toast.LENGTH_LONG).show();
					break;

				case ON_START:
					mProgressBar.show();
					break;

				case ON_COMPLETE:
					mProgressBar.dismiss() ;
					Toast.makeText(mContext, "Download susscess",
							Toast.LENGTH_LONG).show();
					break;

				case ON_UPDATE:
					Integer process = (Integer) t;
					mProgressBar.setProgress(process);

					break;

				default:
					break;
				}
			}
		};
		
		SongApi.downloadFile(
				"http://farm1.static.flickr.com/114/298125983_0e4bf66782_b.jpg",
				"/sdcard/some_photo_from_gdansk_poland.jpg",
				handerDownloadFile);

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