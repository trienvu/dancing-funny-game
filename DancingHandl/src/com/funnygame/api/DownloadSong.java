package com.funnygame.api;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

import com.funnygame.base.Constant;

import android.os.AsyncTask;
import android.util.Log;

public class DownloadSong extends AsyncTask<String, Integer, Boolean> {

	private OnHtmlListener onHtmlListener;

	public DownloadSong() {
	}

	public void setOnHtmlListener(OnHtmlListener onHtmlListener) {
		this.onHtmlListener = onHtmlListener;
	}

	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
		this.onHtmlListener.onHandler(OnHtmlListener.ON_START, null);
	}

	@Override
	protected Boolean doInBackground(String... params) {
		// TODO Auto-generated method stub
		try {

			URL url = new URL(params[0]);
			URLConnection conexion = url.openConnection();
			conexion.connect();

			int lenghtOfFile = conexion.getContentLength();
			Log.d("ANDRO_ASYNC", "Lenght of file: " + lenghtOfFile);

			InputStream input = new BufferedInputStream(url.openStream());

			File file = new File(Constant.BASE_DIR);

			if (!file.exists()) {
				file.mkdir();
			}

			OutputStream output = new FileOutputStream(file.getAbsolutePath()
					+ File.separator + params[1]);

			byte data[] = new byte[1024];

			long total = 0;

			int count;

			while ((count = input.read(data)) != -1) {
				total += count;
				publishProgress((int) ((total * 100) / lenghtOfFile));
				output.write(data, 0, count);
			}

			output.flush();
			output.close();
			input.close();

			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	protected void onProgressUpdate(Integer... values) {
		// TODO Auto-generated method stub
		super.onProgressUpdate(values);
		this.onHtmlListener.onHandler(OnHtmlListener.ON_UPDATE, values[0]);
	}

	@Override
	protected void onPostExecute(Boolean result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);

		if (result) {
			this.onHtmlListener.onHandler(OnHtmlListener.ON_COMPLETE, null);
		} else {
			this.onHtmlListener.onHandler(OnHtmlListener.ON_ERROR, null);
		}
	}
}