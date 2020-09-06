package com.example.snavadogru.SoruTakip.main;

import android.content.Context;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.snavadogru.R;

import java.util.ArrayList;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    private ArrayList<Fragment> listFragment = new ArrayList<>();
    private ArrayList<String> listFragmentName = new ArrayList<>();

    public SectionsPagerAdapter(FragmentManager fm) {
        super(fm,fm.getBackStackEntryCount());
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        return listFragment.get(position);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return listFragmentName.get(position);
    }

    @Override
    public int getCount() {return listFragment.size();}
    public void addFragment(Fragment f,String name){
        listFragment.add(f);
        listFragmentName.add(name);
    }
}