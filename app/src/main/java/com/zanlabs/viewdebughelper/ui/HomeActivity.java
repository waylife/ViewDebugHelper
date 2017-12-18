package com.zanlabs.viewdebughelper.ui;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.accessibility.AccessibilityManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.zanlabs.common.utils.AppUtil;
import com.zanlabs.common.utils.T;
import com.zanlabs.viewdebughelper.R;
import com.zanlabs.viewdebughelper.checkupdate.CheckUpdateUtil;
import com.zanlabs.viewdebughelper.notification.Notifier;
import com.zanlabs.viewdebughelper.service.FloatWindowService;
import com.zanlabs.viewdebughelper.ui.base.BaseActivity;
import com.zanlabs.viewdebughelper.util.AccessibilityServiceHelper;

public class HomeActivity extends BaseActivity implements View.OnClickListener {

    TextView mVersionTv;
    Button mActionBtn;
    Button mActiveServiceBtn;
    ImageView mLogoIv;
    boolean mIsServiceRunning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mVersionTv = $(R.id.home_version);
        mActionBtn = $(R.id.home_action_btn);
        mActiveServiceBtn = $(R.id.home_activte_service_btn);
        mLogoIv = $(R.id.home_center_logo);

        mLogoIv.setOnClickListener(this);
        mActionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateServiceState();
                if (mIsServiceRunning) {
                    FloatWindowService.stop(HomeActivity.this);
                } else {
                    FloatWindowService.start(HomeActivity.this);
                }
                Toast.makeText(HomeActivity.this, mIsServiceRunning ? "服务已停止" : "服务已启动", Toast.LENGTH_SHORT).show();
            }
        });

        mActiveServiceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AccessibilityServiceHelper.goServiceSettings(HomeActivity.this);
            }
        });
        checkApiGreaterThanLollipop();

        setValues();


//        AccessibilityManager mAccessibilityManager = (AccessibilityManager) getSystemService(Context.ACCESSIBILITY_SERVICE);
//        mAccessibilityManager.addAccessibilityStateChangeListener(new AccessibilityManager.AccessibilityStateChangeListener() {
//            @Override
//            public void onAccessibilityStateChanged(boolean enabled) {
//
//            }
//        });
        checkUpdate();
    }

    public void setValues() {
        mVersionTv.setText(String.format("%s-v%s", getString(R.string.app_name), AppUtil.getVersionName(this)));
    }

    private void updateServiceState() {
        mIsServiceRunning = FloatWindowService.isRunning();
        if (mActionBtn != null) {
            mActionBtn.setText(mIsServiceRunning ? "停止服务" : "启动服务");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateServiceState();
    }

    public void checkApiGreaterThanLollipop() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mActiveServiceBtn.setVisibility(View.VISIBLE);
            Toast.makeText(this, "Android 5.0(LOLLIPOP)之后需要开启ViewDebugHelper服务才能获取当前activity信息", Toast.LENGTH_SHORT).show();
        } else {
            mActiveServiceBtn.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.home_version:
                break;
            case R.id.home_center_logo:
                checkUpdate();
                break;
        }
    }


    boolean mCheckUpdating = false;

    private void checkUpdate() {
        if (mCheckUpdating) {
            return;
        }
        mCheckUpdating = true;
        CheckUpdateUtil.checkUpdate(this, new CheckUpdateUtil.OnCheckUpdateListener() {
            @Override
            public void onSuccess(CheckUpdateUtil.CheckUpdateItem item) {
                if (item.hasUpdate()) {
                    Notifier.getInstance().notifyAppUpdate(item);
                    T.l(HomeActivity.this, "应用有更新");
                } else {
                }
                mCheckUpdating = false;
            }

            @Override
            public void onFailed(CheckUpdateUtil.CheckUpdateItem item) {
                T.l(HomeActivity.this, "检查更新失败");
                mCheckUpdating = false;
            }
        });
    }

}
