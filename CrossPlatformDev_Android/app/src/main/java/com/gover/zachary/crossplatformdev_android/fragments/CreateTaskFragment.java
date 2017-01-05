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
import com.gover.zachary.crossplatformdev_android.models.Task;

import java.util.Date;

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
		View view = inflater.inflate(R.layout.fragment_create_task, container, false);

		taskNameField = (EditText) view.findViewById(R.id.TaskNameField);
		taskAmountField = (EditText) view.findViewById(R.id.TaskAmountField);

		view.findViewById(R.id.CreateTaskBtn).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if (formIsValid() && listener != null) {
					// Build a new task when submitting with the current values
					Task task = new Task();
					String name = taskNameField.getText().toString().trim();
					int amount = Integer.parseInt(taskAmountField.getText().toString().trim());
					Long createdDate = new Date().getTime();

					task.setName(name);
					task.setAmount(amount);
					task.setCreatedDate(createdDate);

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

	private boolean formIsValid() {
		boolean isValid = true;

		// Validate fields
		if (taskNameField.getText().toString().trim().isEmpty()) {
			taskNameField.setError("Please enter a value");
			isValid = false;
		} else {
			taskNameField.setError(null);
		}

		if (taskAmountField.getText().toString().trim().isEmpty()) {
			taskAmountField.setError("Please enter a value");
			isValid = false;
		} else {
			taskAmountField.setError(null);
		}

		if (!isValid) {
			AppUtils.showToast(getActivity(), "Please complete the form", false);
		}

		return isValid;
	}
}
