// Zachary Gover
// CPMD - 1701
// LoginRegisterFragment

package com.gover.zachary.crossplatformdev_android.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.gover.zachary.crossplatformdev_android.R;
import com.gover.zachary.crossplatformdev_android.interfaces.TaskListItemListeners;
import com.gover.zachary.crossplatformdev_android.models.FirebaseManager;
import com.gover.zachary.crossplatformdev_android.models.Task;
import com.gover.zachary.crossplatformdev_android.models.TaskListItemViewHolder;

public class TaskListFragment extends Fragment {

	private static final String TAG = "TaskListFragment";

	private TaskListItemListeners listener;
	private FirebaseRecyclerAdapter<Task, TaskListItemViewHolder> mAdapter;
	private RecyclerView mRecycler;
	private LinearLayoutManager mManager;

	public static TaskListFragment newInstance() {
		return new TaskListFragment();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_list_view, container, false);

		setupListView(view);

		return view;
	}

	@Override
	public void onAttach(Context context) {
		super.onAttach(context);

		if (context instanceof TaskListItemListeners) {
			listener = (TaskListItemListeners) context;
		}
	}

	private void setupListView(View view) {
		// Setup the Firebase recycler view and populate the list
		mRecycler = (RecyclerView) view.findViewById(R.id.TaskList);
		mManager = new LinearLayoutManager(getActivity());
		mManager.setReverseLayout(true);
		mManager.setStackFromEnd(true);
		mRecycler.setLayoutManager(mManager);

		mAdapter = new FirebaseRecyclerAdapter<Task, TaskListItemViewHolder>
			(Task.class, R.layout.task_list_item,
			 TaskListItemViewHolder.class,
			 FirebaseManager.getUserTasksQuery()) {

			@Override
			protected void populateViewHolder(final TaskListItemViewHolder viewHolder, final Task task, final int position) {

				// Set click and long click listener for the list item
				DatabaseReference reference = getRef(position);
				final String key = reference.getKey();

				viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						Log.d(TAG, "onActivityCreated:FirebaseRecyclerAdapter:OnClickListener:" + task);

						if (listener != null) {
							listener.listItemClick(task, key);
						}
					}
				});

				viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
					@Override
					public boolean onLongClick(View view) {
						listener.listItemLongClick(key);
						return false;
					}
				});

				// Bind Post to ViewHolder
				Log.d(TAG, "onActivityCreated:FirebaseRecyclerAdapter:populateViewHolder:" + task);
				viewHolder.bindToPost(task);
			}
		};

		mRecycler.setAdapter(mAdapter);
	}
}
