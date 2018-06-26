package com.example.carlosanguiano.compressvideo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Button btnExample1;
    private Button btnExample2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();


        btnExample1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Example1.class);
                startActivity(intent);
            }
        });

        btnExample2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Example2.class);
                startActivity(intent);
            }
        });
    }

    private void initView() {
        btnExample1 = findViewById(R.id.btnExample1);
        btnExample2 = findViewById(R.id.btnExample2);
    }

/*
    private void startAct(Class act) {
        Intent intent = new Intent(this, act);
        this.startActivity(intent);
    }
*/
}
