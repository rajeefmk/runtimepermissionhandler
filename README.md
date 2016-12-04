[![Release](https://jitpack.io/v/rajeefmk/runtimepermissionhandler.svg)](https://jitpack.io/#rajeefmk/runtimepermissionhandler)

# Runtime Permission Handler

The library came out as an effort to reduce the boilerplate required for getting runtime permissions for Android devices running OS 6.0 and above.

The developer can now focus on handling the granted and denied state of the permission rather than dealing with the permission flow and dialogues. 

## Integrating into your project

This library is available in [JitPack.io](https://jitpack.io/) repository.
To use it make sure that repository's url is added to the `build.gradle` file in your app:

```groovy
repositories {
    mavenCentral()
    maven { url "https://jitpack.io" }
}

dependencies {
    compile 'com.github.rajeefmk:runtimepermissionhandler:1.0.1'

    // Other dependencies your app might use
}
```
## Usage

A sample implementation can be found here [Sample app](https://github.com/rajeefmk/runtimepermissionhandler/tree/master/app).  You can also make use of the [Utils.java](https://github.com/rajeefmk/runtimepermissionhandler/blob/master/app/src/main/java/com/themvpguy/permissionsapp/Utils.java) class in the sample repo for avoiding more boilerplates.

```java
public class MainActivity extends AppCompatActivity implements PermissionsApi.PermissionCallback {

    private static final int SMS_REQUEST = 101;
    private static final int STORAGE_REQUEST = 102;
    private static final int SOME_LIST_OF_PERMISSION = 103;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        PermissionsApi.getInstance().setPermissionCallback(this);
    }
```
The permission can be checked by calling the method ```Permissionutils.hasPermission(Context, String[] of permission,
permission rationale message, REQUEST CODE}.```

The method returns true only if all requested permission is granted by the user. If any one permission is denied, this method will return false. 

The 3rd param - permission rationale message - determines if the requested permission is optional (if message is null) or required ( message is provided). 

```java
/**
     * Permission rationale won't be shown when passed null. Its recommended to pass null for
     * those permissions which are optional.
     */
    private void onSmsPermission() {
        if (PermissionUtils.hasPermission(this, {Manifest.permission.RECEIVE_SMS}, 
        null, OPTIONAL_REQUEST))
        //Permission is already granted. Continue exuecuting your code.
    }

    private void onStoragePermission() {
        if (PermissionUtils.hasPermission(this,{Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE},
                getString(R.string.message_permission_rationale), REQUIRED_REQUEST))
            showToastMessage(getString(R.string.message_required_permission_granted));

    }

    /**
     * If a list of permissions needs to be granted, until the user grants 
     * all the permissions in the list the runtime dialogue will keep showing 
     * everytime user clicks on the button. Also, those permissions which are 
      * already granted won't be asked again.
     **/
    private void someListOfPermissions() {
        if (PermissionUtils.hasPermission(this, Utils.getPermissionList(),
                getString(R.string.message_permission_rationale), SOME_LIST_OF_PERMISSION))
            showToastMessage(getString(R.string.message_permission_list_granted));
    }
```

Override onRequestPermissionResult for handling permission response from user. 
```java
   
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (this.isFinishing())
            return;
       switch (requestCode) {
            case SMS_REQUEST:
                /**
                 * Passing null here instead of rationale message will result in 
                  * permission being treated as denied instead of showing rationale message
                 */
                PermissionUtils.onRequestPermissionsResult(mContext, requestCode,
                permissions, grantResults, null);
                break;
            case STORAGE_REQUEST:
                PermissionUtils.onRequestPermissionsResult(mContext, requestCode, 
                permissions, grantResults, mContext.getString(R.string.message_permission_rationale));
                break;
            case SOME_LIST_OF_PERMISSION:
                PermissionUtils.onRequestPermissionsResult(mContext, requestCode, 
                permissions, grantResults, mContext.getString(R.string.message_permission_rationale));
                break;
        }
    }

```
These methods are implemented for handling permission granted and denied cases. 
```java
    @Override
    public void onPermissionGranted(int requestCode) {
        switch (requestCode) {
            case SMS_REQUEST:
                showToastMessage(getString(R.string.message_optional_permission_granted));
                break;
            case STORAGE_REQUEST:
                showToastMessage(getString(R.string.message_required_permission_granted));
                break;
            case SOME_LIST_OF_PERMISSION:
                showToastMessage(getString(R.string.message_permission_list_granted));
                break;
        }
    }

    @Override
    public void onPermissionDenied(int requestCode) {
        if (requestCode == SMS_REQUEST)
            showToastMessage("Sms permission denied but can continue.");
        //Based on denied permission request code, handle the case accordingly.
    }
```


## Help

#### Bugs, Feature requests
Found a bug? Something that's missing? Feedback is an important part of improving the project, so please [open an issue](https://github.com/rajeefmk/runtimepermissionhandler/issues).

#### Code
Fork this project and start working on your own feature branch. When you're done, send a Pull Request to have your suggested changes merged into the master branch by the project's collaborators. Read more about the [GitHub flow](https://guides.github.com/introduction/flow/).


License
-------

    Copyright 2017 Muhammed Rajeef M K

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
