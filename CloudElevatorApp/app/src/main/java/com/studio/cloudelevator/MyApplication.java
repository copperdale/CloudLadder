package com.studio.cloudelevator;

import java.io.File;

import android.content.Intent;
import android.studio.ApplicationSupport;
import android.studio.os.EnvironmentUtils;

import com.studio.cloudelevator.task.SerialportService;

public class MyApplication extends ApplicationSupport {

	public static File mediaDir;

	@Override
	public void onCreate() {
		super.onCreate();
		mediaDir = EnvironmentUtils.getExternalStoragePublicDirectory("cloudelevator"); // getExternalFilesDir("media");
		initdir(mediaDir);
		startService(new Intent(this, SerialportService.class));
	}

	private void initdir(File mediaDir) {
		if (!mediaDir.exists()) {
			mediaDir.mkdirs();
		}
	}

}
