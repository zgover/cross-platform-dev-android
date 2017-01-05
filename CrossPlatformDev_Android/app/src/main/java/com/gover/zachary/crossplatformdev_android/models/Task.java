// Zachary Gover
// CPMD - 1701
// Task

package com.gover.zachary.crossplatformdev_android.models;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Locale;

@IgnoreExtraProperties
public class Task implements Serializable {

	@Exclude
	public static final String TAG = "Task";
	@Exclude
	public static final String KEY = "Task.KEY";
	@Exclude
	public static final String OBJECT_NAME = "tasks";

	private String name;
	private int amount;
	private Long createdDate;

	public Task(){}

	public Task(String name, int amount, Long createdDate) {
		this.name = name;
		this.amount = amount;
		this.createdDate = createdDate;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public Long getCreatedDate() {
		return createdDate;
	}

	@Exclude
	public String getShortCreatedDate() {
		if (createdDate == null || createdDate == 0) { return null; }
		SimpleDateFormat sdf = new SimpleDateFormat("MM/yy", Locale.US);
		return sdf.format(createdDate);
	}

	public void setCreatedDate(Long createdDate) {
		this.createdDate = createdDate;
	}
}
