package com.example.collegeevent.Lokesh;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.widget.*;
import android.os.Bundle;

import com.example.collegeevent.R;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class Groups extends AppCompatActivity {
ViewPager viewPager;
PageAdapter pagerAdapter;
Toolbar toolbar;
TabLayout tabLayout;
int image[]={R.drawable.ic_group_black_24dp};
@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groups);
        toolbar= findViewById(R.id.tool);
        viewPager= findViewById(R.id.viewpager);
        tabLayout= findViewById(R.id.tabs);
        getSupportActionBar();
        setupViewPager();

        tabLayout.setupWithViewPager(viewPager);
 //   setupTabIcon();

    }

    private void setupViewPager() {
    pagerAdapter= new PageAdapter(getSupportFragmentManager());
    pagerAdapter.addFragment(new Fragment_Group(),"GROUPS");

    viewPager.setAdapter(pagerAdapter);
    }

//    private void setupTabIcon() {
//    tabLayout.getTabAt(0).setIcon(image[0]);
//    }



    private class PageAdapter extends FragmentPagerAdapter {
    ArrayList<Fragment> mFragmentList;
        ArrayList<String> mTitleList;
        public PageAdapter(FragmentManager supportFragmentManager) {
            super(supportFragmentManager);
            mFragmentList= new ArrayList<>();
            mTitleList= new ArrayList<>();
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }
        public  void addFragment(Fragment fragment,String title)
        {
            mFragmentList.add(fragment);
            mTitleList.add(title);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return mTitleList.get(position);
        }
    }
}
