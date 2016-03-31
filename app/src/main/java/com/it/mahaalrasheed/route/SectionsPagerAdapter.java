package com.it.mahaalrasheed.route;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

////new things
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    public SectionsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        //s= position;
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        return PlaceholderFragment.newInstance(position + 1);
    }

    public void walktext (String text) {

        PlaceholderFragment.calcTime(text);
    }

    public void passroute (double fromCoor1, double fromCoor2,double toCoor1,double toCoor2) {
        PlaceholderFragment.routeoption(fromCoor1, fromCoor2, toCoor1, toCoor2, 1);
    }



    @Override
    public int getCount() {
        // Show 3 total pages.

        return 3;
    }

}
