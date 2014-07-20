package com.funnygame.DancingHandl;

import java.io.IOException;
import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.funnygame.api.OnHtmlListener;
import com.funnygame.api.SongApi;
import com.funnygame.base.Constant;
import com.funnygame.entity.Song;

public class MainActivity extends Activity implements OnHtmlListener,
		MediaPlayer.OnPreparedListener {

	private Context mContext = this;
	private MediaPlayer mPlayer;
	private ProgressDialog mProgressBar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mPlayer = new MediaPlayer();
		mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
		mPlayer.setOnPreparedListener(this);

		mProgressBar = new ProgressDialog(mContext);
		mProgressBar.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		
		
		mPlayer = new MediaPlayer();
		mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
		mPlayer.setOnPreparedListener(this);
		String pathUri = "android.resource://" + getPackageName() + "/"+R.raw.nuhoncuoicung;
		playAudio(Uri.parse(pathUri));
		
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

		// DemoDownload file
		// OnHtmlListener handerDownloadFile = new OnHtmlListener() {
		//
		// @Override
		// public <T> void onHandler(int code, T t) {
		// // TODO Auto-generated method stub
		// switch (code) {
		// case ON_ERROR:
		// mProgressBar.dismiss();
		// Toast.makeText(mContext, "Download error",
		// Toast.LENGTH_LONG).show();
		// break;
		//
		// case ON_START:
		// mProgressBar.show();
		// break;
		//
		// case ON_COMPLETE:
		// mProgressBar.dismiss();
		// Toast.makeText(mContext, "Download susscess",
		// Toast.LENGTH_LONG).show();
		// break;
		//
		// case ON_UPDATE:
		// Integer process = (Integer) t;
		// mProgressBar.setProgress(process);
		//
		// break;
		//
		// default:
		// break;
		// }
		// }
		// };

		// SongApi.downloadFile(
		// "http://mp3.zing.vn/xml/load-song/MjAxNCUyRjA0JTJGMjQlMkYzJTJGNyUyRjM3NzZkMDg1ZWQxYjcwMjNhYzI4YjFlYzNjYWY5N2E5Lm1wMyU3QzI=",
		// "hihi.mp3", null);

		int channels = SongApi.getSongInfo(mContext, Constant.BASE_DIR
				+ "hihi.mp3");

		Log.d("channels", channels + "");

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

	private void playAudio(Uri uri) {
		if (mPlayer.isPlaying())
			mPlayer.stop();
		mPlayer.reset();
		try {
			mPlayer.setDataSource(getApplicationContext(), uri);
			mPlayer.prepare();

		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void onPrepared(MediaPlayer mp) {
		if (!mp.isPlaying())
			mp.start();
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	protected void onStop() {
		super.onStop();
		if (mPlayer != null) {
			if (mPlayer.isPlaying()) {
				mPlayer.stop();
			}
			mPlayer.release();
		}
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (mPlayer != null) mPlayer.release();

	}
}
