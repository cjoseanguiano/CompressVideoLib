package com.example.carlosanguiano.compressvideo;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.PowerManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.netcompss.ffmpeg4android.GeneralUtils;
import com.netcompss.ffmpeg4android.Prefs;
import com.netcompss.loader.LoadJNI;


public class Example1 extends AppCompatActivity {


    String workFolder = null;
    String demoVideoFolder = null;
    String demoVideoPath = null;
    String vkLogPath = null;
    private boolean commandValidationFailedFlag = false;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example_one);

        demoVideoFolder = Environment.getExternalStorageDirectory().getAbsolutePath() + "/videokit/";
//	  	  demoVideoPath = demoVideoFolder + "in.mp4";
        demoVideoPath = "/storage/81B6-1202/DCIM/Camera/20180623_215118.mp4";

        Log.i(Prefs.TAG, getString(R.string.app_name) + " version: " + GeneralUtils.getVersionName(getApplicationContext()));
        workFolder = getApplicationContext().getFilesDir().getAbsolutePath() + "/";
        //Log.i(Prefs.TAG, "workFolder: " + workFolder);
        vkLogPath = workFolder + "vk.log";

        GeneralUtils.copyLicenseFromAssetsToSDIfNeeded(this, workFolder);
        GeneralUtils.copyDemoVideoFromAssetsToSDIfNeeded(this, demoVideoFolder);

        Button invoke = (Button) findViewById(R.id.invokeButton);
        invoke.setOnClickListener(new View.OnClickListener() {
            //	      	/storage/81B6-1202/DCIM/Camera/20180622_154723.mp4
            public void onClick(View v) {
                Log.i(Prefs.TAG, "run clicked.");
                if (GeneralUtils.checkIfFileExistAndNotEmpty(demoVideoPath)) {
                    new TranscdingBackground(Example1.this).execute();
                } else {
                    Toast.makeText(getApplicationContext(), demoVideoPath + " not found", Toast.LENGTH_LONG).show();
                }
            }
        });

        int rc = GeneralUtils.isLicenseValid(getApplicationContext(), workFolder);
        Log.i(Prefs.TAG, "License check RC: " + rc);
    }

    public class TranscdingBackground extends AsyncTask<String, Integer, Integer> {

        ProgressDialog progressDialog;
        Activity _act;

        public TranscdingBackground(Activity act) {
            _act = act;
        }


        @Override
        protected void onPreExecute() {
            progressDialog = new ProgressDialog(_act);
            progressDialog.setMessage("Video Transcoding in progress...");
            progressDialog.show();

        }

        protected Integer doInBackground(String... paths) {
            Log.i(Prefs.TAG, "doInBackground started...");

            // delete previous log
            boolean isDeleted = GeneralUtils.deleteFileUtil(workFolder + "/vk.log");
            Log.i(Prefs.TAG, "vk deleted: " + isDeleted);

            PowerManager powerManager = (PowerManager) _act.getSystemService(Activity.POWER_SERVICE);
            PowerManager.WakeLock wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "VK_LOCK");
            Log.d(Prefs.TAG, "Acquire wake lock");
            wakeLock.acquire();

            //String commandStr = commandText.getText().toString();

            ///////////// Set Command using code (overriding the UI EditText) /////

//	->		String commandStr1 = "ffmpeg -y -i /storage/81B6-1202/DCIM/Camera/20180623_215118.mp4 -strict experimental -s 320x240 -r 30 -aspect 3:4 -ab 48000 -ac 2 -ar 22050 -vcodec mpeg4 -b 2097152 /sdcard/videokit/out1.mp4"; /*1:30 -- 17.2MB*/
//			String commandStr1 = "ffmpeg -y -i /storage/81B6-1202/DCIM/Camera/20180623_215118.mp4 -strict experimental -s 360x640 -r 30 -aspect 1:10 -ab 48000 -ac 2 -ar 22050 -vcodec mpeg4 -b 2097152 /sdcard/videokit/out1.mp4";  /*2:30 -- 18.12MB*/
            String commandStr1 = "ffmpeg -y -i /storage/81B6-1202/DCIM/Camera/20180623_215118.mp4 -strict experimental -s 160x120 -r 25 -vcodec mpeg4 -b 150k -ab 48000 -ac 2 -ar 22050 /sdcard/videokit/out.mp4"; /*1:10 Sec -- 145.16 a 1.81MB  Resolucion 1920 x 1080 a 160 x 120*/


            //String[] complexCommand1 = {"ffmpeg","-y" ,"-i", "/sdcard/videokit/in.mp4","-strict","experimental", "-vf", "movie=/sdcard/videokit/water.png [watermark]; [in][watermark] overlay=main_w-overlay_w-10:10 [out]","-s", "320x240","-r", "30", "-b", "15496k", "-vcodec", "mpeg4","-ab", "48000", "-ac", "2", "-ar", "22050", "/sdcard/videokit/out1.mp4"};
            ///////////////////////////////////////////////////////////////////////


            LoadJNI vk = new LoadJNI();
            try {
                //Toast.makeText(getApplicationContext(), "starting command1", Toast.LENGTH_LONG).show();
                Log.i(Prefs.TAG, "=======running first command=========");
                vk.run(GeneralUtils.utilConvertToComplex(commandStr1), workFolder, getApplicationContext());
                //vk.run(complexCommand1, workFolder, getApplicationContext());
                GeneralUtils.copyFileToFolder(vkLogPath, demoVideoFolder);
                Log.i(Prefs.TAG, "=======running thrird command=========");
            } catch (Throwable e) {
                Log.e(Prefs.TAG, "vk run exeption.", e);
            } finally {
                if (wakeLock.isHeld())
                    wakeLock.release();
                else {
                    Log.i(Prefs.TAG, "Wake lock is already released, doing nothing");
                }
            }
            Log.i(Prefs.TAG, "doInBackground finished");
            return Integer.valueOf(0);
        }

        protected void onProgressUpdate(Integer... progress) {
        }

        @Override
        protected void onCancelled() {
            Log.i(Prefs.TAG, "onCancelled");
            //progressDialog.dismiss();
            super.onCancelled();
        }


        @Override
        protected void onPostExecute(Integer result) {
            Log.i(Prefs.TAG, "onPostExecute");
            progressDialog.dismiss();
            super.onPostExecute(result);

            // finished Toast
            String rc = null;
            if (commandValidationFailedFlag) {
                rc = "Command Vaidation Failed";
            } else {
                rc = GeneralUtils.getReturnCodeFromLog(vkLogPath);
            }
            final String status = rc;
            Example1.this.runOnUiThread(new Runnable() {
                public void run() {
                    Toast.makeText(Example1.this, status, Toast.LENGTH_LONG).show();
                    if (status.equals("Transcoding Status: Failed")) {
                        Toast.makeText(Example1.this, "Check: " + vkLogPath + " for more information.", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }

    }


}

