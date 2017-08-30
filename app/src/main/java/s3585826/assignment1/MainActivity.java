package s3585826.assignment1;

import android.os.AsyncTask;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

import java.util.ArrayList;
import java.util.List;

import s3585826.assignment1.Model.Model;

/**
 * Created by Fabio Monsalve s3585826.
 */

public class MainActivity extends AppCompatActivity {
    private static final String LOG_TAG = "Main Activity";
    private static LocationListener locationListener;
    protected static Toolbar toolbar;
    private boolean firstTime = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Model.getInstance().firstTimeMain) {
            Model.getInstance().loadDummyData(this);
            Model.getInstance().firstTimeMain=false;
        }



        setContentView(R.layout.activity_main);
        ViewPager mViewPager = (ViewPager) findViewById(R.id.container);
        setupViewPager(mViewPager);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        //creat and run a location listener thread
        locationListener = new LocationListener(this);
        AsyncTask.execute(locationListener);

    }

    private void setupViewPager(ViewPager viewPager) {

        SectionsPageAdapter adapter = new SectionsPageAdapter
            (getSupportFragmentManager());

        adapter.addFragment(new Friends(), "Friends");
        adapter.addFragment(new Meetings(), "Meetings");
        adapter.addFragment(new UserInfo(), "My Details");

        viewPager.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main2, menu);
        return true;
    }

    private class SectionsPageAdapter extends FragmentPagerAdapter {

        private final List<Fragment> fragmentList = new ArrayList<>();
        private final List<String> fragmentTitleList = new ArrayList<>();

        void addFragment(Fragment fragment, String title) {
            fragmentList.add(fragment);
            fragmentTitleList.add(title);
        }

        SectionsPageAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return fragmentTitleList.get(position);
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }
    }
}