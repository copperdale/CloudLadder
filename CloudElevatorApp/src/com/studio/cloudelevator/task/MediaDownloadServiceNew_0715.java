package com.studio.cloudelevator.task;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

import com.studio.cloudelevator.config.AppConfig;

public class MediaDownloadServiceNew_0715 extends Service {

	static final String EXTRA_SYNC = "extra_sync";
	MediaDownloadAsyncTask asyncTask;

	public static void start(Context context, boolean syncImmediate) {
		Intent intent = new Intent(context, MediaDownloadServiceNew_0715.class);
		intent.putExtra(EXTRA_SYNC, syncImmediate);
		context.startService(intent);
	}
	
	public static void stop(Context context) {
		Intent intent = new Intent(context, MediaDownloadServiceNew_0715.class);
		context.stopService(intent);
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onStart(Intent intent, int startId) {
		super.onStart(intent, startId);
		boolean syncImmediate = intent.getBooleanExtra(EXTRA_SYNC, false);
		if (syncImmediate) {
			startMediaDownladAsyncTask();
		} else {
			if (asyncTask == null || asyncTask.isComputed()) {
				//if (AppConfig.isBreakpoint()) {
					startMediaDownladAsyncTask();
				//}
			}
		}
	}
	
	@Override
	public void onDestroy() {
		stopMediaDownloadAsyncTask();
	}

	private void startMediaDownladAsyncTask() {
		stopMediaDownloadAsyncTask();
		asyncTask = new MediaDownloadAsyncTask(this,null);
		asyncTask.execute((Void) null);
	}
	
	private void stopMediaDownloadAsyncTask() {
		if (asyncTask != null) {
			asyncTask.cancel(true);
			asyncTask = null;
		}
	}
}
