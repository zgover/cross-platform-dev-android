// Zachary Gover
// CPMD - 1701
// TaskListItemListeners

package com.gover.zachary.crossplatformdev_android.interfaces;

import com.gover.zachary.crossplatformdev_android.models.Task;

public interface TaskListItemListeners {

	void listItemClick(Task task, String key);
	void listItemLongClick(String key);

}
