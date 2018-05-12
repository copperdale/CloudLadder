package com.studio.cloudelevator.task;

import static android.text.format.DateUtils.SECOND_IN_MILLIS;
import static java.net.HttpURLConnection.HTTP_MOVED_PERM;
import static java.net.HttpURLConnection.HTTP_MOVED_TEMP;
import static java.net.HttpURLConnection.HTTP_OK;
import static java.net.HttpURLConnection.HTTP_PARTIAL;
import static java.net.HttpURLConnection.HTTP_SEE_OTHER;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.HttpResponseException;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.studio.io.FileSystemUtils;
import android.studio.os.LogCat;

import com.github.kevinsawicki.http.HttpRequest;
import com.studio.cloudelevator.MyApplication;
import com.studio.cloudelevator.config.AppConfig;
import com.studio.cloudelevator.entity.DownloadInfo;
import com.studio.cloudelevator.entity.InitConfig;
import com.studio.cloudelevator.entity.Media;

public class MediaDownloadService_0715 extends IntentService {

	static final int timeoutMs = 20 * 1000;
	private static boolean isDownloading = false;

	private static final int DEFAULT_TIMEOUT = (int) (10 * SECOND_IN_MILLIS);
	public static final int MAX_REDIRECTS = 5;
	public static final int BUFFER_SIZE = 8 * 1024;
	private long mLastUpdateBytes = 0;
	private long mLastUpdateTime = 0;

	private boolean syncImmediate = false;

	public static void start(Context context, boolean syncImmediate) {
		Intent intent = new Intent(context, MediaDownloadService_0715.class);
		intent.putExtra("syncImmediate", syncImmediate);
		context.startService(intent);
	}

	public MediaDownloadService_0715() {
		super("ServiceDownloader");
	}

	@Override
	protected void onHandleIntent(Intent i) {
		if (isDownloading) {
			LogCat.e("file downloading...");
			return;
		}

		try {
			isDownloading = true;
			syncImmediate = i.getBooleanExtra("syncImmediate", false);
			InitConfig initConfig = AppConfig.getInitConfig();
			if (initConfig.medias != null) {
				ArrayList<String> pathList = downloadMediaList(initConfig);
				clearOtherMedia(initConfig);
				if (syncImmediate) {
					sendMedia(pathList);
				}
			}

		} catch (Exception e) {
			LogCat.e("onHandleIntent : %s", e.toString());
		} finally {
			isDownloading = false;
		}
	}

	private void sendMedia(ArrayList<String> pathList) {
		Intent intent = new Intent(AppConfig.ACTION_MEDIAS);
		intent.putExtra("path", pathList);
		sendBroadcast(intent);
	}

	private ArrayList<String> downloadMediaList(InitConfig initConfig) throws Exception {
		ArrayList<String> results = new ArrayList<String>();
		for (Media media : initConfig.medias) {
			downloadMedia(results, media);
		}
		return results;
	}

	private void downloadMedia(ArrayList<String> results, Media media) throws Exception {
		DownloadInfo info = DownloadInfo.newInstance(media.url, new File(MyApplication.mediaDir, media.mediaId + AppConfig.MEDIA_SUFFIX).getPath());
		results.add(info.mFileName);
		try {
			if (info.exists()) {
				LogCat.e("file is exists : %s", info.mFileName);
				return;
			}

			syncImmediate = true;
			// downloadMedia(media.url, info);
			LogCat.i("download uri: %s", media.url);
			executeDownload(media.url, info);
			if (info.renameTo()) {
				LogCat.i("file download success: %s", info.mFileName);
			}
		} catch (Exception e) {
			info.getTempFile().delete();
			throw e;
		}
	}

	/**
	 * 清除不是发布的媒体资源
	 * 
	 * @param initConfig
	 */
	private void clearOtherMedia(InitConfig initConfig) {
		File[] medias = AppConfig.getMediaFileAll();
		if (medias != null) {
			for (File f : medias) {
				if (!containMedia(initConfig.medias, f.getName())) {
					syncImmediate = true;
					f.delete();
					LogCat.e("delete media %s", f.getPath());
				}
			}
		}
	}

	/**
	 * 判断是否存在
	 * 
	 * @param
	 * @param name
	 * @return
	 */
	private boolean containMedia(List<Media> medias, String name) {
		for (Media media : medias) {
			if (name.equals(media.mediaId + AppConfig.MEDIA_SUFFIX)) {
				return true;
			}
		}
		return false;
	}

	private void downloadMedia(String url, DownloadInfo info) throws IOException {
		LogCat.i("media download... %s", url);
		HttpRequest request = HttpRequest.get(url, true, "clientId", AppConfig.getUuid()).connectTimeout(timeoutMs).readTimeout(timeoutMs);
		if (!request.ok()) {
			throw new HttpResponseException(request.code(), "");
		}
		InputStream inputSteram = request.buffer();
		FileSystemUtils.copy(inputSteram, new BufferedOutputStream(new FileOutputStream(info.getTempFile())));
	}

	private void executeDownload(String uri, DownloadInfo info) throws Exception {
		final boolean resuming = info.mCurrentBytes > 0 && info.mCurrentBytes < info.mTotalBytes;

		URL url = new URL(uri);
		int redirectionCount = 0;
		while (redirectionCount++ < MAX_REDIRECTS) {
			// Open connection and follow any redirects until we have a useful
			// response with body.
			HttpURLConnection conn = null;
			try {
				conn = openConnection(url);
				addRequestHeaders(conn, resuming, info);

				final int responseCode = conn.getResponseCode();
				info.mStatus = responseCode;
				switch (responseCode) {
				case HTTP_OK:
					// 如果是恢复断点出错，那么直接重新开始下载
					if (resuming) {
						LogCat.e("恢复断点出错，转为重新下载...");
					}

					info.mCurrentBytes = 0;
					info.getTempFile().delete();

					parseOkHeaders(conn, info);
					transferData(conn, info);
					return;

				case HTTP_PARTIAL:
					if (!resuming) {
						// throw new
						// Exception("STATUS_CANNOT_RESUME : Expected OK, but received partial");
					}
					transferData(conn, info);
					return;

				case HTTP_MOVED_PERM:
				case HTTP_MOVED_TEMP:
				case HTTP_SEE_OTHER:
				case 307:
					final String location = conn.getHeaderField("Location");
					url = new URL(url, location);
					if (responseCode == HTTP_MOVED_PERM) {
						uri = url.toString();
					}
					continue;

				default:
					throw new Exception("STATUS_CODE: " + conn.getResponseMessage());
				}

			} catch (Exception e) {
				throw e;
			} finally {
				if (conn != null) {
					conn.disconnect();
				}
			}
		}

		throw new Exception("STATUS_TOO_MANY_REDIRECTS : Too many redirects");
	}

	private HttpURLConnection openConnection(URL url) throws IOException {
		HttpURLConnection conn;
		conn = (HttpURLConnection) url.openConnection();
		conn.setInstanceFollowRedirects(false);
		conn.setConnectTimeout(DEFAULT_TIMEOUT);
		conn.setReadTimeout(DEFAULT_TIMEOUT);
		// conn.setUseCaches(false);
		// conn.setDoInput(true);
		return conn;
	}

	/**
	 * Transfer data from the given connection to the destination file.
	 */
	private void transferData(HttpURLConnection conn, DownloadInfo info) throws Exception {
		InputStream in = null;
		RandomAccessFile out = null;
		try {
			in = conn.getInputStream();
			out = new RandomAccessFile(info.getTempFile(), "rw");
			out.seek(info.mCurrentBytes);
			transferData(in, out, info);
			// out.flush();
		} catch (IOException e) {
			throw new Exception("STATUS_HTTP_DATA_ERROR", e);
		} finally {
			try {
				in.close();
			} catch (IOException e) {
			}
			try {
				out.close();
			} catch (IOException e) {
			}
		}
	}

	/**
	 * Transfer as much data as possible from the HTTP response to the
	 * destination file.
	 */
	private void transferData(InputStream in, RandomAccessFile out, DownloadInfo info) throws Exception {
		final byte buffer[] = new byte[BUFFER_SIZE];
		while (true) {
			int bytesRead = in.read(buffer);
			if (bytesRead == -1) {
				updateProgress(info, true);
				// Finished without error; verify length if known
				if (info.mTotalBytes != -1 && info.mCurrentBytes != info.mTotalBytes) {
					throw new Exception("STATUS_HTTP_DATA_ERROR : Content length mismatch");
				}
				return;
			}

			out.write(buffer, 0, bytesRead);
			// mMadeProgress = true;
			info.mCurrentBytes += bytesRead;
			updateProgress(info, false);
		}
	}

	public static final int MIN_PROGRESS_STEP = 64 * 1024;

	public static final long MIN_PROGRESS_TIME = 500;

	private void updateProgress(DownloadInfo info, boolean immediate) throws IOException, Exception {
		final long now = SystemClock.elapsedRealtime();
		final long currentBytes = info.mCurrentBytes;
		final long bytesDelta = currentBytes - mLastUpdateBytes;
		final long timeDelta = now - mLastUpdateTime;
		if (immediate || (bytesDelta > MIN_PROGRESS_STEP && timeDelta > MIN_PROGRESS_TIME)) {
			LogCat.i("updateProgress [%d%%]", info.getProgressPercent());
			info.writeToDatabase();
			//AppConfig.setPrefBreakpoint(info.mCurrentBytes > 0 && info.mCurrentBytes != info.mTotalBytes);
			mLastUpdateBytes = currentBytes;
			mLastUpdateTime = now;
		}
	}

	/**
	 * Process response headers from first server response. This derives its
	 * filename, size, and ETag.
	 * 
	 * @param info
	 */
	private void parseOkHeaders(HttpURLConnection conn, DownloadInfo info) throws Exception {
		final String transferEncoding = conn.getHeaderField("Transfer-Encoding");
		if (transferEncoding == null) {
			info.mTotalBytes = getHeaderFieldLong(conn, "Content-Length", -1);
		}

		info.mETag = conn.getHeaderField("ETag");
		info.writeToDatabase();
	}

	/**
	 * Add custom headers for this download to the HTTP request.
	 */
	private void addRequestHeaders(HttpURLConnection conn, boolean resuming, DownloadInfo info) {
		/*
		 * for (Pair<String, String> header : mInfo.getHeaders()) {
		 * conn.addRequestProperty(header.first, header.second); }
		 */

		if (conn.getRequestProperty("User-Agent") == null) {
			conn.addRequestProperty("User-Agent", "WebAndroid");
		}

		conn.setRequestProperty("Accept-Encoding", "identity");
		conn.setRequestProperty("Connection", "close");
		if (resuming) {
			if (info.mETag != null) {
				conn.addRequestProperty("If-Match", info.mETag);
			}
			conn.addRequestProperty("Range", "bytes=" + info.mCurrentBytes + "-");
		}
	}

	private static long getHeaderFieldLong(URLConnection conn, String field, long defaultValue) {
		try {
			return Long.parseLong(conn.getHeaderField(field));
		} catch (NumberFormatException e) {
			return defaultValue;
		}
	}
}
