package com.studio.cloudelevator.view;

import android.content.Context;
import android.graphics.Paint;
import android.text.TextUtils.TruncateAt;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.TextView;

public class MarqueeTextView extends TextView implements Runnable {

	private int viewWidth;
	private int textWidth = 0;
	private int scrollToX = 0;
	// private boolean isRun = true;
	private boolean isMeasureContentWidth = false;

	private String[] notices;

	public MarqueeTextView(Context context) {
		super(context);
		initView(context);
	}

	public MarqueeTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView(context);
	}

	private void initView(Context context) {
		setSingleLine();
		setGravity(Gravity.CENTER_VERTICAL);
		setEllipsize(TruncateAt.MARQUEE);
		setMarqueeRepeatLimit(-1);
		getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
			@Override
			public void onGlobalLayout() {
				if (!isMeasureContentWidth) {
					isMeasureContentWidth = true;
					viewWidth = getWidth();
					startMarqueeText();
				}
			}
		});
	}

	public void setMarqueeText(String... notices) {
		this.notices = notices;
		if (isMeasureContentWidth) {
			startMarqueeText();
		}
	}

	private void startMarqueeText() {
		if (notices == null || notices.length == 0) {
			pauseScroll();
			setText(null);
			return;
		}

		// 获取文字长度
		Paint paint = MarqueeTextView.this.getPaint();
		String content = getMarqueeText(paint); // MarqueeTextView.this.getText().toString();
		textWidth = (int) paint.measureText(content);

		setText(content);
		startScroll();
	}

	private String getMarqueeText(Paint paint) {
		if (notices == null || notices.length == 0) {
			return null;
		}

		if (notices.length == 1) {
			return notices[0];
		}

		String nbsp = " ";
		while (paint.measureText(nbsp) < viewWidth) {
			nbsp += " ";
		}

		StringBuilder sb = new StringBuilder();
		for (int i = 0, iSize = notices.length; i < iSize; i++) {
			sb.append(notices[i]);
			if (i < iSize - 1) {
				sb.append(nbsp);
			}
		}
		return sb.toString();
	}

	@Override
	public void setGravity(int gravity) {
		super.setGravity(gravity);

	}

	@Override
	public void run() {
		// if (isRun) {
		// 重新开始
		if (scrollToX >= textWidth) {
			scrollToX = -viewWidth;
		}

		scrollTo(scrollToX, 0);
		scrollToX = scrollToX + 1;

		removeCallbacks(this);
		postDelayed(this, 150);
		// }
	}

	// 点击开始,执行线程
	private void startScroll() {
		stopScroll();
		post(this);
	}

	// 点击暂停
	public void pauseScroll() {
		removeCallbacks(this);
		// isRun = false;
	}

	public void resmeScroll() {
		post(this);
	}

	// 停止
	private void stopScroll() {
		scrollToX = 0;
		removeCallbacks(this);
	}

}
