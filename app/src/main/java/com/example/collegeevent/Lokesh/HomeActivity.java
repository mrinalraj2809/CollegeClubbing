package com.example.collegeevent.Lokesh;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.collegeevent.R;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {
    ViewPager viewPager;
//    PageAdapter pagerAdapter;
    Toolbar toolbar;
    TabLayout tabLayout;
    TabAccessorAdapter mTabAccessorAdapter;
    FirebaseUser currentUser;
    // firebase auth 's get current user gives the status of user's logged in.
    FirebaseAuth mAuth;
    int image[]={R.drawable.ic_group_black_24dp};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        toolbar= findViewById(R.id.main_page_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("College Club");

        viewPager= findViewById(R.id.viewpager);
        tabLayout= findViewById(R.id.tabs);
        // TabAccessorAdapter is used to display the names in tab layout and call 3 fragments such as groups, feed, and Events
        mTabAccessorAdapter = new TabAccessorAdapter(getSupportFragmentManager());
        viewPager.setAdapter(mTabAccessorAdapter);
        //setupViewPager();
        tabLayout.setupWithViewPager(viewPager);
 //   setupTabIcon();

    }

//    private void setupViewPager() {
//    pagerAdapter= new PageAdapter(getSupportFragmentManager());
//    pagerAdapter.addFragment(new Fragment_Group(),"GROUPS");
//    // add fragments here to page adapter
//    viewPager.setAdapter(pagerAdapter);
//    }
//
////    private void setupTabIcon() {
////    tabLayout.getTabAt(0).setIcon(image[0]);
////    }
//
//
//
//    private class PageAdapter extends FragmentPagerAdapter {
//    ArrayList<Fragment> mFragmentList;
//        ArrayList<String> mTitleList;
//        public PageAdapter(FragmentManager supportFragmentManager) {
//            super(supportFragmentManager);
//            mFragmentList= new ArrayList<>();
//            mTitleList= new ArrayList<>();
//        }
//
//        @Override
//        public Fragment getItem(int position) {
//            return mFragmentList.get(position);
//        }
//        public  void addFragment(Fragment fragment,String title)
//        {
//            mFragmentList.add(fragment);
//            mTitleList.add(title);
//        }
//
//        @Override
//        public int getCount() {
//            return mFragmentList.size();
//        }
//
//        @Nullable
//        @Override
//        public CharSequence getPageTitle(int position) {
//            return mTitleList.get(position);
//        }
//    }

        //not required
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        super.onCreateOptionsMenu(menu);
//        getMenuInflater().inflate(R.menu.menu,menu);
//        return true;
//    }
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        super.onOptionsItemSelected(item);
//        switch (item.getItemId())
//        {
//            case R.id.menu_create_group:break;
//            case R.id.menu_logout: mAuth.signOut(); break;
//            case R.id.menu_profile_info:break;
//            default:
//        }
//        return true;
//    }

    @Override
    protected void onStart() {
        super.onStart();
        if(currentUser == null)
        {
            startActivity(new Intent(HomeActivity.this,LoginActivity.class));
        }
    }
}
