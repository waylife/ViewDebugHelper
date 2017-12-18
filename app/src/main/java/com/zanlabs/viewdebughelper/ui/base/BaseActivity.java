package com.zanlabs.viewdebughelper.ui.base;

import android.support.v4.app.FragmentActivity;
import android.view.View;

/**
 * Created by rxread on 8/2/16.
 */
public class BaseActivity extends FragmentActivity {

    public <T extends View> T $(int resId) {
        return (T) findViewById(resId);
    }
}
