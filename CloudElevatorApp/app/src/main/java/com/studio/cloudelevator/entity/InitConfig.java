package com.studio.cloudelevator.entity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.studio.cloudelevator.MyApplication;
import com.studio.cloudelevator.config.AppConfig;

public class InitConfig {

	public boolean status;
	public String message;
	public String token;
	public String deviceName;
	public List<Notice> notices;
	public List<Media> medias;
	public CheckScreen checkScreen;
	public Client client;

	public boolean checkUpdate() {
		InitConfig oldConfig = AppConfig.getInitConfig();
		return oldConfig == null || (client != null && !client.time.equals(oldConfig.client.time));
	}

	public boolean isNoticeSyn() {
		InitConfig oldConfig = AppConfig.getInitConfig();
		return oldConfig == null || (notices != null && !notices.toString().equals(oldConfig.notices.toString()));
	}

	public boolean isMediaSyn() {
		InitConfig oldConfig = AppConfig.getInitConfig();
		return oldConfig == null || isSync(oldConfig.medias);
	}

	public boolean isSync(List<Media> _medias) {
		if (_medias == null || medias.size() != _medias.size()) {
			return true;
		}

		for (int i = 0; i < medias.size(); i++) {
			String url1 = medias.get(i).url;
			if (!url1.equals(_medias.get(i).url)) {
				return true;
			}
		}

		return false;
	}

	public boolean isScreenshot() {
		InitConfig oldConfig = AppConfig.getInitConfig();
		return oldConfig == null || (checkScreen != null && checkScreen.time > oldConfig.checkScreen.time);
	}

	public String[] getNotices() {
		List<String> results = new ArrayList<String>();
		if (notices != null) {
			for (Notice n : notices) {
				results.add(n.content);
			}
		}
		return results.toArray(new String[0]);
	}

	public String[] getMedias() {
		List<String> results = new ArrayList<String>();
		InitConfig initConfig = AppConfig.getInitConfig();
		if (initConfig.medias != null) {
			for (Media media : initConfig.medias) {
				results.add(new File(MyApplication.mediaDir, media.mediaId + AppConfig.MEDIA_SUFFIX).getPath());
			}
		}
		return results.toArray(new String[0]);
	}
}
