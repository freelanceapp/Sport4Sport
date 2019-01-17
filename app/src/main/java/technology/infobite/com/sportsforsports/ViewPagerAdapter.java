package technology.infobite.com.sportsforsports;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    private Context context;
    int totalTabs;

    public ViewPagerAdapter(FragmentManager fm, int totalTabs) {
        super(fm);
        context = context;
        this.totalTabs = totalTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                CommentFragment commentFragment = new CommentFragment();
                return commentFragment;
            case 1:
                ImageFragment imageFragment = new ImageFragment();
                return imageFragment;
            case 2:
                VideoFragment videoFragment = new VideoFragment();
                return videoFragment;
            default:
                return null;
        }
    }

    public  int getCount(){
        return totalTabs;
    }
}