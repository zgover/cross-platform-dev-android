// Zachary Gover
// CPMD - 1701
// TaskUtils

package com.gover.zachary.crossplatformdev_android.models;

import android.widget.EditText;

import java.util.Date;

public class TaskUtils {

	public static String validateName(String name) {
		String msg = null;

		// Validate empties and via regex pattern for only numbers and letters
		if (name == null || name.trim().isEmpty()) {
			msg = "Please enter a correct value";
		} else if (name.trim().matches("^\\d*$")) {
			msg = "Please enter only numbers and letters";
		}

		return msg;
	}

	public static String validateAmount(String amount) {
		String msg = null;

		// Validate via regex pattern for only numbers
		if (!amount.trim().matches("^\\d{1,9}$")) {
			msg = "Please enter a valid number";
			return msg;
		}

		// Validate value can be parsed into an integer
		try {
			Integer.parseInt(amount.trim());
		} catch (NumberFormatException e) {
			e.printStackTrace();
			msg = "Please enter a valid number";
		}

		return msg;
	}

	public static boolean formIsValid(EditText name, EditText amount) {
		boolean isValid = true;
		String nameValidation = TaskUtils.validateName(name.getText().toString());
		String amountValidation = TaskUtils.validateAmount(amount.getText().toString());

		// Validate fields
		if (nameValidation != null) {
			name.setError(nameValidation);
			isValid = false;
		} else {
			name.setError(null);
		}

		if (amountValidation != null) {
			amount.setError(amountValidation);
			isValid = false;
		} else {
			amount.setError(null);
		}

		return isValid;
	}

	public static Task newTask(EditText nameField, EditText amountField) {
		Task task = new Task();
		String name = nameField.getText().toString().trim();
		int amount = Integer.parseInt(amountField.getText().toString().trim());
		Long createdDate = new Date().getTime();

		task.setName(name);
		task.setAmount(amount);
		task.setCreatedDate(createdDate);

		return task;
	}

}
