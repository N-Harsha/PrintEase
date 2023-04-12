package com.printease.application.utils;

import java.util.Locale;


public final class ProjectConstants {

	// FIXME : Customize project constants for your application.

	public static final String DEFAULT_ENCODING = "UTF-8";
	public static final Locale DEFAULT_LOCALE = Locale.ENGLISH;

	public static final String FILE_TYPES = "image/jpeg, image/png, application/pdf";
	public static final String ACCEPTED_ORDER_STATUS = "Accepted";
	public static final String REJECTED_ORDER_STATUS = "Rejected";
	public static final String PENDING_ORDER_STATUS = "Pending";
	public static final String CANCELLED_ORDER_STATUS = "Cancelled";
	public static final String COMPLETED_ORDER_STATUS = "Completed";
	public static final String IN_PROGRESS_ORDER_STATUS = "In Progress";

	//exception message constants

	public static final String INVALID_DUE_DATE = "invalid_due_date";

	public static final String FILE_UPLOAD_ERROR = "file_upload_error";
	//success message constants

	public static final String ORDER_CREATED_SUCCESSFULLY = "order_created_successfully";


	private ProjectConstants() {

		throw new UnsupportedOperationException();
	}

}
