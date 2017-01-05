// Zachary Gover
// CPMD - 1701
// CreateOnFail

package com.gover.zachary.crossplatformdev_android.models;

import android.content.Context;
import android.support.annotation.NonNull;
import com.google.android.gms.tasks.OnFailureListener;

public class CreateOnFail implements OnFailureListener {

	private Context context;

	public CreateOnFail(Context context) {
		this.context = context;
	}

	@Override
	public void onFailure(@NonNull Exception e) {
		AppUtils.dismissProgress();
		AppUtils.showToast(context, "Failed to save", false);
	}
}
