package com.studio.cloudelevator.task;

import java.io.File;
import java.util.Timer;
import java.util.TimerTask;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInstallerUpdate;
import android.studio.os.EnvironmentUtils;
import android.studio.os.LogCat;
import android.studio.util.URLUtils;

import com.github.kevinsawicki.http.HttpRequest;
import com.google.gson.GsonUtils;
import com.studio.cloudelevator.MyApplication;
import com.studio.cloudelevator.config.AppConfig;
import com.studio.cloudelevator.entity.InitConfig;
import com.studio.cloudelevator.entity.Media;

/**
 * 心跳包10秒/次
 * 
 * @author Administrator
 * 
 */
public class HeartbeatService {

	private ServerHeartbeatTask heartbeatTask; // 网络盘监控器
	private static HeartbeatService INSTANCE;

	public static HeartbeatService getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new HeartbeatService();
		}
		return INSTANCE;
	}

	private HeartbeatService() {
	}

	public void startMonitor(Context context) {
		stopMonitor(context);
		heartbeatTask = new ServerHeartbeatTask(context);
		heartbeatTask.start();
	}

	public void stopMonitor(Context context) {
		MediaDownloadAsyncTaskUtils.stop();
		if (heartbeatTask != null) {
			heartbeatTask.stop();
			heartbeatTask = null;
		}
	}

	/**
	 * 网络磁盘监控
	 * 
	 * @author fq
	 */
	public class ServerHeartbeatTask extends TimerTask {

		static final int sleep_period = 5 * 1000;
		private Timer timer;
		private boolean isRuning;
		private Context context;

		public ServerHeartbeatTask(Context context) {
			this.context = context;
		}

		/**
		 * 启动定时扫描
		 */
		public void start() {
			timer = new Timer();
			timer.schedule(this, 1000, sleep_period);
		}

		/**
		 * 清除定时扫描
		 */
		public void stop() {
			timer.cancel();
			timer = null;
			isRuning = false;
		}

		@Override
		public void run() {
			try {
				if (isRuning) {
					return;
				}

				isRuning = true;
				// if (NetUtils.isNetConnected(context)) {
				syncConfig();
				// }
			} catch (Throwable e) {
				LogCat.e(e.getMessage());
			} finally {
				isRuning = false;
			}
		}

		/**
		 * 发送心跳包
		 * 
		 * @throws Exception
		 * @throws
		 */
		private void syncConfig() throws Exception {
			String uuid = EnvironmentUtils.getUUID(context);
			String media = getMediaParam();
			String configURL = String.format(AppConfig.InitConfig, uuid, //
					URLUtils.encodeURIComponent(android.os.Build.MODEL), //
					EnvironmentUtils.getSDTotalSize(), //
					EnvironmentUtils.getSDAvailableSize(), media);

			HttpRequest request = HttpRequest.get(configURL);
			request.connectTimeout(sleep_period);
			request.readTimeout(sleep_period);
			if (!request.ok()) {
				throw new Exception("request code: " + request.code());
			}

			String jsonConfig = request.body();
			LogCat.i("syncConfig: " + jsonConfig);
			InitConfig newConfig = GsonUtils.jsonDeserializer(jsonConfig, InitConfig.class);
			if (newConfig == null) {
				throw new NullPointerException("InitConfig is null");
			}

			checkUpdate(newConfig);
			sendUuidBroadcast(newConfig);
			sendNoticeBroadcast(newConfig);
			sendScreenshotBroadcast(newConfig);
			MediaDownloadAsyncTaskUtils.start(context, newConfig);

			AppConfig.setPrefConfigJson(context, jsonConfig);
		}

		private String getMediaParam() {
			StringBuilder result = new StringBuilder();
			InitConfig config = AppConfig.getInitConfig();
			if (config != null) {
				for (int i = 0; i < config.medias.size(); i++) {
					Media m = config.medias.get(i);
					result.append(m.mediaId + ",");

					File mediaTempFile = new File(MyApplication.mediaDir, m.mediaId + AppConfig.MEDIA_SUFFIX + AppConfig.MEDIA_TEMP_SUFFIX);
					if (mediaTempFile.exists()) {
						result.append(mediaTempFile.length());
					} else {
						File mediaFile = new File(MyApplication.mediaDir, m.mediaId + AppConfig.MEDIA_SUFFIX);
						result.append(mediaFile.length());
					}

					if (i < config.medias.size() - 1) {
						result.append(",");
					}
				}
			}
			return result.toString();
		}

		private void checkUpdate(InitConfig newConfig) {
			if (newConfig.checkUpdate()) {
				PackageInstallerUpdate.startThread(context, newConfig.client.url);
			}
		}

		private void sendUuidBroadcast(InitConfig newConfig) {
			Intent intent = new Intent(AppConfig.ACTION_DEVICE_NAME);
			intent.putExtra("deviceName", newConfig.deviceName);
			context.sendBroadcast(intent);
		}

		private void sendNoticeBroadcast(InitConfig initConfig) {
			if (initConfig.isNoticeSyn()) {
				Intent intent = new Intent(AppConfig.ACTION_NOTICES);
				intent.putExtra("notices", initConfig.getNotices());
				context.sendBroadcast(intent);
			}
		}

		private void sendScreenshotBroadcast(InitConfig initConfig) {
			// 判断是否要截屏
			if (initConfig.isScreenshot()) {
				// AppConfig.setPref_screenshot_time(initConfig.checkScreen.time);
				context.sendBroadcast(new Intent(AppConfig.ACTION_SCREENSHOT));
			}
		}
	}
}
