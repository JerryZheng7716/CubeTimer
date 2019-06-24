package com.estoon.cubetimer;

import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    TextView min, s, ms;
    int mins=0,ss=0,mss=0;
    RelativeLayout ctrl;
    boolean isStart;
    boolean isInit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);        //强制为横屏
        min = findViewById(R.id.min);
        s = findViewById(R.id.s);
        ms = findViewById(R.id.ms);
        ctrl = findViewById(R.id.ctrl);
        final Timer timer = new Timer();
        isInit=true;
        ctrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isInit&&!isStart){
                    Toast.makeText(MainActivity.this,"计时开始",Toast.LENGTH_SHORT).show();
                    isInit=false;
                    isStart=true;
                    Thread thread = new Thread(timer);
                    thread.start();
                }else if (isStart){
                    isStart=false;
                    Toast.makeText(MainActivity.this,"计时结束",Toast.LENGTH_SHORT).show();
                }else {
                    initTime();
                    isInit=true;
                    isStart=false;
                }
            }
        });
    }

    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String minString,sString,msString;
            if (mss<=9){
                msString="00"+mss;
            }else if (mss<=99){
                msString="0"+mss;
            }else {
                msString=""+mss;
            }
            if (ss<=9){
                sString="0"+ss;
            }else {
                sString=""+ss;
            }
            if (mins<=9){
                minString="0"+mins;
            }else {
                minString=""+mins;
            }
            ms.setText(msString);
            s.setText(sString);
            min.setText(minString);
        }
    };

    private class Timer implements Runnable {
        public void run() {
            while (isStart){
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                mss+=1;
                if (mss==999){
                    ss++;
                    mss=0;
                }
                if (ss==59){
                    mins++;
                    ss=0;
                }
                handler.sendEmptyMessage(0);
            }

        }
    }

    private void initTime(){
        mins=0;ss=0;mss=0;
        ms.setText("000");
        s.setText("00");
        min.setText("00");
    }
}
