package com.studio.cloudelevator.entity;

import java.io.File;
import java.net.HttpURLConnection;

import android.studio.os.PreferencesUtils;

import com.google.gson.GsonUtils;
import com.studio.cloudelevator.config.AppConfig;

public class DownloadInfo {

	static final String URI_RANGE_PREF = "uri:range";
	public String mUri;
	public String mFileName;
	public int mStatus;
	public long mTotalBytes;
	public long mCurrentBytes;
	public String mETag;

	private DownloadInfo(String uri, String filepath) {
		mUri = uri;
		mFileName = filepath;
		mStatus = HttpURLConnection.HTTP_OK;
	}

	public static DownloadInfo newInstance(String uri, String filepath) {
		DownloadInfo result = new DownloadInfo(uri, filepath);

		// 只有临时文件存在，才可以断点
		if (result.getTempFile().exists()) {
			String jsonDownloadInfo = PreferencesUtils.getString(URI_RANGE_PREF, null);
			DownloadInfo delta = GsonUtils.jsonDeserializer(jsonDownloadInfo, DownloadInfo.class);
			if (delta != null && filepath.equals(delta.mFileName)) {
				result.mTotalBytes = delta.mTotalBytes;
				result.mCurrentBytes = delta.mCurrentBytes;
				result.mETag = delta.mETag;
			}
		}

		return result;
	}

	public File getTempFile() {
		File file = new File(mFileName);
		return new File(file.getParent(), file.getName() + AppConfig.MEDIA_TEMP_SUFFIX);
	}

	public boolean exists() {
		return new File(mFileName).exists();
	}

	public boolean renameTo() {
		return getTempFile().renameTo(new File(mFileName));
	}

	public int getProgressPercent() {
		return (int) (mCurrentBytes / (double) mTotalBytes * 100);
	}

	public boolean isSuccess() {
		return mTotalBytes != -1 && mCurrentBytes != mTotalBytes;
	}

	private String buildContentValues() {
		return GsonUtils.jsonSerializer(this);
	}

	public void writeToDatabase() {
		PreferencesUtils.setString(URI_RANGE_PREF, buildContentValues());
		// AppConfig.setPrefBreakpoint(mCurrentBytes > 0 && mCurrentBytes !=
		// mTotalBytes);
	}

}
