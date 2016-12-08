package com.my.myvideoplayer;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Administrator on 2016/12/8 0008.
 */

public class WelcomeActivity extends AppCompatActivity {

    private TextView mTV_welcomeNum;

    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            mTV_welcomeNum.setText(msg.what + "s");
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        mTV_welcomeNum = (TextView) findViewById(R.id.tv_welcomeNum);


        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 2; i >= 0; i--) {

                    try {
                        Thread.sleep(1000);
                        Message msg = new Message();
                        msg.what = i;

                        handler.sendEmptyMessage(msg.what);

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        }).start();

        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {

                Intent intent = new Intent();
                intent.setClass(WelcomeActivity.this, MainActivity.class);
                startActivity(intent);
                WelcomeActivity.this.finish();

            }
        };

        timer.schedule(timerTask, 3000);
    }
}
