package technology.infobite.com.sportsforsports.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import technology.infobite.com.sportsforsports.R;
import technology.infobite.com.sportsforsports.retrofit_provider.RetrofitService;
import technology.infobite.com.sportsforsports.utils.BaseFragment;
import technology.infobite.com.sportsforsports.utils.ConnectionDetector;

public class DraggabbleTopFragment extends BaseFragment {

    private View rootView;
    private TextView tv_about;
    private String about = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        rootView = inflater.inflate(R.layout.fragment_draggable_top, container, false);
        mContext = getActivity();
        retrofitApiClient = RetrofitService.getRetrofit();
        cd = new ConnectionDetector(mContext);
        return rootView;
    }
}
