// Zachary Gover
// CPMD - 1701
// AppUtils

package com.gover.zachary.crossplatformdev_android.models;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.annotation.Nullable;
import android.widget.Toast;

public class AppUtils {

	private static ProgressDialog progress;

	public static void showToast(Context context, String message, boolean isLong) {
		// Create new toast and show it to the user.
		Toast
			.makeText(context, message, isLong ? Toast.LENGTH_LONG : Toast.LENGTH_SHORT)
			.show();
	}

	public static void showProgress(Context context, @Nullable String title, @Nullable String msg) {
		// Create new new progress dialog and show it to the user.
		progress = new ProgressDialog(context);
		String defTitle = title == null || title.isEmpty() ? "Please Wait" : title;
		String defMsg = msg == null || msg.isEmpty() ? "Loading..." : msg;

		progress.setTitle(defTitle);
		progress.setMessage(defMsg);
	}

	public static void dismissProgress() {
		// Dismiss the progress dialog and make it null if it is showing.
		if (progress != null && progress.isShowing()) {
			progress.dismiss();
			progress = null;
		}
	}

}
