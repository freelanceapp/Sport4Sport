package technology.infobite.com.sportsforsports;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class CrateProfileAdapter extends FragmentPagerAdapter {

    private Context context;
    int totalTabs;

    public CrateProfileAdapter(FragmentManager fm, int totalTabs) {
        super(fm);
        context = context;
        this.totalTabs = totalTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                PersonalInfoFragment personalInfo = new PersonalInfoFragment();
                return personalInfo;
            case 1:
                CareerFragment career = new CareerFragment();
                return career;
            case 2:

                ProfileFragment profile = new ProfileFragment();
                return profile;
            default:
                return null;
        }
    }

    public  int getCount(){
        return totalTabs;
    }
}