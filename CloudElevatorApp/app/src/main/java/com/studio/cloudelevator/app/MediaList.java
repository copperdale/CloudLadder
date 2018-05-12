package com.studio.cloudelevator.app;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.studio.cloudelevator.R;
import com.studio.cloudelevator.config.AppConfig;

public class MediaList {

	private List<String> mediaList = new ArrayList<String>();
	private CloudElevatorActivity activity;
	private int currMedia = -1;

	public MediaList(CloudElevatorActivity activity) {
		this.activity = activity;
	}

	public void initMedia() {
		currMedia = -1;
		setData(AppConfig.getInitMedias());
	}

	public boolean isEmpty() {
		return mediaList.isEmpty();
	}

	public void clear() {
		currMedia = -1;
		mediaList.clear();
	}

	public void setData(List<String> path) {
		clear();
		mediaList.addAll(path);
	}

	public void setData(String[] path) {
		clear();
		if (path != null) {
			Collections.addAll(mediaList, path);
		}
	}

	public String getNextMedia() {
		if (mediaList.isEmpty()) {
			return "android.resource://" + activity.getPackageName() + "/" + R.raw.media_ad2;
		}

		if (++currMedia > mediaList.size() - 1) {
			currMedia = 0;
		}

		return mediaList.get(currMedia);
	}

	public String getCurrMediaPath() {
		return currMedia == -1 ? null : mediaList.get(currMedia);
	}
}
