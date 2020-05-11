package com.example.collegeevent.Lokesh;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.collegeevent.R;

public class TabAccessorAdapter extends FragmentPagerAdapter {
    public TabAccessorAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    public TabAccessorAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position)
        {
            case 0: Fragment_Group fragment_group = new Fragment_Group();
                    return fragment_group;
            case 1: FragmentFeed fragmentFeed = new FragmentFeed();
                return fragmentFeed;
            case 2: Fragment_Events fragment_events = new Fragment_Events();
                return fragment_events;
            default:return null;
        }
    }

    @Override
    public int getCount() {
        // Since there will be three fragments: Groups, Feed and Events fragments.
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position)
        {
            case 0: return "Groups";
            case 1: return "Feed";
            case 2: return "Events";
            default:return null;
        }
    }
}
