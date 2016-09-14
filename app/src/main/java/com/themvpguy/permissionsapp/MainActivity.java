package com.themvpguy.permissionsapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.Toast;

import com.themvpguy.runtimepermissionhandler.PermissionUtils;
import com.themvpguy.runtimepermissionhandler.PermissionsApi;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements PermissionsApi.PermissionCallback {

    @Bind(R.id.optional_permission)
    Button mOptionPermissionButton;

    @Bind(R.id.required_permission)
    Button mRequiredPermissionButton;

    @Bind(R.id.permission_list)
    Button mPermissionListButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        PermissionsApi.getInstance().setPermissionCallback(this);
    }

    /**
     * Permission rationale won't be shown when passed null. Its recommended to pass null for
     * those permissions which are optional.
     */
    @OnClick(R.id.optional_permission)
    void onOptionPermissionButtonClicked() {
        if (PermissionUtils.hasPermission(this, Utils.getOptionalPermissions(), null, Utils.OPTIONAL_REQUEST))
            showToastMessage(getString(R.string.message_optional_permission_granted));
    }

    @OnClick(R.id.required_permission)
    void onRequiredPermissionButtonClicked() {
        if (PermissionUtils.hasPermission(this, Utils.getRequiredPermission(),
                getString(R.string.message_permission_rationale), Utils.REQUIRED_REQUEST))
            showToastMessage(getString(R.string.message_required_permission_granted));

    }

    /**
     * If a list of permissions needs to be granted, until the user grants all the permissions in the list
     * the runtime dialogue will keep showing everytime user clicks on the button.
     * Also, those permissions which are already granted won't be asked again.
     **/
    @OnClick(R.id.permission_list)
    void onPermissionListButtonClicked() {
        if (PermissionUtils.hasPermission(this, Utils.getPermissionList(),
                getString(R.string.message_permission_rationale), Utils.PERMISSION_LIST_REQUEST))
            showToastMessage(getString(R.string.message_permission_list_granted));
    }

    private void showToastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (this.isFinishing())
            return;
        Utils.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
    }

    @Override
    public void onPermissionGranted(int requestCode) {
        switch (requestCode) {
            case Utils.OPTIONAL_REQUEST:
                showToastMessage(getString(R.string.message_optional_permission_granted));
                break;
            case Utils.REQUIRED_REQUEST:
                showToastMessage(getString(R.string.message_required_permission_granted));
                break;
            case Utils.PERMISSION_LIST_REQUEST:
                showToastMessage(getString(R.string.message_permission_list_granted));
                break;
        }
    }

    @Override
    public void onPermissionDenied(int requestCode) {
        if (requestCode == Utils.OPTIONAL_REQUEST)
            showToastMessage("Optional message denied but can continue.");
        //Based on denied permission request code, handle the case accordingly.
    }
}
