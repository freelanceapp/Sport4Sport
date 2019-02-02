package technology.infobite.com.sportsforsports.ui.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import life.knowledge4.videotrimmer.K4LVideoTrimmer;
import life.knowledge4.videotrimmer.interfaces.OnTrimVideoListener;
import technology.infobite.com.sportsforsports.R;
import technology.infobite.com.sportsforsports.constant.Constant;
import technology.infobite.com.sportsforsports.utils.BaseFragment;

import static technology.infobite.com.sportsforsports.ui.activity.VideoActivity.videoFragmentManager;

public class VideoTrimmerFragment extends BaseFragment implements OnTrimVideoListener {

    private View rootView;
    private String str_video, strVideoThumb;
    private K4LVideoTrimmer videoTrimmer;
    private ProgressDialog mProgressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        rootView = inflater.inflate(R.layout.fragment_video_trimer, container, false);
        activity = getActivity();
        mContext = getActivity();
        init();
        return rootView;
    }

    private void init() {
        mProgressDialog = new ProgressDialog(mContext);
        mProgressDialog.setCancelable(false);
        mProgressDialog.setMessage("Start progress");

        videoTrimmer = rootView.findViewById(R.id.videoTrimmer);
        str_video = getArguments().getString("video_path");
        strVideoThumb = getArguments().getString("video_thumb");
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

    @Override
    public void getResult(Uri uri) {
        mProgressDialog.cancel();
        Log.e("Video Path ", "..." + uri.getPath());

        /*Intent intent_gallery = new Intent(VideoTrimerActivity.this,UploadVideoActivity.class);
        intent_gallery.putExtra("video1",uri.getPath());
        startActivity(intent_gallery);*/

       /* Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        intent.setDataAndType(uri, "video/mp4");
        startActivity(intent);*/

        Intent returnIntent = new Intent();
        returnIntent.putExtra("crop_video", uri.getPath());
        returnIntent.putExtra("video_thumb", strVideoThumb);
        activity.setResult(Activity.RESULT_OK, returnIntent);
        activity.finish();
    }

    @Override
    public void cancelAction() {
        mProgressDialog.cancel();
        videoTrimmer.destroy();

        videoFragmentManager
                .beginTransaction()
                .replace(R.id.fram_container, new VideoGalleryFragment(),
                        Constant.VideoGalleryFragment).commit();
    }
}
