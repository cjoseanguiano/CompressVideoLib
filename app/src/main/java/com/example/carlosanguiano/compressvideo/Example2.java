package com.example.carlosanguiano.compressvideo;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.carlosanguiano.compressvideo.features.trim.VideoTrimmerActivity;
import com.example.carlosanguiano.compressvideo.video.MediaController;

import java.io.File;
import java.text.DecimalFormat;


public class Example2 extends AppCompatActivity {

    private TextView fileSize;
    private TextView filePath;
    private static String path1 = "/storage/81B6-1202/DCIM/Camera/20180623_215118.mp4";
//    private static String path1 = "/storage/emulated/0/DCIM/Camera/VID_20180627_173203.3gp";
//    private static String path1 = "/storage/emulated/0/Android/data/com.example.carlosanguiano.compressvideo/cache/QbitsVideoCompressor/VIDEO_20180627_173932.mp4";
    private Button validate;
    private Button test2;
    private int limitSize = 16777216; // 16 MB;
    private long lengthFile = 0;
    private File file;
    private String mFinalPath;
    private ProgressDialog mProgressDialog;

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
            Toast.makeText(Example2.this, "TEST 1", Toast.LENGTH_SHORT).show();

            Log.i("onCreate", "onCreate: " + lengthFile);
            Log.i("onCreate", "onCreate: " + limitSize);
            if (lengthFile < limitSize) {
                VideoTrimmerActivity.call(Example2.this, path1);
            } else {
                new VideoCompressors().execute();
            }
        });

    }

    private void initView() {
        fileSize = findViewById(R.id.size);
        filePath = findViewById(R.id.path);
        validate = findViewById(R.id.validate);
        test2 = findViewById(R.id.test2);
    }

    public int getFileSize(File file) {
        DecimalFormat format = new DecimalFormat("#.##");

        long KB = 1024;//1000
        long MB = KB * KB;//10000
        long GB = MB * KB;//100000
        long TB = GB * KB;

        if (file.exists()){
            if (!file.isFile()) {
                throw new IllegalArgumentException("Expected a file");
            }
        }
        lengthFile = file.length();//110000

        if (lengthFile > GB) {
            Log.i("sizeFile", "getFileSize: " + format.format(lengthFile / GB) + " GB");
            Toast.makeText(this, " " + format.format(lengthFile / GB) + " GB", Toast.LENGTH_SHORT).show();
//            return Double.parseDouble(format.format(lengthFile / GB));
            return (int) (lengthFile / GB);//1
        }
        if (lengthFile > MB) {
            Log.i("sizeFile", "getFileSize: " + format.format(lengthFile / MB) + " MB");
            Toast.makeText(this, " " + format.format(lengthFile / MB) + " MB", Toast.LENGTH_SHORT).show();
            return (int) (lengthFile / MB);//1
        }
        if (lengthFile > KB) {
            Log.i("sizeFile", "getFileSize: " + format.format(lengthFile / KB) + " KB");
            Toast.makeText(this, " " + format.format(lengthFile / KB) + " KB", Toast.LENGTH_SHORT).show();
            return (int) (lengthFile / KB);//1
        }
        Log.i("sizeFile", "getFileSize: " + format.format(lengthFile) + " B");
        Toast.makeText(this, " " + format.format(lengthFile) + " B", Toast.LENGTH_SHORT).show();
        return (int) lengthFile;
    }

    private ProgressDialog buildDialog(String msg) {
        if (mProgressDialog == null) {
            mProgressDialog = ProgressDialog.show(this, "", msg);
        }
        mProgressDialog.setMessage(msg);
        return mProgressDialog;
    }

    class VideoCompressors extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.d("Start", "Start video compression");
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            return MediaController.getInstance().convertVideo(path1);
        }

        @Override
        protected void onPostExecute(Boolean compressed) {
            super.onPostExecute(compressed);
            if (compressed) {
                Log.d("Start", "Compression successfully!");
            }
        }
    }
}
