package com.themvpguy.permissionsapp;

import android.Manifest;
import android.content.Context;

import com.rajeefmk.runtimepermissionhandler.PermissionUtils;

/**
 * Created by rjhashlearn on 14/9/16.
 */
public class Utils {

    public static final int OPTIONAL_REQUEST = 101;
    public static final int REQUIRED_REQUEST = 102;
    public static final int PERMISSION_LIST_REQUEST = 103;


    public static String[] getOptionalPermissions() {
        return new String[]{Manifest.permission.RECEIVE_SMS};
    }

    public static String[] getPermissionList() {
        return new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.GET_ACCOUNTS};
    }

    public static String[] getRequiredPermission() {
        return new String[]{Manifest.permission.READ_PHONE_STATE};
    }

    public static void onRequestPermissionsResult(Context mContext, int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case OPTIONAL_REQUEST:
                /**
                 * Passing null here instead of rationale message will result in permission being treated as denied
                 * instead of showing rationale message
                 */
                PermissionUtils.onRequestPermissionsResult(mContext, requestCode, permissions, grantResults, null);
                break;
            case REQUIRED_REQUEST:
                PermissionUtils.onRequestPermissionsResult(mContext, requestCode, permissions, grantResults, mContext.getString(R.string.message_permission_rationale));
                break;
            case PERMISSION_LIST_REQUEST:
                PermissionUtils.onRequestPermissionsResult(mContext, requestCode, permissions, grantResults, mContext.getString(R.string.message_permission_rationale));
                break;
        }

    }
}
