// Zachary Gover
// CPMD - 1701
// FirebaseManager

package com.gover.zachary.crossplatformdev_android.models;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.gover.zachary.crossplatformdev_android.LoginRegisterActivity;
import com.gover.zachary.crossplatformdev_android.interfaces.LoginRegisterAuthListener;

public class FirebaseManager {

	private static final String TAG = "FirebaseManager";

	private static FirebaseAuth fbAuth;
	private static FirebaseDatabase fbDatabase;
	private static DatabaseReference fbDbReference;

	public static FirebaseAuth getFbAuth() {
		// Return and/or create the auth instance
		if (fbAuth == null) {
			fbAuth = FirebaseAuth.getInstance();
		}

		return fbAuth;
	}

	public static FirebaseDatabase getFbDatabase() {
		// Return and/or create the database instance
		if (fbDatabase == null) {
			fbDatabase = FirebaseDatabase.getInstance();
			fbDatabase.setPersistenceEnabled(true);
		}

		return fbDatabase;
	}

	public static DatabaseReference getFbDbReference() {
		// Return and/or create the database reference
		if (fbDbReference == null) {
			fbDbReference = getFbDatabase().getReference();
		}

		return fbDbReference;
	}

	public static Query getUserTasksQuery() {
		// Build query to fetch only the current users tasks
		Query tasks = getFbDbReference().child(getUserId())
			.child(com.gover.zachary.crossplatformdev_android.models.Task.OBJECT_NAME)
			.orderByChild("createdDate");

		return tasks;
	}

	public static void deleteTask(String key) {
		getFbDbReference().child(getUserId())
			.child(com.gover.zachary.crossplatformdev_android.models.Task.OBJECT_NAME)
			.child(key)
			.removeValue();
	}

	public static String getUserId() {
		return getFbAuth().getCurrentUser().getUid();
	}

	public static void logout(Activity activity) {
		// Logout user, clear activity history and present login/register activity.
		Intent intent = new Intent(activity, LoginRegisterActivity.class)
			.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
			.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

		getFbAuth().signOut();
		activity.startActivity(intent);
		activity.finish();
	}

	public static AuthCompleteListener getAuthCompleteListener(@NonNull Context context) {
		return new AuthCompleteListener(context);
	}

	public static FirebaseAuthListener getFirebaseAuthListener(@NonNull LoginRegisterAuthListener listener) {
		return new FirebaseAuthListener(listener);
	}

	private static class FirebaseAuthListener implements FirebaseAuth.AuthStateListener {

		private LoginRegisterAuthListener listener;

		public FirebaseAuthListener(LoginRegisterAuthListener listener) {
			this.listener = listener;
		}

		@Override
		public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
			FirebaseUser user = firebaseAuth.getCurrentUser();

			if (user != null) {
				// User is signed in
				Log.d(TAG, "FirebaseAuthListener:onAuthStateChanged:signed_in:" + user.getUid());
			} else {
				// User is signed out
				Log.d(TAG, "FirebaseAuthListener:onAuthStateChanged:signed_out");
			}

			AppUtils.dismissProgress();

			if (user != null) { listener.userLoggedIn(); }
		}
	}

	private static class AuthCompleteListener implements OnCompleteListener<AuthResult> {

		private Context context;

		public AuthCompleteListener(Context context) {
			this.context = context;
		}

		@Override
		public void onComplete(@NonNull Task<AuthResult> task) {
			Log.d(TAG, "AuthCompletionListener:onComplete:" + task.isSuccessful());

			// If sign in fails, display a message to the user.
			if (!task.isSuccessful()) {
				Log.w(TAG, "AuthCompletionListener:isNotSuccessful:");
				AppUtils.showToast(context, "Authentication failed...", false);
				task.getException().printStackTrace();
			}

			AppUtils.dismissProgress();
		}
	}

}
