package com.studio.cloudelevator.task;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.params.CoreConnectionPNames;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.studio.os.LogCat;
import android.view.View;

import com.studio.cloudelevator.MyApplication;
import com.studio.cloudelevator.config.AppConfig;

/**
 * 截屏
 * 
 * @author Administrator
 * 
 */
public class Screenshot {

	private Activity mHoldingActivity;
	private BlurAsyncTask mBluringTask;
	private boolean sending;

	public Screenshot(Activity holdingActivity) {
		mHoldingActivity = holdingActivity;
	}

	public void startScreenshot(String currMedia, int currentPosition) {
		if (sending) {
			return;
		}

		cancel();
		sending = true;
		mBluringTask = new BlurAsyncTask(currMedia, currentPosition);
		mBluringTask.execute();

	}

	/**
	 * 取消截屏
	 */
	public void cancel() {
		sending = false;
		if (mBluringTask != null) {
			mBluringTask.cancel(true);
			mBluringTask = null;
		}
	}

	/**
	 * Async task used to process blur out of ui thread
	 */
	public class BlurAsyncTask extends AsyncTask<Void, Void, Void> {

		private Bitmap mBackground;
		private View mBackgroundView;

		String currMedia;
		int currentPosition;

		public BlurAsyncTask(String currMedia, int currentPosition) {
			this.currMedia = currMedia;
			this.currentPosition = currentPosition;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			LogCat.i("Screenshot ing...");
			mBackgroundView = mHoldingActivity.getWindow().getDecorView();
			Rect rect = new Rect();
			mBackgroundView.getWindowVisibleDisplayFrame(rect);
			mBackgroundView.destroyDrawingCache();
			mBackgroundView.setDrawingCacheEnabled(true);
			mBackgroundView.buildDrawingCache(true);
			mBackground = mBackgroundView.getDrawingCache(true);

			if (mBackground == null) {
				mBackgroundView.measure(View.MeasureSpec.makeMeasureSpec(rect.width(), View.MeasureSpec.EXACTLY),
						View.MeasureSpec.makeMeasureSpec(rect.height(), View.MeasureSpec.EXACTLY));
				mBackgroundView.layout(0, 0, mBackgroundView.getMeasuredWidth(), mBackgroundView.getMeasuredHeight());
				mBackgroundView.destroyDrawingCache();
				mBackgroundView.setDrawingCacheEnabled(true);
				mBackgroundView.buildDrawingCache(true);
				mBackground = mBackgroundView.getDrawingCache(true);
			}
		}

		@Override
		protected Void doInBackground(Void... params) {
			Bitmap bitmap = null;
			int bitmapWidth = mBackground.getWidth() * 3 / 8;
			int bitmapHeight = mBackground.getHeight() * 3 / 8;
			if (currMedia != null) {
				MediaMetadataRetriever rev = new MediaMetadataRetriever();
				rev.setDataSource(mHoldingActivity, Uri.parse(currMedia));
				bitmap = rev.getFrameAtTime(currentPosition * 1000, MediaMetadataRetriever.OPTION_CLOSEST_SYNC);
				// bitmap = Bitmap.createScaledBitmap(bitmapVideo, bitmapWidth,
				// bitmapHeight, true);
				// bitmapVideo.recycle();
			}

			if (bitmap == null) {
				bitmap = mBackground;
			}

			Bitmap tempBitmap = bitmap;
			bitmap = Bitmap.createScaledBitmap(tempBitmap, bitmapWidth, bitmapHeight, true);
			tempBitmap.recycle();

			// clear memory
			// mBackground.recycle();
			mBackgroundView.destroyDrawingCache();
			mBackgroundView.setDrawingCacheEnabled(false);

			try {
				// ByteArrayOutputStream outStream = new
				// ByteArrayOutputStream();
				File _tempFile = new File(MyApplication.mediaDir, "_temp.png");
				OutputStream outStream = new FileOutputStream(_tempFile);
				bitmap.compress(Bitmap.CompressFormat.JPEG, 10, outStream);
				bitmap.recycle();
				outStream.flush();
				outStream.close();

				sendRequestBitmap(_tempFile);
			} catch (Throwable e) {
				LogCat.e("%s", e.getMessage());
			}

			return null;
		}

		private void sendRequestBitmap(File _tempFile) throws IOException {
			HttpPost request = new HttpPost(AppConfig.SCREENSHOT_POST);
			MultipartEntity entity = new MultipartEntity();
			entity.addPart("clientId", new StringBody(AppConfig.getUuid()));
			entity.addPart("screenImage", new FileBody(_tempFile));
			request.setEntity(entity);
			AndroidHttpClient httpClient = AndroidHttpClient.newInstance("WebAndroid");
			httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 10 * 1000);
			httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 10 * 1000);

			executeHttp(request, httpClient);

			httpClient.close();
		}

		private void executeHttp(HttpPost request, AndroidHttpClient httpClient) throws IOException {
			LogCat.i("sned ...");
			int retry = 0;
			while (retry++ < 5) {
				try {
					HttpResponse response = httpClient.execute(request);
					if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
						LogCat.i("sned OK");
						return;
					}
				} catch (Exception e) {
					LogCat.i("send retry: " + retry);
				}
			}
		}

		@Override
		protected void onPostExecute(Void aVoid) {
			super.onPostExecute(aVoid);
			mBackgroundView = null;
			mBackground = null;
			sending = false;
		}
	}

}
