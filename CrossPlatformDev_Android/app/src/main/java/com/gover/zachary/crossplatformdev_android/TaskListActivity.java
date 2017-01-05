// Zachary Gover
// CPMD - 1701
// TaskListActivity

package com.gover.zachary.crossplatformdev_android;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class TaskListActivity extends AppCompatActivity {

	private static final String TAG = "TaskListActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_container);

		// Setup default properties
		setupFragment();
	}

	private void setupFragment() {

	}
}
