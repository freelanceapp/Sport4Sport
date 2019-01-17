package technology.infobite.com.sportsforsports;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import technology.infobite.com.sportsforsports.ui.activity.GalleryActivity;
import technology.infobite.com.sportsforsports.ui.activity.MyPageActivity;

@SuppressLint("ValidFragment")
public class PersonalInfoFragment  extends Fragment {

    Button button,button1;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.personal_info_fragment,container,false);
        button1 = view.findViewById(R.id.commentpage);
        button = view.findViewById(R.id.netxmypage);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getContext(),MyPageActivity.class);
                startActivity(intent);

            }
        });

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(getContext(),GalleryActivity.class);
                startActivity(intent1);
            }
        });
        return view;

    }
}
