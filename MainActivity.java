package com.example.vioce.papb_thread;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

//    private ProgressBar progress;
    private TextView text;
    private Button btn, btnStop, btnResume;
    private Thread bgthread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        progress = (ProgressBar) findViewById(R.id.progressBar1);
        text = (TextView) findViewById(R.id.textView1);
        btn = (Button) findViewById(R.id.button1);
        btnStop = (Button) findViewById(R.id.stopButton);
//        btnResume = (Button) findViewById(R.id.resumeButton);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Buat Thread baru setiap kali tombol start progress di klik
                //Setiap kali thread akan dijalankan, harus dibuat baru,
                //Thread yang sudah finish/terminated tidak bisa dijalankan kembali
                if (bgthread == null || bgthread.getState() == Thread.State.TERMINATED) {
                    Runnable runnable = new Runnable() {
                        @Override
                        public void run() {
                            try {
                                for (int i = 0;; i++) {
                                    //Simulating something timeconsuming
                                    Thread.sleep(500);

                                    text.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            Random rand = new Random();
                                            final int randNum = rand.nextInt(10);
                                            text.setText(String.valueOf(randNum));
                                        }
                                    });

//                            progress.post(new Runnable() {
//                                @Override
//                                public void run() {
//                                    text.setText("Updating " + value + "/10");
//                                    progress.setProgress(value);
//                                }
//                            });
                                }
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    };
                    bgthread = new Thread(runnable);
                    bgthread.start();
                }
            }
        });

        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                synchronized (bgthread) {
                    try {
                        bgthread.wait(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

//        btnResume.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                bgthread.notifyAll();
//            }
//        });
    }
}
