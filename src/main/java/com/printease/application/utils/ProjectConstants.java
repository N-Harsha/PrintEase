package com.printease.application.utils;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;


public final class ProjectConstants {

    // FIXME : Customize project constants for your application.
    public static final String APPLICATION_NAME = "PrintEase";
    public static final String APPLICATION_DESCRIPTION = "PrintEase is a web application that allows users to order printing services online.";
    public static final String APPLICATION_VERSION = "1.0.0";
    public static final String BASE_URL = "http://localhost:8080";
    public static final String DEFAULT_ENCODING = "UTF-8";
    public static final Locale DEFAULT_LOCALE = Locale.ENGLISH;

    public static final String FILE_TYPES = "image/jpeg, image/png, application/pdf";
    // order status constants
    public static final String CANCELLED_ORDER_STATUS = "Cancelled";
    public static final String REJECTED_ORDER_STATUS = "Rejected";
    public static final String PENDING_ORDER_STATUS = "Pending";
    public static final String ACCEPTED_ORDER_STATUS = "Accepted";
    public static final String IN_PROGRESS_ORDER_STATUS = "In Progress";
    public static final String COMPLETED_ORDER_STATUS = "Completed";

    public static final List<String> ORDER_STATUS_LIST = Arrays.asList(PENDING_ORDER_STATUS,
            ACCEPTED_ORDER_STATUS, IN_PROGRESS_ORDER_STATUS, COMPLETED_ORDER_STATUS);

    //user roles
    public static final String ROLE_CUSTOMER = "ROLE_CUSTOMER";
    public static final String ROLE_SERVICE_PROVIDER = "ROLE_SERVICE_PROVIDER";

    //exception message constants

    public static final String INVALID_DUE_DATE = "invalid_due_date";

    public static final String FILE_UPLOAD_ERROR = "file_upload_error";
    public static final String USER_NOT_FOUND = "user_not_found";
    public static final String INVALID_USER_ROLE = "invalid_user_role";
    public static final String FILE_NOT_FOUND = "file_not_found";
    public static final String FILE_CANNOT_BE_DOWNLOADED = "file_cannot_be_downloaded";
    public static final String INVALID_FILE_EXTENSION = "invalid_file_extension";
    public static final String ORDER_NOT_FOUND = "order_not_found";
    public static final String DOWNLOAD_NOT_AUTHORIZED = "download_not_authorized";
    public static final String USER_NOT_AUTHORIZED_FOR_STATUS_UPDATE = "user_not_authorized_for_status_update";
    public static final String ORDER_CANNOT_BE_CANCELLED = "order_cannot_be_cancelled";
    public static final String ORDER_STATUS_CANNOT_BE_UPDATED = "order_status_cannot_be_updated";
    public static final String ORDER_STATUS_IS_ALREADY_COMPLETED = "order_status_is_already_completed";
    public static final String FAVOURITE_SERVICE_PROVIDER_ALREADY_ADDED = "favorite_already_added";
    public static final String RATING_ALREADY_GIVEN = "rating_already_given";
    public static final String RATING_NOT_FOUND = "rating_not_found";

    //success message constants
    public static final String ORDER_CREATED_SUCCESSFULLY = "order_created_successfully";
    public static final String ORDER_STATUS_UPDATED = "order_status_updated";
    public static final String FAVOURITE_SERVICE_PROVIDER_ADDED = "favorite_added_successfully";
    public static final String FAVOURITE_SERVICE_PROVIDER_REMOVED = "favorite_removed_successfully";
    public static final String RATING_CREATED = "rating_created_successfully";
    public static final String RATING_UPDATED = "rating_updated_successfully";
    public static final String RATING_DELETED = "rating_deleted_successfully";

    private ProjectConstants() {

        throw new UnsupportedOperationException();
    }

}
