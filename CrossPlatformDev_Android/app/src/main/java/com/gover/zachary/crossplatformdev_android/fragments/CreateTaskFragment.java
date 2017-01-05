// Zachary Gover
// CPMD - 1701
// CreateTaskFragment

package com.gover.zachary.crossplatformdev_android.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.gover.zachary.crossplatformdev_android.R;

public class CreateTaskFragment extends Fragment {

	private static final String TAG = "CreateTaskFragment";

	public static CreateTaskFragment newInstance() {
		return new CreateTaskFragment();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_create_task, container, false);
	}
}
