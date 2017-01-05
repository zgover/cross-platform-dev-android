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
import com.gover.zachary.crossplatformdev_android.models.FirebaseManager;
import com.gover.zachary.crossplatformdev_android.models.Task;

public class TaskListActivity extends AppCompatActivity implements TaskListItemListeners {

	private static final String TAG = "TaskListActivity";
	private static final int CREATE_TASK = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_container);

		// Setup default properties
		//setupFragment();
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
	public void listItemClick(Task task) {

	}

	@Override
	public void listItemLongClick(final String key) {
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

	private void setupFragment() {
		// Setup the fragment to populate list
		TaskListFragment frag = TaskListFragment.newInstance();
		getSupportFragmentManager().beginTransaction()
			.replace(R.id.Content, frag, TAG).commit();
	}

	private static void addNewTask() {

	}
}
