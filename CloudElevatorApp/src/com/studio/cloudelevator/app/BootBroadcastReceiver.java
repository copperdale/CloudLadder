package com.studio.cloudelevator.app;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.studio.os.LogCat;

public class BootBroadcastReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		String action = intent.getAction();
		if (Intent.ACTION_BOOT_COMPLETED.equals(action)) {
			Intent startIntent = new Intent(context, CloudElevatorActivity.class);
			startIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			context.startActivity(startIntent);
		}
		// 升级
		else if (Intent.ACTION_PACKAGE_REPLACED.equals(action)) {
			if (!context.getPackageName().equals(intent.getData().getSchemeSpecificPart())) {
				LogCat.i("other apps were upgraded");
				return;
			}

			Intent startIntent = new Intent(context, CloudElevatorActivity.class);
			startIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			context.startActivity(startIntent);
		}
	}

}
