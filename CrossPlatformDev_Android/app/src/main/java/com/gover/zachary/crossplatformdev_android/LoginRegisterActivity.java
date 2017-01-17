// Zachary Gover
// CPMD - 1701
// LoginRegisterActivity

package com.gover.zachary.crossplatformdev_android;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.gover.zachary.crossplatformdev_android.fragments.LoginRegisterFragment;
import com.gover.zachary.crossplatformdev_android.interfaces.LoginRegisterAuthListeners;
import com.gover.zachary.crossplatformdev_android.interfaces.LoginRegisterBtnListeners;
import com.gover.zachary.crossplatformdev_android.models.AppUtils;
import com.gover.zachary.crossplatformdev_android.models.FirebaseManager;
import com.gover.zachary.crossplatformdev_android.models.Network;

public class LoginRegisterActivity extends AppCompatActivity implements LoginRegisterBtnListeners,
																		LoginRegisterAuthListeners {

	private static final String TAG = "LoginRegisterActivity";

	private FirebaseAuth.AuthStateListener mAuthListener;

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
		mAuthListener = FirebaseManager.getFirebaseAuthListener(this);

	}

	@Override
	public void onStart() {
		super.onStart();
		FirebaseManager.getFbAuth().addAuthStateListener(mAuthListener);
	}

	@Override
	public void onStop() {
		super.onStop();
		if (mAuthListener != null) {
			FirebaseManager.getFbAuth().removeAuthStateListener(mAuthListener);
		}
	}

	@Override
	public void loginBtnClicked(String email, String password) {
		// Login to firebase with creds
		AppUtils.showProgress(this, "Please wait", "Logging in...");
		FirebaseManager.getFbAuth().signInWithEmailAndPassword(email, password)
			.addOnCompleteListener(FirebaseManager.getAuthCompleteListener(this));
	}

	@Override
	public void registerBtnClicked(String email, String password) {
		// Register to firebase with creds
		AppUtils.showProgress(this, "Please wait", "Registering...");
		FirebaseManager.getFbAuth().createUserWithEmailAndPassword(email, password)
			.addOnCompleteListener(FirebaseManager.getAuthCompleteListener(this));
	}

	@Override
	public void userLoggedIn() {
		// Login/Registration has succeeded proceed to next screen
		Intent intent = new Intent(this, TaskListActivity.class);
		startActivity(intent);
	}

	private void setupFragment() {
		// Open fragment on the content frame layout
		LoginRegisterFragment frag = LoginRegisterFragment.newInstance();
		getSupportFragmentManager().beginTransaction()
			.replace(R.id.Content, frag, TAG).commit();
	}
}