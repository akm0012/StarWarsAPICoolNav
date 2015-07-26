package com.mobiquity.amarshall.starwarscoolnav.fragments;

import java.util.Locale;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.annotation.Nullable;
import android.support.v13.app.FragmentPagerAdapter;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mobiquity.amarshall.starwarscoolnav.R;
import com.mobiquity.amarshall.starwarscoolnav.objects.DetailUpdateListener;
import com.mobiquity.amarshall.starwarscoolnav.objects.Entry;


public class MainFragment extends Fragment implements ActionBar.TabListener, DetailUpdateListener{

    public static final String TAG = "MainFragment";

    SectionsPagerAdapter mSectionsPagerAdapter;
    ViewPager mViewPager;
    Activity mContainingActivity;

    // Factory
    public static MainFragment newInstance() {

        MainFragment frag = new MainFragment();

        return frag;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        mContainingActivity = activity;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_main_activity2, container, false);

        // Set up the action bar.
        final ActionBar actionBar = mContainingActivity.getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) view.findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        // When swiping between different sections, select the corresponding
        // tab. We can also use ActionBar.Tab#select() to do this if we have
        // a reference to the Tab.
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);
            }
        });

        // For each of the sections in the app, add a tab to the action bar.
        for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
            // Create a tab with text corresponding to the page title defined by
            // the adapter. Also specify this Activity object, which implements
            // the TabListener interface, as the callback (listener) for when
            // this tab is selected.
            actionBar.addTab(
                    actionBar.newTab()
                            .setText(mSectionsPagerAdapter.getPageTitle(i))
                            .setTabListener(this));
        }

        return view;
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        // When the given tab is selected, switch to the corresponding page in
        // the ViewPager.
        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    @Override
    public void update_display(Entry _entry) {

        //TODO: Bug - this will fail if you update while on page 2
        DetailsFragment detailsFrag = (DetailsFragment) getFragmentManager().findFragmentByTag("android:switcher:" + mViewPager.getId() + ":" + mViewPager.getCurrentItem());
        Images_Fragment imageFrag = (Images_Fragment) getFragmentManager().findFragmentByTag("android:switcher:" + mViewPager.getId() + ":" + "1");

        detailsFrag.set_name(_entry.getmName());
        detailsFrag.set_height(_entry.getmHeight());
        detailsFrag.set_mass(_entry.getmMass());
        detailsFrag.set_hair_color(_entry.getmHairColor());
        detailsFrag.set_skin_color(_entry.getmSkinColor());
        detailsFrag.set_eye_color(_entry.getmEyeColor());
        detailsFrag.set_birth_year(_entry.getmBirthYear());
        detailsFrag.set_gender(_entry.getmGender());

        imageFrag.go_to_url(_entry.getmName());

        Log.d("tag", "Details Frag: " + detailsFrag);
        Log.d("tag", "Image Frag: " + imageFrag);

    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.

            if (position == 0) {
                DetailsFragment detailsFragment = DetailsFragment.newInstance(); //TODO: fill in with info
                return  detailsFragment;
            } else {
                Images_Fragment images_fragment = Images_Fragment.newInstance(); //TODO: fill in with info
                return  images_fragment;
            }

        }

        @Override
        public int getCount() {
            // Show 2 total pages.
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();
            switch (position) {
                case 0:
                    return getString(R.string.title_section1).toUpperCase(l);
                case 1:
                    return getString(R.string.title_section2).toUpperCase(l);
            }
            return null;
        }
    }

//    /**
//     * A placeholder fragment containing a simple view.
//     */
//    public static class PlaceholderFragment extends Fragment {
//        /**
//         * The fragment argument representing the section number for this
//         * fragment.
//         */
//        private static final String ARG_SECTION_NUMBER = "section_number";
//
//        /**
//         * Returns a new instance of this fragment for the given section
//         * number.
//         */
//        public static PlaceholderFragment newInstance(int sectionNumber) {
//            PlaceholderFragment fragment = new PlaceholderFragment();
//            Bundle args = new Bundle();
//            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
//            fragment.setArguments(args);
//            return fragment;
//        }
//
//        public PlaceholderFragment() {
//        }
//
//        @Override
//        public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                                 Bundle savedInstanceState) {
//            View rootView = inflater.inflate(R.layout.fragment_main_activity2, container, false);
//            return rootView;
//        }
//    }

}
