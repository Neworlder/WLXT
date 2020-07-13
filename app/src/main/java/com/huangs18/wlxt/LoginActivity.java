package com.huangs18.wlxt;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.sip.SipSession;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.huangs18.Login.Login;
import com.huangs18.Login.AssignmentInfo;
import  com.huangs18.Layouts.HomeworkLayout;

import java.util.Vector;



public class LoginActivity extends Activity {
    private Handler mainThreadHandler;
    private Handler loginThreadHandler;
    private Bundle bundle;

    class loginThread extends Thread {
        @SuppressLint("HandlerLeak")
        @Override
        public void run() {
            super.run();
            Looper.prepare();
            loginThreadHandler = new Handler() {
                @Override
                public void handleMessage(@NonNull Message msg) {
                    super.handleMessage(msg);
                    switch (msg.what) {
                        case 1:
                            Bundle bundle1 = (Bundle)msg.obj;
                            Vector<AssignmentInfo> assignmentVec = null;
                            try {
                                assignmentVec = Login.run(bundle1.getString("userName"), bundle1.getString("password"));
                            } catch (Exception e) {
                                AlertDialog.Builder builder  = new AlertDialog.Builder(LoginActivity.this);
                                builder.setTitle("登录错误" );
                                builder.setMessage("用户名或密码错误。" );
                                builder.setPositiveButton("返回", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        Intent backIntent = new Intent(LoginActivity.this, MainActivity.class);
                                        startActivity(backIntent);
                                    }
                                });
                                builder.show();
                                return;
                            }
                            Message message = new Message();
                            message.what = 2;
                            message.obj = assignmentVec;
                            mainThreadHandler.sendMessage(message);
                            break;
                    }
                }
            };
            Message handlerMessage = new Message();
            handlerMessage.what = 0;
            mainThreadHandler.sendMessage(handlerMessage);
            Looper.loop();
        }
    }

    @SuppressLint("HandlerLeak")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Intent intent = getIntent();
        bundle = intent.getExtras();
        LinearLayout mainLayout = findViewById(R.id.loginMainLayout);
        mainThreadHandler = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case 2:
                        Vector<AssignmentInfo> assignmentVec = (Vector<AssignmentInfo>) msg.obj;
                        for (AssignmentInfo Ainfo : assignmentVec) {
                            LinearLayout mainLayout = findViewById(R.id.loginMainLayout);
                            mainLayout.addView(new HomeworkLayout(LoginActivity.this, Ainfo.courseName, Ainfo.assignmentTitle, Ainfo.date));
                        }
                        break;
                    case 0:
                        Message bundleMessage = new Message();
                        bundleMessage.what = 1;
                        bundleMessage.obj = LoginActivity.this.bundle;
                        loginThreadHandler.sendMessage(bundleMessage);
                }

            }
        };
        new loginThread().start();
    }
}
