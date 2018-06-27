package com.example.carlosanguiano.compressvideo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListPopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.carlosanguiano.compressvideo.features.trim.VideoTrimmerActivity;
import com.example.carlosanguiano.compressvideo.interfaces.CompressVideoListener;
import com.example.carlosanguiano.compressvideo.interfaces.TrimVideoListener;
import com.example.carlosanguiano.compressvideo.utils.CompressVideoUtil;
import com.example.carlosanguiano.compressvideo.utils.TrimVideoUtil;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;


public class Example2 extends AppCompatActivity implements TrimVideoListener {

    private TextView fileSize;
    private TextView filePath;
    private static String path1 = "/storage/81B6-1202/DCIM/Camera/20180623_215118.mp4";
    private static String path2 = "/storage/81B6-1202/DCIM/Camera/20180626_132316.mp4";
    //    private static String path2 = "/storage/81B6-1202/DCIM/Camera/Test01.mp4";
    private Button validate;
    private Button test2;
    private int limitSize = 16777216; // 16 MB;
    private long lengthFile = 0;
    private File file;
    public TrimVideoListener mOnTrimVideoListener;
    private String mFinalPath;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example2);
        initView();
        setOnTrimVideoListener(this);
        filePath.setText(path1);
        file = new File(path1);
        int sizeFile = getFileSize(file);
        fileSize.setText("" + sizeFile);

        validate.setOnClickListener(view -> {
            Toast.makeText(Example2.this, "TEST 1", Toast.LENGTH_SHORT).show();

            Log.i("onCreate", "onCreate: " + lengthFile);
            Log.i("onCreate", "onCreate: " + limitSize);
            if (lengthFile > limitSize) {
                VideoTrimmerActivity.call(Example2.this, path1);
            } else {
                Log.i("onCreate", "onCreate: " + lengthFile);
            }
        });

        test2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Example2.this, "TEST 2", Toast.LENGTH_SHORT).show();
                MediaPlayer mediaPlayer = MediaPlayer.create(Example2.this, Uri.parse("file://" + file.getAbsolutePath()));
                if (mediaPlayer != null) {
                    mediaPlayer.getDuration();
                    if (mOnTrimVideoListener != null) {
                        TrimVideoUtil.trim(Example2.this, path1, getTrimmedVideoPath(), 1, mediaPlayer.getDuration() - 1, mOnTrimVideoListener);
                    }
                } else {
                    Log.i("onCreate", "onCreate: ");
                }
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
        test2 = findViewById(R.id.test2);
    }

    private String getTrimmedVideoPath() {
        if (mFinalPath == null) {
            File file = Example2.this.getExternalCacheDir();
            if (file != null) {
                mFinalPath = file.getAbsolutePath();
            }
        }
        return mFinalPath;
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

    @Override
    public void onStartTrim() {
        Log.i("onCreate", "onCreate: " + lengthFile);
    }

    @Override
    public void onFinishTrim(String in) {
        String out = "/storage/emulated/0/Android/data/com.iknow.android/cache/compress.mp4";
        buildDialog(getResources().getString(R.string.compressing)).show();
        CompressVideoUtil.compress(this, in, out, new CompressVideoListener() {
            @Override
            public void onSuccess(String message) {
                Log.i("sizeFile", "getFileSize: ");

            }

            @Override
            public void onFailure(String message) {
                Log.i("sizeFile", "getFileSize: ");

            }

            @Override
            public void onFinish() {
                Log.i("sizeFile", "getFileSize: ");
                if (mProgressDialog.isShowing()) {
                    mProgressDialog.dismiss();
                    finish();
                }
            }
        });
    }

    private ProgressDialog buildDialog(String msg) {
        if (mProgressDialog == null) {
            mProgressDialog = ProgressDialog.show(this, "", msg);
        }
        mProgressDialog.setMessage(msg);
        return mProgressDialog;
    }

    @Override
    public void onCancel() {
        Log.i("onCreate", "onCreate: " + lengthFile);
    }
}
