package com.themvpguy.runtimepermissionhandler;

/**
 * Created by rmk on 22/2/16.
 */
public class PermissionsApi {
    private static PermissionsApi mInstance;
    private PermissionCallback permissionCallback;

    private PermissionsApi() {

    }

    public static PermissionsApi getInstance() {
        if (mInstance == null) {
            mInstance = new PermissionsApi();
        }
        return mInstance;
    }

    public void setPermissionCallback(PermissionCallback permissionCallback) {
        this.permissionCallback = permissionCallback;
    }

    public void onPermissionsGranted(int requestCode) {
        permissionCallback.onPermissionGranted(requestCode);
    }

    public void onPermissionsDenied(int requestCode) {
        permissionCallback.onPermissionDenied(requestCode);
    }

    public interface PermissionCallback {
        void onPermissionGranted(int requestCode);

        void onPermissionDenied(int requestCode);
    }
}
