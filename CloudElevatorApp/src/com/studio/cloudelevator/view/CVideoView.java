package com.studio.cloudelevator.view;

import android.app.Activity;
import android.content.Context;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v4.media.TransportMediator;
import android.util.AttributeSet;
import android.view.View;
import android.widget.VideoView;

/**
 * 媒体播放
 */
public class CVideoView extends VideoView implements View.OnClickListener, MediaPlayer.OnPreparedListener, MediaPlayer.OnCompletionListener,
		MediaPlayer.OnErrorListener {
	Activity mActivity;
	TransportMediator mTransportMediator;
	boolean mPaused;
	int mLastSystemUiVis;

	private int mVideoWidth;
	private int mVideoHeight;

	Runnable mNavHider = new Runnable() {
		@Override
		public void run() {
			setNavVisibility(false);
		}
	};

	Runnable mProgressUpdater = new Runnable() {
		@Override
		public void run() {
			getHandler().postDelayed(this, 1000);
		}
	};

	public CVideoView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// setZOrderOnTop(true);
		// setBackgroundColor(Color.RED);
		setOnClickListener(this);
		setOnPreparedListener(this);
		setOnCompletionListener(this);
		setOnErrorListener(this);
	}

	public void init(Activity activity, TransportMediator transportMediator) {
		// This called by the containing activity to supply the surrounding
		// state of the video player that it will interact with.
		mActivity = activity;
		mTransportMediator = transportMediator;
		// pause();
		// start();
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		int width = getDefaultSize(mVideoWidth, widthMeasureSpec);
		int height = getDefaultSize(mVideoHeight, heightMeasureSpec);
		/*
		 * if (mVideoWidth > 0 && mVideoHeight > 0) { if ( mVideoWidth * height
		 * > width * mVideoHeight ) { height = width * mVideoHeight /
		 * mVideoWidth; } else if ( mVideoWidth * height < width * mVideoHeight
		 * ) { width = height * mVideoWidth / mVideoHeight; } }
		 */
		setMeasuredDimension(width, height);
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		mVideoWidth = w;
		mVideoHeight = h;
		if (mVideoWidth != 0 && mVideoHeight != 0) {
			getHolder().setFixedSize(mVideoWidth, mVideoHeight);
		}
	}

	@Override
	protected void onAttachedToWindow() {
		super.onAttachedToWindow();
		if (mActivity != null) {
			// mAddedMenuListener = true;
			// mActivity.getActionBar().addOnMenuVisibilityListener(this);
		}
	}

	@Override
	protected void onDetachedFromWindow() {
		pause();
		super.onDetachedFromWindow();
		// if (mAddedMenuListener) {
		// mActivity.getActionBar().removeOnMenuVisibilityListener(this);
		// }
		// mNavVisible = false;
	}

	@Override
	protected void onWindowVisibilityChanged(int visibility) {
		super.onWindowVisibilityChanged(visibility);

		// When we become visible or invisible, play is paused.
		// pause();
		start();
	}

	@Override
	public void onClick(View v) {
		// Clicking anywhere makes the navigation visible.
		setNavVisibility(true);
	}

	@Override
	public void onPrepared(MediaPlayer mp) {
		mVideoWidth = mp.getVideoWidth();
		mVideoHeight = mp.getVideoHeight();
	}

	@Override
	public void onCompletion(MediaPlayer mp) {
		// mTransportMediator.pausePlaying();
		// pause();
		mTransportMediator.stopPlaying();
	}

	@Override
	public boolean onError(MediaPlayer mp, int what, int extra) {
		mTransportMediator.pausePlaying();
		return true;
	}

	@Override
	public void start() {
		super.start();
		mPaused = false;
		setKeepScreenOn(true);
		setNavVisibility(true);
		scheduleProgressUpdater();
	}

	@Override
	public void pause() {
		super.pause();
		mPaused = true;
		setKeepScreenOn(false);
		setNavVisibility(true);
		scheduleProgressUpdater();
	}

	void scheduleProgressUpdater() {
		Handler h = getHandler();
		if (h != null) {
			if (/* mNavVisible && */!mPaused) {
				h.removeCallbacks(mProgressUpdater);
				h.post(mProgressUpdater);
			} else {
				h.removeCallbacks(mProgressUpdater);
			}
		}
	}

	void setNavVisibility(boolean visible) {
		scheduleProgressUpdater();
	}
}
