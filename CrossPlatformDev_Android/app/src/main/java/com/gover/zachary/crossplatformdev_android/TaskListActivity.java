// Zachary Gover
// CPMD - 1701
// TaskListActivity

package com.gover.zachary.crossplatformdev_android;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import com.gover.zachary.crossplatformdev_android.fragments.TaskListFragment;
import com.gover.zachary.crossplatformdev_android.interfaces.TaskListItemListeners;
import com.gover.zachary.crossplatformdev_android.models.AppUtils;
import com.gover.zachary.crossplatformdev_android.models.FirebaseManager;
import com.gover.zachary.crossplatformdev_android.models.Network;
import com.gover.zachary.crossplatformdev_android.models.Task;

public class TaskListActivity extends AppCompatActivity implements TaskListItemListeners {

	private static final String TAG = "TaskListActivity";
	private static final int CREATE_TASK = 1;
	private static final int EDIT_TASK = 2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_container);

		// Verify network state and notify the user
		if (!Network.isOnline(this)) {
			AppUtils.showToast(this, "Invalid Network Connection", false);
		}

		// Setup default properties
		setupFragment();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_task_list_activity, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Determine selection
		switch(item.getItemId()) {
			case R.id.AddNewTaskBtn:
				addNewTask();
				break;
			case R.id.RefreshBtn:
				refreshList();
				break;
			case R.id.LogoutBtn:
				FirebaseManager.logout(this);
				break;
			default:
				break;
		}

		return true;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		switch (requestCode) {
			case CREATE_TASK:
				if (resultCode == RESULT_OK) { setupFragment(); }
				break;
			default:
				break;
		}
	}

	@Override
	public void listItemClick(Task task, String key) {
		// Open the edit activity and pass the task
		Intent intent = new Intent(this, EditTaskActivity.class);

		intent.putExtra(Task.TAG, task);
		intent.putExtra(Task.KEY, key);

		startActivityForResult(intent, EDIT_TASK);
	}

	@Override
	public void listItemLongClick(final String key) {
		// Verify network state and notify the user
		if (!Network.isOnline(this)) {
			AppUtils.showToast(this, "Invalid Network Connection", true);
			return;
		}

		// Build confirmation to verify deletion
		AlertDialog.Builder builder = new AlertDialog.Builder(this);

		builder.setTitle("Are you sure?");
		builder.setMessage("Are you sure you would like to delete this task?");
		builder.setPositiveButton("Cancel", null);
		builder.setNegativeButton("Delete", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialogInterface, int i) {
				FirebaseManager.deleteTask(key);
			}
		}).show();
	}

	private void refreshList() {
		// Verify network state and notify the user
		if (!Network.isOnline(this)) {
			AppUtils.showToast(this, "Invalid Network Connection", false);
			return;
		}

		setupFragment();
	}

	private void setupFragment() {
		// Setup the fragment to populate list
		TaskListFragment frag = TaskListFragment.newInstance();
		getSupportFragmentManager().beginTransaction()
			.replace(R.id.Content, frag, TAG).commit();
	}

	private void addNewTask() {
		Intent intent = new Intent(this, CreateTaskActivity.class);
		startActivityForResult(intent, CREATE_TASK);
	}
}
