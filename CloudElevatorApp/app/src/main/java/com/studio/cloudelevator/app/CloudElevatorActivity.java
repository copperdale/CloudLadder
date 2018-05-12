package com.studio.cloudelevator.app;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.studio.app.ActivitySupport;
import android.studio.os.EnvironmentUtils;
import android.studio.widget.MarqueeTextView;
import android.support.v4.media.TransportMediator;
import android.support.v4.media.TransportPerformer;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.studio.cloudelevator.R;
import com.studio.cloudelevator.config.AppConfig;
import com.studio.cloudelevator.task.HeartbeatService;
import com.studio.cloudelevator.task.Screenshot;
import com.studio.cloudelevator.task.SerialportService;
import com.studio.cloudelevator.task.SerialportService.OnFloorChangedListener;
import com.studio.cloudelevator.view.CVideoView;

public class CloudElevatorActivity extends ActivitySupport {

	private CVideoView mContent;
	private TextView mediaMsg;
	private TransportMediator mTransportMediator;

	private ImageView imgFloorArrowUp;
	private TextView txtFloorNumber;
	private ImageView imgFloorArrowDown;

	private ImageView imgFire;
	private ImageView imgNosmoking;
	private TextView txtDeviceName;

	private MarqueeTextView txtNotification;
	private MediaList mediaList;
	private Screenshot screenshot;

	private SerialportService serialport;

	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		setContentView(R.layout.media_video);
		initViews();

		mediaList = new MediaList(this);
		mediaList.initMedia();

		screenshot = new Screenshot(this);
		updateView();
		startPlay();
		registerReceiver();

		Intent intent = new Intent(this, SerialportService.class);
		bindService(intent, conn, Context.BIND_AUTO_CREATE);
	}

	private ServiceConnection conn = new ServiceConnection() {
		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			serialport = ((SerialportService.ServiceBinder) service).getService();
			serialport.setFloorChangedListener(floorChangedListener);
		}

		@Override
		public void onServiceDisconnected(ComponentName name) {
			serialport = null;
		}
	};

	private OnFloorChangedListener floorChangedListener = new OnFloorChangedListener() {
		@Override
		public void onFloorChanged(int hf, int lf, String floor) {
			txtFloorNumber.setText(floor);
		}

		@Override
		public void onStatusChanged(int down, int up, int open, int nosmoking, int overload, int fire) {
			setFloorStatus(down, up, 0, 1, 0, fire);
		}
	};

	/** 注册广播 */
	private void registerReceiver() {
		IntentFilter filter = new IntentFilter();
		filter.addAction(AppConfig.ACTION_DEVICE_NAME);
		filter.addAction(AppConfig.ACTION_NOTICES);
		filter.addAction(AppConfig.ACTION_MEDIAS);
		filter.addAction(AppConfig.ACTION_SCREENSHOT);
		registerReceiver(notificationReceiver, filter);
	}

	/** 启动播放 */
	private void startPlay() {
		if (mediaList.isEmpty()) {
			mediaMsg.setVisibility(View.VISIBLE);
			mediaMsg.setText("暂无媒体资源");
		} else {
			// if (!mContent.isPlaying()) {
			mediaMsg.setVisibility(View.GONE);
			mContent.stopPlayback();
			mContent.setVideoPath(mediaList.getNextMedia());
			mContent.start();
			// }
		}
	}

	private void initViews() {
		// Find the video player in our UI.
		mContent = (CVideoView) findViewById(R.id.content);
		mediaMsg = (TextView) findViewById(R.id.mediaMsg);
		mTransportMediator = new TransportMediator(this, mTransportPerformer);
		mContent.init(this, mTransportMediator);

		imgFloorArrowUp = (ImageView) findViewById(R.id.imgFloorArrowUp);
		txtFloorNumber = (TextView) findViewById(R.id.txtFloorNumber);
		imgFloorArrowDown = (ImageView) findViewById(R.id.imgFloorArrowDown);

		imgFire = (ImageView) findViewById(R.id.imgFire);
		imgNosmoking = (ImageView) findViewById(R.id.imgNosmoking);
		txtDeviceName = (TextView) findViewById(R.id.txtDeviceName);

		txtNotification = (MarqueeTextView) findViewById(R.id.txtNotification);
		txtNotification.setMarqueeSpeed(2.5f);
		setFloorStatus(0, 0, 0, 1, 0, 0);
	}

	private BroadcastReceiver notificationReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			if (AppConfig.ACTION_DEVICE_NAME.equals(action)) {
				String deviceName = intent.getStringExtra("deviceName");
				updateDeviceNameView(deviceName);
			} else if (AppConfig.ACTION_NOTICES.equals(action)) {
				String[] notices = intent.getStringArrayExtra("notices");
				updateNoticeView(notices);
			} else if (AppConfig.ACTION_MEDIAS.equals(action)) {
				final String[] path = intent.getStringArrayExtra("path");
				mediaList.setData(path);
				// isSync = false;
				startPlay();
			} else if (AppConfig.ACTION_SCREENSHOT.equals(action)) {
				screenshot.startScreenshot(mediaList.getCurrMediaPath(), mContent.getCurrentPosition());
			}
		}
	};

	@Override
	protected void onResume() {
		HeartbeatService.getInstance().startMonitor(this);
		super.onResume();
	}

	@Override
	protected void onPause() {
		HeartbeatService.getInstance().stopMonitor(this);
		super.onPause();
	}

	@Override
	protected void onDestroy() {
		screenshot.cancel();
		unregisterReceiver(notificationReceiver);
		unbindService(conn);
		super.onDestroy();
	}

	@Override
	public boolean dispatchKeyEvent(KeyEvent event) {
		if (mTransportMediator.dispatchKeyEvent(event)) {
			return true;
		}

		return super.dispatchKeyEvent(event);
	}

	/**
	 * 上楼或下楼
	 * 
	 * @param down
	 * @param up
	 * @param open
	 * @param nosmoking
	 * @param overload
	 * @param fire
	 */
	private void setFloorStatus(int down, int up, int open, int nosmoking, int overload, int fire) {
		imgFloorArrowUp.setImageResource(up == 1 ? R.drawable.floor_ic_arrows_up_on : R.drawable.floor_ic_arrows_up);
		imgFloorArrowDown.setImageResource(down == 1 ? R.drawable.floor_ic_arrows_down_on : R.drawable.floor_ic_arrows_down);
		imgNosmoking.setVisibility(nosmoking == 1 ? View.VISIBLE : View.INVISIBLE);
		imgFire.setVisibility(fire == 1 ? View.VISIBLE : View.INVISIBLE);
	}

	private void updateView() {
		updateDeviceNameView(AppConfig.getInitDeviceName());
		updateNoticeView(AppConfig.getInitNotices());
	}

	private void updateDeviceNameView(String deviceName) {
		if (TextUtils.isEmpty(deviceName)) {
			txtDeviceName.setText(EnvironmentUtils.getUUID(this));
		} else {
			txtDeviceName.setText(deviceName);
		}
	}

	private void updateNoticeView(String[] notices) {
		if (notices == null || notices.length == 0) {
			txtNotification.setVisibility(View.GONE);
		} else {
			txtNotification.setVisibility(View.VISIBLE);
			txtNotification.setMarqueeText(notices);
		}
	}

	/**
	 * Handle actions from on-screen media controls. Most of these are simple
	 * re-directs to the VideoView; some we need to capture to update our state.
	 */
	private TransportPerformer mTransportPerformer = new TransportPerformer() {
		@Override
		public void onStart() {
			mContent.start();
		}

		@Override
		public void onStop() {
			// mContent.pause();
			// mContent.stopPlayback();
			// mContent.setVideoPath(mediaList.getNextMedia());
			// mContent.start();
			startPlay();
		}

		@Override
		public void onPause() {
			mContent.pause();
		}

		@Override
		public long onGetDuration() {
			return mContent.getDuration();
		}

		@Override
		public long onGetCurrentPosition() {
			return mContent.getCurrentPosition();
		}

		@Override
		public void onSeekTo(long pos) {
			mContent.seekTo((int) pos);
		}

		@Override
		public boolean onIsPlaying() {
			return mContent.isPlaying();
		}

		@Override
		public int onGetBufferPercentage() {
			return mContent.getBufferPercentage();
		}

		@Override
		public int onGetTransportControlFlags() {
			int flags = TransportMediator.FLAG_KEY_MEDIA_PLAY | TransportMediator.FLAG_KEY_MEDIA_PLAY_PAUSE | TransportMediator.FLAG_KEY_MEDIA_STOP
					| TransportMediator.FLAG_KEY_MEDIA_PREVIOUS | TransportMediator.FLAG_KEY_MEDIA_NEXT;
			if (mContent.canPause()) {
				flags |= TransportMediator.FLAG_KEY_MEDIA_PAUSE;
			}
			if (mContent.canSeekBackward()) {
				flags |= TransportMediator.FLAG_KEY_MEDIA_REWIND;
			}
			if (mContent.canSeekForward()) {
				flags |= TransportMediator.FLAG_KEY_MEDIA_FAST_FORWARD;
			}
			return flags;
		}
	};
}
