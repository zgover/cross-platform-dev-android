// Zachary Gover
// CPMD - 1701
// LoginRegisterFragment

package com.gover.zachary.crossplatformdev_android.fragments;

import android.support.v4.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.gover.zachary.crossplatformdev_android.R;
import com.gover.zachary.crossplatformdev_android.interfaces.LoginRegisterBtnListeners;
import com.gover.zachary.crossplatformdev_android.models.AppUtils;
import com.gover.zachary.crossplatformdev_android.models.Network;

public class LoginRegisterFragment extends Fragment {

	private static final String TAG = "LoginRegisterFragment";

	private LoginRegisterBtnListeners listener;
	private TextView emailField;
	private TextView pwField;

	public static LoginRegisterFragment newInstance() {
		return new LoginRegisterFragment();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// Setup the fragment view and click events
		View view = inflater.inflate(R.layout.fragment_login_register, container, false);

		emailField = (TextView) view.findViewById(R.id.EmailField);
		pwField = (TextView) view.findViewById(R.id.PasswordField);

		view.findViewById(R.id.LoginBtn).setOnClickListener(new LoginClickListener());
		view.findViewById(R.id.RegisterBtn).setOnClickListener(new RegisterClickListener());

		return view;
	}

	@Override
	public void onAttach(Context context) {
		super.onAttach(context);

		if (context instanceof LoginRegisterBtnListeners) {
			listener = (LoginRegisterBtnListeners) context;
		}
	}

	private boolean validate() {
		// Validate network and form
		if (!Network.isOnline(getActivity())) {
			AppUtils.showToast(getActivity(), "Invalid network connection", false);
			return false;
		} else if (!validateForm()) {
			AppUtils.showToast(getActivity(), "Please complete the form", false);
			return false;
		}

		return true;
	}

	private boolean validateForm() {
		// Get current values and make sure they are not empty
		String email = emailField.getText().toString().trim();
		String pw = pwField.getText().toString().trim();
		boolean isValid = false;

		if (email.isEmpty()) {
			emailField.setError("Please enter a valid email");
		} else {
			emailField.setError(null);
			isValid = true;
		}

		if (pw.isEmpty()) {
			pwField.setError("Please enter a valid password");
		} else {
			pwField.setError(null);
			isValid = true;
		}

		return isValid;
	}

	private class LoginClickListener implements View.OnClickListener {

		@Override
		public void onClick(View view) {
			// Validate network and form then send back to activity
			if (validate() && listener != null) {
				listener.loginBtnClicked(emailField.getText().toString().trim(),
										 pwField.getText().toString().trim());
			}
		}
	}

	private class RegisterClickListener implements View.OnClickListener {

		@Override
		public void onClick(View view) {
			// Validate network and form then send back to activity
			if (validate() && listener != null) {
				listener.registerBtnClicked(emailField.getText().toString().trim(),
										 pwField.getText().toString().trim());
			}
		}
	}
}
