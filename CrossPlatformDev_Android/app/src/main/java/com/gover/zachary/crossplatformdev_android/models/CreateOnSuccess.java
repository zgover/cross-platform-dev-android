// Zachary Gover
// CPMD - 1701
// CreateOnSuccess

package com.gover.zachary.crossplatformdev_android.models;

import android.content.Context;
import com.google.android.gms.tasks.OnSuccessListener;
import com.gover.zachary.crossplatformdev_android.interfaces.CreatedSuccessfullyCallbacks;

public class CreateOnSuccess implements OnSuccessListener {

	private Context context;
	private CreatedSuccessfullyCallbacks callbacks;

	public CreateOnSuccess(Context context, CreatedSuccessfullyCallbacks callbacks) {
		this.context = context;
		this.callbacks = callbacks;
	}

	@Override
	public void onSuccess(Object o) {
		AppUtils.dismissProgress();
		AppUtils.showToast(context, "Created successfully", false);
		callbacks.createdSuccessfully();
	}
}
