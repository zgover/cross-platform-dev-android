// Zachary Gover
// CPMD - 1701
// CreateTaskFragment

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
import com.gover.zachary.crossplatformdev_android.interfaces.CreateTaskListeners;
import com.gover.zachary.crossplatformdev_android.models.AppUtils;
import com.gover.zachary.crossplatformdev_android.models.Network;
import com.gover.zachary.crossplatformdev_android.models.Task;
import com.gover.zachary.crossplatformdev_android.models.TaskUtils;

public class CreateTaskFragment extends Fragment {

	private static final String TAG = "CreateTaskFragment";

	private CreateTaskListeners listener;
	private EditText taskNameField;
	private EditText taskAmountField;

	public static CreateTaskFragment newInstance() {
		return new CreateTaskFragment();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_create_edit_task, container, false);

		taskNameField = (EditText) view.findViewById(R.id.TaskNameField);
		taskAmountField = (EditText) view.findViewById(R.id.TaskAmountField);

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
					Task task = TaskUtils.newTask(taskNameField, taskAmountField);

					listener.createNewTask(task);
				}
			}
		});

		return view;
	}

	@Override
	public void onAttach(Context context) {
		super.onAttach(context);

		if (context instanceof CreateTaskListeners) {
			listener = (CreateTaskListeners) context;
		}
	}
}
