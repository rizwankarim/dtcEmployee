package com.example.dtcemployee.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class HomeFragmentAdapter extends FragmentPagerAdapter implements ObjectAtPostitionInterface {

    private List<Fragment> fragmentList = new ArrayList<>();
    private List<String> fragmentTitles = new ArrayList<>();

    public HomeFragmentAdapter(FragmentManager fragmentManager ) {
        super(fragmentManager);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return fragmentTitles.get(position);
    }

    public void addFragment(Fragment fragment, String name) {
        fragmentList.add(fragment);
        fragmentTitles.add(name);
    }

    @Override
    public Object getObjectAtPosition(int position) {
        if (position <= fragmentList.size() - 1){
            return fragmentList.get(position);
        }
        return null;
    }
}
