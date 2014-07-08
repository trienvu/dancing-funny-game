package com.funnygame.api;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore.Audio;
import android.util.Log;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.funnygame.entity.Song;
import com.funnygame.util.HttpUtil;
import com.funnygame.util.RegexUtil;

public class SongApi {

	// tag for parsing search
	private static final String BASE_URL = "http://www.nhaccuatui.com/tim-kiem/bai-hat?q=";

	private static final String TAG_LIST_SONG = "li[class=list_song]";

	private static final String TAG_NAME_SONG = "a[class=name_song]";

	private static final String TAG_TITLE = "title";

	private static final String TAG_HREF = "href";

	private static final String TAG_NAME_SINGER = "a[class=name_singer]";

	private static final String TAG_ICON_VIEWS = "span[class=icon_listen]";

	private static final String TAG_ICON_UPLOADER = "span[class=icon_user_upload]";

	/**
	 * Tìm nhạc tại nhạc của tui
	 * 
	 * @param key
	 *            Từ khóa tìm kiếm
	 * @param context
	 *            context
	 * @param htmlListener
	 *            delegate
	 */
	@SuppressWarnings("deprecation")
	public final static void search(String key, Context context,
			final OnHtmlListener htmlListener) {
		AQuery aQuery = new AQuery(context);

		AjaxCallback<String> ajaxCallback = new AjaxCallback<String>() {
			@Override
			public void async(Context context) {
				// TODO Auto-generated method stub
				super.async(context);
				htmlListener.onHandler(OnHtmlListener.ON_START, null);
			}

			@Override
			public void callback(String url, String object, AjaxStatus status) {
				// TODO Auto-generated method stub
				super.callback(url, object, status);

				try {
					if (null == object) {
						htmlListener.onHandler(OnHtmlListener.ON_ERROR, null);
					} else {

						List<Song> songs = new ArrayList<Song>();

						Document document = Jsoup.parse(object);

						Elements elements = document.select(TAG_LIST_SONG);

						if (null != elements) {
							for (int i = 0; i < elements.size(); ++i) {
								Element element = elements.get(i);

								String name = element.select(TAG_NAME_SONG)
										.first().attr(TAG_TITLE);
								String link = element.select(TAG_NAME_SONG)
										.first().attr(TAG_HREF);
								String nameSinger = element
										.select(TAG_NAME_SINGER).first().text();
								String views = element.select(TAG_ICON_VIEWS)
										.first().text();
								String uploader = element
										.select(TAG_ICON_UPLOADER).first()
										.text();

								Song song = new Song();
								song.setId(i);
								song.setType(Song.SONG_NCT);
								song.setName(name);
								song.setPath(link);
								song.setSinger(nameSinger);
								song.setUploader(uploader);
								song.setViews(views);

								songs.add(song);

								Log.d("song", song.toString());
							}
						}

						htmlListener.onHandler(OnHtmlListener.ON_COMPLETE,
								songs);
						Log.d("num tag", elements.size() + "");
					}
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
					htmlListener.onHandler(OnHtmlListener.ON_ERROR, null);
				}

			}
		};

		aQuery.ajax(BASE_URL + URLEncoder.encode(key), String.class,
				ajaxCallback);
	}

	public final static void getLinkMp3(String url, Context context,
			final OnHtmlListener htmlListener) {
		new AsyncTask<String, String, String>() {

			protected void onPreExecute() {
				htmlListener.onHandler(OnHtmlListener.ON_START, null);
			};

			@Override
			protected String doInBackground(String... params) {
				// TODO Auto-generated method stub
				String path = params[0];

				try {
					String content = HttpUtil.getContentByUrl(path);

					if (null != content) {
						// (?<=beginningstringname)(.*\n?)(?=endstringname)
						String url_xml = RegexUtil.find(content,
								"(?<=file=)[\\w\\d/:.=?]+(?=\")");

						if (null != url_xml && !"".equals(url_xml)) {
							String xml = HttpUtil.getContentByUrl(url_xml);

							Document document = Jsoup.parse(xml);

							String linkMp3 = document.select("location")
									.first().text();

							Log.d("linkMp3", linkMp3);

							return linkMp3;
						}
					}
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}

				return null;
			}

			protected void onPostExecute(String result) {
				if (null == result) {
					htmlListener.onHandler(OnHtmlListener.ON_ERROR, null);
				} else {
					htmlListener.onHandler(OnHtmlListener.ON_COMPLETE, result);
				}
			};

		}.execute(url);
	}

	public static final List<Song> getAllSongFromSdCard(Context context) {
		List<Song> songs = new ArrayList<Song>();

		// retrieve song info
		ContentResolver musicResolver = context.getContentResolver();

		Uri musicUri = android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;

		Cursor musicCursor = musicResolver.query(musicUri, null, null, null,
				null);

		if (musicCursor != null && musicCursor.moveToFirst()
				&& musicCursor.getCount() > 0) {
			// get columns
			int titleColumn = musicCursor
					.getColumnIndex(android.provider.MediaStore.Audio.Media.TITLE);
//			int idColumn = musicCursor
			// .getColumnIndex(android.provider.MediaStore.Audio.Media._ID);
//			int albumColumn = musicCursor
//					.getColumnIndex(android.provider.MediaStore.Audio.Media.ALBUM);
			int artistColumn = musicCursor
					.getColumnIndex(android.provider.MediaStore.Audio.Media.ARTIST);
//			int durationColumn = musicCursor
//					.getColumnIndex(android.provider.MediaStore.Audio.Media.DURATION);
			int pathColumn = musicCursor.getColumnIndex(Audio.Media.DATA);

			// add songs to list
			while (musicCursor.moveToNext()) {
				// long thisId = musicCursor.getLong(idColumn);
				String thisTitle = musicCursor.getString(titleColumn);
				// int thisDuration = musicCursor.getInt(durationColumn);
				// String thisAlbum = musicCursor.getString(albumColumn);
				String thisArtist = musicCursor.getString(artistColumn);
				String thisPath = musicCursor.getString(pathColumn);

				Song song = new Song();
				song.setName(thisTitle);
				song.setPath(thisPath);
				song.setSinger(thisArtist);
				song.setType(Song.SONG_SDCARD);

				songs.add(song);
			}

		}
		musicCursor.close();

		return songs;

	}

}
