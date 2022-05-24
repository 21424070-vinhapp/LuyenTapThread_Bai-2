package com.example.luyentapthread_bai_2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.luyentapthread_bai_2.databinding.ActivityMainBinding;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding mainBinding;
    LinearLayout.LayoutParams layoutParams;

    //bien tam de luu so luong button
    int n;
    //so phan tram tren edtNumber
    int percent;
    //gia tri tren cac button
    int value;

    Random random=new Random();

    //thread using Post
    Handler handler=new Handler(Looper.myLooper());
    Runnable forcegroundThread = new Runnable() {
        @Override
        public void run() {
            mainBinding.textView.setText(percent+"%");
            Button button=new Button(MainActivity.this);
            button.setText(value+"");
            button.setLayoutParams(layoutParams);
            mainBinding.llLayout.addView(button);
            if(percent==100)
            {
                Toast.makeText(MainActivity.this, "Da xong 100%", Toast.LENGTH_SHORT).show();
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addControls();

        addEvents();
    }

    private void addEvents() {
        mainBinding.btnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickButton();
            }
        });
    }

    private void onClickButton() {
        //remove cac button da tao neu chay lai chuong trinh
        //gan bien n la so luong Button can tao
        mainBinding.llLayout.removeAllViews();
        n=Integer.parseInt(mainBinding.edtNumber.getText().toString().trim());

        //thread
        Thread thread=new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < n; i++) {
                    percent=i*100/n;
                    value= random.nextInt(100);
                    handler.post(forcegroundThread);
                    SystemClock.sleep(100);
                }

                percent=100;
                handler.post(forcegroundThread);
            }
        });
        thread.start();
    }

    private void addControls() {
        mainBinding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mainBinding.getRoot());
        layoutParams=new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
    }
}