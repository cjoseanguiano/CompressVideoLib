package com.example.carlosanguiano.compressvideo;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.carlosanguiano.compressvideo.features.trim.VideoTrimmerActivity;
import com.example.carlosanguiano.compressvideo.interfaces.TrimVideoListener;
import com.example.carlosanguiano.compressvideo.utils.TrimVideoUtil;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;


public class Example2 extends AppCompatActivity {

    private TextView fileSize;
    private TextView filePath;
    private static String path1 = "/storage/81B6-1202/DCIM/Camera/20180623_215118.mp4";
    //    private static String path2 = "/storage/81B6-1202/DCIM/Camera/Test01.mp4";
    private Button validate;
    private int limitSize = 16777216; // 16 MB;
    private long lengthFile = 0;
    private File file;
    public TrimVideoListener mOnTrimVideoListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example2);
        initView();
        filePath.setText(path1);
        file = new File(path1);
        int sizeFile = getFileSize(file);
        fileSize.setText("" + sizeFile);

        validate.setOnClickListener(view -> {
            Log.i("onCreate", "onCreate: " + lengthFile);
            Log.i("onCreate", "onCreate: " + limitSize);
            if (lengthFile > limitSize) {
                VideoTrimmerActivity.call(Example2.this, path1);
            } else {
//                MediaPlayer mediaPlayer = new MediaPlayer();
//                mediaPlayer = MediaPlayer.create(Example2.this, Uri.parse("file://" + file.absolutePath))
                MediaPlayer mediaPlayer = MediaPlayer.create(Example2.this, Uri.parse("file://" + file.getAbsolutePath()));
                if (mediaPlayer != null) {
                    mediaPlayer.getDuration();
                    Log.i("onCreate", "onCreate: " + mediaPlayer.getDuration());
                    String finalPath = "/storage/emulated/0/Android/data/com.example.carlosanguiano.compressvideo/cache";
                    if (mOnTrimVideoListener != null) {
//                        TrimVideoUtil.trim(this, path1, finalPath, 0, mediaPlayer.getDuration(), mOnTrimVideoListener);

                    }

                } else {
                    Log.i("onCreate", "onCreate: ");

                }

                /*try {
                    mediaPlayer.setDataSource(file.getPath());
                } catch (IOException e) {
                    e.printStackTrace();
                }*/
            }
        });
    }

    public void setOnTrimVideoListener(TrimVideoListener onTrimVideoListener) {
        this.mOnTrimVideoListener = onTrimVideoListener;
    }

    private void initView() {
        fileSize = findViewById(R.id.size);
        filePath = findViewById(R.id.path);
        validate = findViewById(R.id.validate);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mOnTrimVideoListener == null) {

        }
    }


    public int getFileSize(File file) {
        DecimalFormat format = new DecimalFormat("#.##");

        long KB = 1024;
        long MB = KB * KB;
        long GB = MB * KB;
        long TB = GB * KB;

        if (!file.isFile()) {
            throw new IllegalArgumentException("Expected a file");
        }
        lengthFile = file.length();
//        int lengthFile = 399184; //test KB = 389 KB
//        int lengthFile = 152211406; // test MB = 145 MB;
//        int lengthFile = (int) 6.3609118e+16; // test GB = 1 GB;

        if (lengthFile > GB) {
            Log.i("sizeFile", "getFileSize: " + format.format(lengthFile / GB) + " GB");
            Toast.makeText(this, " " + format.format(lengthFile / GB) + " GB", Toast.LENGTH_SHORT).show();
//            return Double.parseDouble(format.format(lengthFile / GB));
            return (int) (lengthFile / GB);
        }
        if (lengthFile > MB) {
            Log.i("sizeFile", "getFileSize: " + format.format(lengthFile / MB) + " MB");
            Toast.makeText(this, " " + format.format(lengthFile / MB) + " MB", Toast.LENGTH_SHORT).show();
            return (int) (lengthFile / MB);
        }
        if (lengthFile > KB) {
            Log.i("sizeFile", "getFileSize: " + format.format(lengthFile / KB) + " KB");
            Toast.makeText(this, " " + format.format(lengthFile / KB) + " KB", Toast.LENGTH_SHORT).show();
            return (int) (lengthFile / KB);
        }
        Log.i("sizeFile", "getFileSize: " + format.format(lengthFile) + " B");
        Toast.makeText(this, " " + format.format(lengthFile) + " B", Toast.LENGTH_SHORT).show();
        return (int) lengthFile;
    }
}
