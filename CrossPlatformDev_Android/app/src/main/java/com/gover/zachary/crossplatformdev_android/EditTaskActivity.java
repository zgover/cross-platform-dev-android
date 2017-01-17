// Zachary Gover
// CPMD - 1701
// CreateTaskActivity

package com.gover.zachary.crossplatformdev_android;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import com.gover.zachary.crossplatformdev_android.fragments.EditTaskFragment;
import com.gover.zachary.crossplatformdev_android.interfaces.CreatedSuccessfullyCallbacks;
import com.gover.zachary.crossplatformdev_android.interfaces.EditTaskListeners;
import com.gover.zachary.crossplatformdev_android.models.*;

public class EditTaskActivity extends AppCompatActivity implements EditTaskListeners,
																   CreatedSuccessfullyCallbacks {

	private static final String TAG = "CreateTaskActivity";

	private String taskKey;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_container);

		// Verify network state and notify the user
		if (!Network.isOnline(this)) {
			AppUtils.showToast(this, "Invalid Network Connection", true);
		}

		// Setup default properties
		taskKey = getIntent().getExtras().getString(Task.KEY);
		setupFragment();
	}

	@Override
	public void saveTask(Task task) {
		// Verify network state and create new the new task
		if (!Network.isOnline(this)) {
			AppUtils.showToast(this, "Invalid Network Connection", true);
			return;
		}

		AppUtils.showProgress(this, "Please wait...", "Saving task");
		FirebaseManager.saveTask(
			task, taskKey,
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
		EditTaskFragment frag = EditTaskFragment.newInstance();
		Bundle args = new Bundle();

		args.putSerializable(Task.TAG, getIntent().getExtras().getSerializable(Task.TAG));

		frag.setArguments(args);
		getSupportFragmentManager().beginTransaction()
			.replace(R.id.Content, frag, TAG).commit();
	}
}
