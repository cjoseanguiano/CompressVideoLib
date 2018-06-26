package com.example.carlosanguiano.compressvideo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.text.DecimalFormat;


public class Example2 extends AppCompatActivity {

    private TextView fileSize;
    private TextView filePath;
    private static String path1 = "/storage/81B6-1202/DCIM/Camera/20180625_132406.mp4"; // 1 Min
    private static String path2 = "/storage/81B6-1202/DCIM/Camera/20180625_133936.mp4"; //4 Second
    private static String path3 = "/storage/81B6-1202/DCIM/Camera/20180625_132406.mp4"; // 42 Min
    private static String path4 = "/storage/emulated/0/VideoCompressor/Temp/20180625_184805.mp4"; // 1 Sec
    private Button validate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example2);
        initView();
        filePath.setText(path1);
        fileSize.setText(getFileSize(path2));
        String pathCompleted = formatFileSize();
        Toast.makeText(this, " " + pathCompleted, Toast.LENGTH_SHORT).show();

        validate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
    }

    private void initView() {
        fileSize = findViewById(R.id.size);
        filePath = findViewById(R.id.path);
        validate = findViewById(R.id.validate);
    }

    private String getFileSize(String path) {
        File file = new File(path);
        if (!file.exists()) {
            return "0 MB";
        } else {
            long size = file.length();
            return (size / 1024f) / 1024f + " MB";
        }
    }

/*
    public static String getStringSizeLengthFile() {
        File files = new File(path2);
        if (files.exists()){

            size = files.length();
        }

        DecimalFormat df = new DecimalFormat("0.00");

        float sizeKb = 1024.0f;
        float sizeMo = sizeKb * sizeKb;
        float sizeGo = sizeMo * sizeKb;
        float sizeTerra = sizeGo * sizeKb;


        if (size < sizeMo) {
            return df.format(size / sizeKb) + " KB";
        } else if (size < sizeGo) {
            return df.format(size / sizeMo) + " MB";
        } else if (size < sizeTerra) {
            return df.format(size / sizeGo) + " GB";
        }
        return "0";
    }
*/

    public static String formatFileSize() {
        File file = new File(path4);
        long size = 0;

        if (file.exists()) {
            size = file.length();
        }
        String hrSize = null;

        double b = size;
        double k = size / 1024.0;
        double m = ((size / 1024.0) / 1024.0);
        double g = (((size / 1024.0) / 1024.0) / 1024.0);
        double t = ((((size / 1024.0) / 1024.0) / 1024.0) / 1024.0);

        DecimalFormat dec = new DecimalFormat("0.00");

        if (t > 1) {
            hrSize = dec.format(t).concat(" TB");
        } else if (g > 1) {
            hrSize = dec.format(g).concat(" GB");
        } else if (m > 1) {
            hrSize = dec.format(m).concat(" MB");
        } else if (k > 1) {
            hrSize = dec.format(k).concat(" KB");
        } else {
            hrSize = dec.format(b).concat(" Bytes");
        }

        return hrSize;
    }


}
