package technology.infobite.com.sportsforsports;

import android.media.Image;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

public class Profile_Camera extends AppCompatActivity {

    private List<GalleryModel> galleryModels1 = new ArrayList<>();
    RecyclerView imagerclv;
    ImageView messages,photos,videos;
    TabLayout tabMode;
    ViewPager viewPager;
    private int[] icon = {
            R.drawable.ic_menu1
            ,R.drawable.ic_menu1
            ,R.drawable.ic_menu1
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile__camera);

        tabMode = findViewById(R.id.tabMode);
        viewPager = findViewById(R.id.viewpager);

       /* tabMode.getTabAt(0).setIcon(icon[0]);
        tabMode.getTabAt(1).setIcon(icon[1]);
        tabMode.getTabAt(2).setIcon(icon[2]);*/
        tabMode.setTabGravity(TabLayout.GRAVITY_FILL);
        tabMode.setTabMode(TabLayout.MODE_FIXED);
        tabMode.setupWithViewPager(viewPager);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(), tabMode.getTabCount());
        viewPager.setAdapter(adapter);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabMode));

        tabMode.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }

        });

    }

}
