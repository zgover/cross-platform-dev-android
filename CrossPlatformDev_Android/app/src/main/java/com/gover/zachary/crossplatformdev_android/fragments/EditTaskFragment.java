// Zachary Gover
// CPMD - 1701
// EditTaskFragment

package com.gover.zachary.crossplatformdev_android.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import com.gover.zachary.crossplatformdev_android.R;
import com.gover.zachary.crossplatformdev_android.interfaces.EditTaskListeners;
import com.gover.zachary.crossplatformdev_android.models.AppUtils;
import com.gover.zachary.crossplatformdev_android.models.Network;
import com.gover.zachary.crossplatformdev_android.models.Task;
import com.gover.zachary.crossplatformdev_android.models.TaskUtils;

public class EditTaskFragment extends Fragment {

	private static final String TAG = "EditTaskFragment";

	private EditTaskListeners listener;
	private Task task;
	private EditText taskNameField;
	private EditText taskAmountField;

	public static EditTaskFragment newInstance() {
		return new EditTaskFragment();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// Setup the view and listeners
		View view = inflater.inflate(R.layout.fragment_create_edit_task, container, false);

		task = (Task) getArguments().getSerializable(Task.TAG);
		taskNameField = (EditText) view.findViewById(R.id.TaskNameField);
		taskAmountField = (EditText) view.findViewById(R.id.TaskAmountField);

		taskNameField.setText(task.getName());
		taskAmountField.setText(Integer.toString(task.getAmount()));

		view.findViewById(R.id.CreateTaskBtn).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				// Verify network state and create new the new task
				if (!Network.isOnline(getActivity())) {
					AppUtils.showToast(getActivity(), "Invalid Network Connection", false);
					return;
				}

				if (TaskUtils.formIsValid(taskNameField, taskAmountField) && listener != null) {
					// Build a new task when submitting with the current values
					Task updatedTask = TaskUtils.newTask(taskNameField, taskAmountField);
					updatedTask.setCreatedDate(task.getCreatedDate());

					listener.saveTask(updatedTask);
				}
			}
		});

		return view;
	}

	@Override
	public void onAttach(Context context) {
		super.onAttach(context);

		if (context instanceof EditTaskListeners) {
			listener = (EditTaskListeners) context;
		}
	}

}
