// Zachary Gover
// CPMD - 1701
// Network

package com.gover.zachary.crossplatformdev_android.models;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class Network {

	public static boolean isOnline(Context context) {
		ConnectivityManager man = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info = man.getActiveNetworkInfo();

		// Return true if we're connected
		return info != null && info.isConnectedOrConnecting();
	}

}
