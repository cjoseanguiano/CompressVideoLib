package com.example.carlosanguiano.compressvideo;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.carlosanguiano.compressvideo.databinding.ActivityTrimmerLayoutBindingImpl;
import com.example.carlosanguiano.compressvideo.interfaces.TrimVideoListener;


public class MainActivity extends AppCompatActivity implements TrimVideoListener {

    private Button btnExample1;
    private Button btnExample2;
    private Button btnExample3;
    private Button btnExample4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        btnExample1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(MainActivity.this, Example1.class);
//                startActivity(intent);
            }
        });

        btnExample2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Example2.class);
                startActivity(intent);
            }
        });
     /*   btnExample3.setOnClickListener(view -> {
            Intent intent = new Intent(MainActip`s, VideoSelectActivity.class);
            startActivity(intent);
        });*/

        btnExample4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Example3.class);
                startActivity(intent);
            }
        });
    }

    private void initView() {
        btnExample1 = findViewById(R.id.btnExample1);
        btnExample2 = findViewById(R.id.btnExample2);
        btnExample3 = findViewById(R.id.btnExample3);
        btnExample4 = findViewById(R.id.btnExample4);
    }

    @Override
    public void onStartTrim() {

    }

    @Override
    public void onFinishTrim(String url) {

    }

    @Override
    public void onCancel() {

    }
}
