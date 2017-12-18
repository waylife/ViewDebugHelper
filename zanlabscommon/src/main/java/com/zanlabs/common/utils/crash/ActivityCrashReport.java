package com.zanlabs.common.utils.crash;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.ClipboardManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


import com.zanlabs.common.widget.ScrollingTextView;

import java.io.File;

/**
 * Created by rxread on 2015/11/16.
 */
public class ActivityCrashReport extends Activity {
    public static final String []REPORT_EMAIL = new String[]{"dobluetooth@163.com"};
    private TextView mMessageTv;
    private String mStringMsg;

    static public void actionReport(Context context, String message) {
        Intent intent = new Intent(context, ActivityCrashReport.class);
        intent.putExtra("log_msg", message);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMessageTv =new ScrollingTextView(this);
        setContentView(mMessageTv);

        setupViews();
    }

    private void setupViews() {
        mStringMsg = getIntent().getStringExtra("log_msg");
        mMessageTv.setText(mStringMsg);

        mMessageTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                copyToClipBoard(ActivityCrashReport.this, mStringMsg);
                Toast.makeText(ActivityCrashReport.this, "Crash message is copied to ClipBoard", Toast.LENGTH_SHORT).show();
            }
        });

        mMessageTv.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                sendEmail(ActivityCrashReport.this, REPORT_EMAIL, mStringMsg, null, null);
                return true;
            }
        });
    }

    /** 发送邮件，多人 */
    public static void sendEmail(Context context, String[] emailArr,
                                 String content, String filePath, String mimeType) {
        Intent email = new Intent(Intent.ACTION_SEND);
        email.putExtra(Intent.EXTRA_EMAIL, emailArr);
        email.setType("plain/text");
        email.putExtra(Intent.EXTRA_SUBJECT, "Crash Report");
        email.putExtra(Intent.EXTRA_TEXT, content);
        if (!TextUtils.isEmpty(filePath)) {
            email.setType("application/extension");
            email.putExtra(Intent.EXTRA_STREAM,
                    Uri.fromFile(new File(filePath)));
        }

        context.startActivity(Intent.createChooser(email,"Please choose one to send mail"));
    }

    /**
     * copy plain text to clip board<br/>
     * 复制文本到剪切板<br/>
     */
    public static void copyToClipBoard(Context context, CharSequence text) {
        ClipboardManager clipboardManager = (ClipboardManager) context
                .getSystemService(Context.CLIPBOARD_SERVICE);
        clipboardManager.setText(text);
    }

}
