// Zachary Gover
// CPMD - 1701
// TaskListItemViewHolder

package com.gover.zachary.crossplatformdev_android.models;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import com.gover.zachary.crossplatformdev_android.R;

public class TaskListItemViewHolder extends RecyclerView.ViewHolder {

	private TextView taskName;
	private TextView taskAmount;
	private TextView taskCreatedDate;

	public TaskListItemViewHolder(View itemView) {
		super(itemView);

		// Setup global properties
		taskName = (TextView) itemView.findViewById(R.id.TaskName);
		taskAmount = (TextView) itemView.findViewById(R.id.TaskAmount);
		taskCreatedDate = (TextView) itemView.findViewById(R.id.TaskCreatedDate);
	}

	public void bindToPost(Task task) {
		// Bind the task to the elements
		taskName.setText(task.getName());
		taskAmount.setText(Integer.toString(task.getAmount()));
		taskCreatedDate.setText(task.getShortCreatedDate());
	}

}
