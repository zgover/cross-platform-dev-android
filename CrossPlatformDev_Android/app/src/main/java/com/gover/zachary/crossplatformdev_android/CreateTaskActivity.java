// Zachary Gover
// CPMD - 1701
// CreateTaskActivity

package com.gover.zachary.crossplatformdev_android;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import com.gover.zachary.crossplatformdev_android.fragments.CreateTaskFragment;
import com.gover.zachary.crossplatformdev_android.interfaces.CreateTaskListeners;
import com.gover.zachary.crossplatformdev_android.interfaces.CreatedSuccessfullyCallbacks;
import com.gover.zachary.crossplatformdev_android.models.*;

public class CreateTaskActivity extends AppCompatActivity implements CreateTaskListeners,
																	 CreatedSuccessfullyCallbacks {

	private static final String TAG = "CreateTaskActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_container);

		// Setup default properties
		setupFragment();
	}

	@Override
	public void createNewTask(Task task) {
		// Verify network state and create new the new task
		if (!Network.isOnline(this)) {
			AppUtils.showToast(this, "Invalid Network Connect", true);
			return;
		}

		AppUtils.showProgress(this, "Please wait...", "Creating new task");
		FirebaseManager.createNewTask(
			task,
			new CreateOnFail(this),
			new CreateOnSuccess(this, this));
	}

	@Override
	public void createdSuccessfully() {
		Log.d(TAG, "createdSuccessfully: Closing Activity");
		this.setResult(RESULT_OK);
		this.finish();
	}

	private void setupFragment() {
		// Setup the fragment to populate list
		CreateTaskFragment frag = CreateTaskFragment.newInstance();
		getSupportFragmentManager().beginTransaction()
			.replace(R.id.Content, frag, TAG).commit();
	}
}
