package com.studio.cloudelevator.task;

import android.content.Context;

import com.studio.cloudelevator.entity.InitConfig;

public class MediaDownloadAsyncTaskUtils {

	static MediaDownloadAsyncTask asyncTask;

	public static void start(Context context, InitConfig config) {
		if (config.isMediaSyn()) {
			startMediaDownladAsyncTask(context, config);
		} else {
			if (asyncTask == null || asyncTask.isComputed()) {
				if (config.isSync(MediaDownloadAsyncTask.getMediaPref())) {
					startMediaDownladAsyncTask(context, config);
				}
			}
		}
	}

	private static void startMediaDownladAsyncTask(Context context, InitConfig config) {
		stop();
		asyncTask = new MediaDownloadAsyncTask(context, config);
		asyncTask.execute((Void) null);
	}

	public static void stop() {
		if (asyncTask != null) {
			asyncTask.cancel(true);
			asyncTask = null;
		}
	}
}
