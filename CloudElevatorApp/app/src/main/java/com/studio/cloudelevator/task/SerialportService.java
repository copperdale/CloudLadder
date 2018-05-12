package com.studio.cloudelevator.task;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Locale;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android_serialport_api.SerialPort;

public class SerialportService extends Service {

	public interface OnFloorChangedListener {

		void onFloorChanged(int hf, int lf, String floor);

		void onStatusChanged(int down, int up, int open, int nosmoking, int overload, int fire);
	}

	protected SerialPort mSerialPort;
	protected OutputStream mOutputStream;
	private InputStream mInputStream;
	private ReadThread mReadThread;
	private boolean bStopSerialPort = true;

	static final int _FLOOR = 0x1;
	static final int _STATUS = 0x1 << 1;
	static final HashMap<Integer, String> FLOOR_SER = new HashMap<Integer, String>();
	static {
		FLOOR_SER.put(0, "0");
		FLOOR_SER.put(1, "1");
		FLOOR_SER.put(2, "2");
		FLOOR_SER.put(3, "3");
		FLOOR_SER.put(4, "4");
		FLOOR_SER.put(5, "5");
		FLOOR_SER.put(6, "6");
		FLOOR_SER.put(7, "7");
		FLOOR_SER.put(8, "8");
		FLOOR_SER.put(9, "9");
		FLOOR_SER.put(10, "A");
		FLOOR_SER.put(11, "B");
		FLOOR_SER.put(12, "C");
		FLOOR_SER.put(13, "D");
		FLOOR_SER.put(14, "E");
		FLOOR_SER.put(15, "F");
		FLOOR_SER.put(16, "G");
		FLOOR_SER.put(17, "H");
		FLOOR_SER.put(18, "I");
		FLOOR_SER.put(19, "J");
		FLOOR_SER.put(20, "K");
		FLOOR_SER.put(21, "L");
		FLOOR_SER.put(22, "M");
		FLOOR_SER.put(23, "N");
		FLOOR_SER.put(24, "P");
		FLOOR_SER.put(25, "Q");
		FLOOR_SER.put(26, "R");
		FLOOR_SER.put(27, "T");
		FLOOR_SER.put(28, "U");
		FLOOR_SER.put(29, "V");
		FLOOR_SER.put(30, "W");
		FLOOR_SER.put(31, "S");
		FLOOR_SER.put(32, "Y");
		FLOOR_SER.put(33, "Z");
		FLOOR_SER.put(34, "-");
		FLOOR_SER.put(35, "");
		FLOOR_SER.put(36, "");
		FLOOR_SER.put(37, "S");
	}

	private OnFloorChangedListener changedListener;
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			Object[] results = null;
			switch (msg.what) {
			case _FLOOR:
				results = (Object[]) msg.obj;
				if (changedListener != null) {
					changedListener.onFloorChanged((Integer) results[0], (Integer) results[1], (String) results[2]);
				}
				break;
			case _STATUS:
				results = (Object[]) msg.obj;
				if (changedListener != null) {
					changedListener.onStatusChanged((Integer) results[0], (Integer) results[1], (Integer) results[2], (Integer) results[3],
							(Integer) results[4], (Integer) results[5]);
				}
				break;
			}
		}
	};

	@Override
	public IBinder onBind(Intent intent) {
		return new ServiceBinder();
	}

	public class ServiceBinder extends Binder {
		public SerialportService getService() {
			return SerialportService.this;
		}
	}

	// static int i = 0;

	@Override
	public void onCreate() {
		super.onCreate();
		Log.d("Serialport", "onCreate");
		try {
			mSerialPort = new SerialPort(new File("/dev/ttymxc1"), 9600, 0);
			mOutputStream = mSerialPort.getOutputStream();
			mInputStream = mSerialPort.getInputStream();

			// Create a receiving thread
			bStopSerialPort = false;
			mReadThread = new ReadThread();
			mReadThread.start();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}

		// sendFloor(-1, 7);
		// sendStatus(1, 0, 0, 1, 0, 0);
		// new Timer().schedule(new TimerTask() {
		// @Override
		// public void run() {
		// sendFloor(0,i++);
		// sendStatus(i%2, 1, 0, 1, 0, i%2);
		//
		// if (i >= 100) {
		// i = 0;
		// }
		// }
		// }, 200, 500);
	}

	public void setFloorChangedListener(OnFloorChangedListener l) {
		this.changedListener = l;
	}

	@Override
	public void onDestroy() {
		if (mReadThread != null) {
			mReadThread.interrupt();
		}

		bStopSerialPort = true;
		mSerialPort = null;
		super.onDestroy();
	}

	public static String bytesToHexString(byte[] src) {
		StringBuilder stringBuilder = new StringBuilder("");
		if (src == null || src.length <= 0) {
			return null;
		}

		for (int i = 0; i < src.length; i++) {
			int v = src[i] & 0xFF;
			if (v == 0)
				continue;

			String hv = Integer.toHexString(v);
			if (hv.length() < 2) {
				stringBuilder.append(0);
			}

			stringBuilder.append(hv);
		}

		return stringBuilder.toString();
	}

	public static short byteToShort(byte[] b) {
		short s = 0;
		short s0 = (short) (b[0] & 0xff);
		short s1 = (short) (b[1] & 0xff);
		s1 <<= 8;
		s = (short) (s0 | s1);
		return s;
	}

	private void process(byte[] buffer) {
		if ((0xE7 ^ buffer[1] ^ buffer[2] ^ buffer[3] ^ buffer[4] ^ buffer[5] ^ 0xAF) != buffer[6]) {
			Log.d("Serialport", "error data");
			return;
		}

		sendFloor((int) buffer[1], (int) buffer[2]);
		sendStatus(buffer[3] & 0x01, (buffer[3] & 0x02) >> 1, (buffer[3] & 0x04) >> 2, (buffer[3] & 0x08) >> 3, (buffer[3] & 0x10) >> 4,
				(buffer[3] & 0x20) >> 5);
	}

	private void sendStatus(int down, int up, int open, int nosmoking, int overload, int fire) {
		Log.d("Serialport", String.format("sendStatus %d %d %d %d %d %d", down, up, open, nosmoking, overload, fire));
		sendMessage(_STATUS, down, up, open, nosmoking, overload, fire);
	}

	private void sendFloor(int hf, int lf) {
		String floor = getFloor(hf, lf);
		Log.d("Serialport", String.format("sendFloor %d %d (%s)", hf, lf, floor));
		sendMessage(_FLOOR, hf, lf, floor);
	}

	private void sendMessage(int what, Object... obj) {
		Message msg = handler.obtainMessage(what);
		msg.obj = obj;
		handler.sendMessage(msg);
	}

	private String getFloor(int hf, int lf) {
		String floor;
		if (hf == 0 || hf == 35 || hf == 36) {
			floor = String.format(Locale.getDefault(), "%02d", lf);
		} else if (hf == 34) {
			if (lf == 34) {
				floor = "--";
			} else {
				floor = String.format(Locale.getDefault(), "-%d", lf);
			}
		} else {
			String lfString;
			if (lf <= 11) {
				lfString = String.format("%X", lf);
			} else {
				lfString = "X";
			}
			String hfString = FLOOR_SER.get(hf);
			floor = String.format(Locale.getDefault(), "%s%s", hfString, lfString);
		}
		return floor;
	}

	private class ReadThread extends Thread {

		@Override
		public void run() {
			final int size = 8;
			byte[] buffer = new byte[size];
			while (!isInterrupted() && !bStopSerialPort) {
				try {
					if (mInputStream == null)
						return;

					int readCount = mInputStream.read(buffer, 0, 1);
					if (buffer[0] == 0) {
						continue;
					}

					if ((buffer[0] & 0xFF) != 0xE7) {
						continue;
					}

					while (readCount < size) {
						readCount += mInputStream.read(buffer, readCount, size - readCount);
					}

					if (readCount == size) {
						Log.d("Serialport", bytesToHexString(buffer));
						process(buffer);
					}
				} catch (IOException e) {
					e.printStackTrace();
					return;
				}
			}
		}
	}
}
