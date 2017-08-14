package s3585826.assignment1;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    protected static User user1;
    private static final String LOG_TAG = "Main Activity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setUpUsers();

        setContentView(R.layout.activity_main);
        ViewPager mViewPager = (ViewPager) findViewById(R.id.container);
        setupViewPager(mViewPager);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setSubtitle(user1.getName());
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

    public void setUpUsers(){

        ArrayList<Meeting> meetingList = new ArrayList<>();
        HashMap<String, Friend> friendMap = new HashMap<>();

        user1 = new User("j0u301", "Nebojsa Pajkic", "user1@user1.com",
            friendMap, meetingList);

        Friend user2 = new Friend("13jk14", "Alan Lam", "user2@user2.com", "31/12/1991");
        friendMap.put("13jk14", user2);
        Friend user3 = new Friend("fa879f","Fabio Monsalve", "user3@user3.com", "31/12/1991");
        friendMap.put("fa879f", user3);
        Friend user4 = new Friend("fa7ffa", "Callum Pearse", "user4@user4.com", "31/12/1991");
        friendMap.put("fa7ffa", user4);

        Log.d(LOG_TAG, "setUpUsers");
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