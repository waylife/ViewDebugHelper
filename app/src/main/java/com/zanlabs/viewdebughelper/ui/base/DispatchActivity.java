package com.zanlabs.viewdebughelper.ui.base;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.zanlabs.viewdebughelper.checkupdate.CheckUpdateUtil;

/**
 * Created by rxread on 8/3/16.
 */
public class DispatchActivity extends BaseActivity {
    public final static int TYPE_CHECK_UPDATE = 1;
    public final static String S_TYPE_STRING = "_dispatch_type";
    public final static String S_DISPATCH_DATA = "_dispatch_data";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        handleDispatch(getIntent());

        this.finish();
    }

    public void handleDispatch(Intent intent) {
        if (intent == null) {
            return;
        }
        int type = intent.getIntExtra(S_TYPE_STRING, 0);
        switch (type) {
            case TYPE_CHECK_UPDATE:
                CheckUpdateUtil.CheckUpdateItem item = (CheckUpdateUtil.CheckUpdateItem) intent.getSerializableExtra(S_DISPATCH_DATA);
                if (item != null) {
                    Intent it = new Intent(Intent.ACTION_VIEW);
                    it.setData(Uri.parse(item.downloadUrl));
                    it = Intent.createChooser(it, null);
                    startActivity(it);
                }
                break;
        }
    }

}
