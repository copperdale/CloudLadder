package com.studio.cloudelevator.config;

import java.io.File;
import java.io.FilenameFilter;

import android.content.Context;
import android.studio.os.EnvironmentUtils;
import android.studio.os.PreferencesUtils;

import com.google.gson.GsonUtils;
import com.studio.cloudelevator.MyApplication;
import com.studio.cloudelevator.entity.InitConfig;

public class AppConfig {

	//public static final String BaseUrl = String.format("http://%s/cloudelevator/api", "115.231.97.151:3180"); // 120.26.69.243:8080#120.24.84.72
	
	public static final String BaseUrl = "http://115.231.97.151:3080/elevator/api";
	public static final String InitConfig = BaseUrl + "/init.htm?clientId=%s&deviceName=%s&allStorage=%s&curStorage=%s&media=%s";

	/** 截屏指令与截屏数据接收地址 */
	public static final String SCREENSHOT_POST = BaseUrl + "/saveScreen.htm";

	private static final String pref_uuid = "pref_uuid";
	private static final String pref_config_json = "pref_config";
	private static final String pref_breakpoint = "pref_breakpoint";

	public static final String ACTION_DEVICE_NAME = "studio.intent.action.ACTION_DEVICE_NAME";
	public static final String ACTION_NOTICES = "studio.intent.action.ACTION_NOTICES";
	public static final String ACTION_MEDIAS = "studio.intent.action.ACTION_MEDIAS";
	public static final String ACTION_SCREENSHOT = "studio.intent.action.ACTION_SCREENSHOT";

	public static final String MEDIA_SUFFIX = ".mp4";
	public static final String MEDIA_TEMP_SUFFIX = ".~tmp";

	public static void setPrefConfigJson(Context context, String configJson) {
		PreferencesUtils.setString(pref_uuid, EnvironmentUtils.getUUID(context));
		PreferencesUtils.setString(pref_config_json, configJson);
	}

	public static String getUuid() {
		return PreferencesUtils.getString(pref_uuid, null);
	}

	public static InitConfig getInitConfig() {
		String jsonInitConfig = PreferencesUtils.getString(pref_config_json, null);
		return GsonUtils.jsonDeserializer(jsonInitConfig, InitConfig.class);
	}

	public static String getInitDeviceName() {
		try {
			return getInitConfig().deviceName;
		} catch (Exception e) {
			return null;
		}
	}

	public static String[] getInitNotices() {
		try {
			return getInitConfig().getNotices();
		} catch (Exception e) {
			return null;
		}
	}

	public static String[] getInitMedias() {
		try {
			String[] results = getInitConfig().getMedias();
			for (String media : results) {
				if (!new File(media).exists()) {
					return null;
				}
			}
			return results;
		} catch (Exception e) {
			return null;
		}
	}

	/** 是否断点传输 */
	/*
	 * public static void setPrefBreakpoint(boolean breakpoint) {
	 * PreferencesUtils.setBoolean(pref_breakpoint, breakpoint); }
	 */

	/**
	 * 断点传输
	 * 
	 * @return true为断点 | false为断点完成
	 */
	/*
	 * public static boolean isBreakpoint() { return
	 * PreferencesUtils.getBoolean(pref_breakpoint, false); }
	 */

	public static File[] getMediaFileAll() {
		File[] medias = MyApplication.mediaDir.listFiles(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String filename) {
				return filename.endsWith(AppConfig.MEDIA_SUFFIX) || filename.endsWith(MEDIA_TEMP_SUFFIX);
			}
		});
		return medias == null ? new File[0] : medias;
	}
}
