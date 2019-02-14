package technology.infobite.com.sportsforsports.ui.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import life.knowledge4.videotrimmer.K4LVideoTrimmer;
import life.knowledge4.videotrimmer.interfaces.OnTrimVideoListener;
import technology.infobite.com.sportsforsports.R;

public class VideoTrimerActivity extends Activity implements OnTrimVideoListener {

    private String str_video;
    private K4LVideoTrimmer videoTrimmer;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_video_trimer);
        init();
    }

    private void init() {
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setCancelable(false);
        mProgressDialog.setMessage("Start progress");

        videoTrimmer = ((K4LVideoTrimmer) findViewById(R.id.videoTrimmer));
        str_video = getIntent().getStringExtra("video");
       /* vv_video.setVideoPath(str_video);
        vv_video.start();*/

        if (videoTrimmer != null) {
            videoTrimmer.setMaxDuration(120);
            videoTrimmer.setOnTrimVideoListener(this);
            videoTrimmer.getDrawingTime();
            //videoTrimmer.setOnK4LVideoListener(this);
            //videoTrimmer.setDestinationPath("/storage/emulated/0/DCIM/CameraCustom/");
            videoTrimmer.setVideoURI(Uri.parse(str_video));
            //videoTrimmer.setVideoInformationVisibility(true);
        }
    }

   /* @Override
    public void onTrimStarted() {
        mProgressDialog.show();
    }*/

    @Override
    public void getResult(final Uri uri) {
        mProgressDialog.cancel();

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                // Toast.makeText(VideoTrimerActivity.this, getString(R.string.video_saved_at, uri.getPath()), Toast.LENGTH_SHORT).show();
            }
        });
        Log.e("Video Path ", "..." + uri.getPath());
        /*Intent intent_gallery = new Intent(VideoTrimerActivity.this,UploadVideoActivity.class);
        intent_gallery.putExtra("video1",uri.getPath());
        startActivity(intent_gallery);*/

       /* Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        intent.setDataAndType(uri, "video/mp4");
        startActivity(intent);*/
        finish();
    }

    @Override
    public void cancelAction() {
        mProgressDialog.cancel();
        videoTrimmer.destroy();
        finish();
    }

    /*@Override
    public void onError(final String message) {
        mProgressDialog.cancel();

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(VideoTrimerActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onVideoPrepared() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(VideoTrimerActivity.this, "onVideoPrepared", Toast.LENGTH_SHORT).show();
            }
        });
    }*/

}
