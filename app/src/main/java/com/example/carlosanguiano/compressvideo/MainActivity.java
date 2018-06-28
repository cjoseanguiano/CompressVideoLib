package com.example.carlosanguiano.compressvideo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.carlosanguiano.compressvideo.compresscomplete.Example2;
import com.example.carlosanguiano.compressvideo.compresscomplete.Example3;


public class MainActivity extends AppCompatActivity {

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
        btnExample3.setOnClickListener(view -> {
//            Intent intent = new Intent(this, VideoSelectActivity.class);
//            startActivity(intent);
        });

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

}
