package com.mobiquity.amarshall.starwarscoolnav;

import android.app.Activity;

import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;

import com.mobiquity.amarshall.starwarscoolnav.fragments.MainFragment;
import com.mobiquity.amarshall.starwarscoolnav.objects.DetailUpdateListener;
import com.mobiquity.amarshall.starwarscoolnav.objects.Entry;
import com.mobiquity.amarshall.starwarscoolnav.objects.EntryDataSource;
import com.mobiquity.amarshall.starwarscoolnav.objects.GetEntryTask;
import com.mobiquity.amarshall.starwarscoolnav.objects.GetStarWarsNamesTask;

import java.util.ArrayList;


public class MainActivity extends Activity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks, DisplayNameListener, GetStarWarsNamesTask.GetNamesListener, GetEntryTask.DetailEntryListener {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    private String[] name_list;
    private ArrayList<String> name_array_list;
    private int progress_tracker;
    private ProgressDialog progressDialog;
    private int api_page_num;
    private boolean mDoneLoading = false;
    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));

        name_array_list = new ArrayList<>();

        progress_tracker = 0;
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Downloading Data from the Death Star...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setMax(87);

        api_page_num = 1;

        GetStarWarsNamesTask getStarWarsNamesTask = new GetStarWarsNamesTask(this);
        getStarWarsNamesTask.execute("" + api_page_num);

        MainFragment mainFragment = MainFragment.newInstance();

        getFragmentManager().beginTransaction()
                .replace(R.id.container, mainFragment, MainFragment.TAG)
                .commit();

    }

    @Override
    protected void onStart() {
        super.onStart();
        progressDialog.show();

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        if (mDoneLoading) {

            int newPosition = position + 1;

            if (newPosition >= 17) {
                newPosition++;  // Fixes API bug where 17 is null
            }

            GetEntryTask getEntryTask = new GetEntryTask(this);
            Log.i("tag", "Position + 1: " + newPosition);

            getEntryTask.execute("" + newPosition);

        }
    }

    public void update_detail(String _entry_api_id) {
        if (mDoneLoading) {

            FragmentManager fragmentManager = getFragmentManager();
            Fragment frag = fragmentManager.findFragmentByTag(MainFragment.TAG);

            if (frag instanceof DetailUpdateListener) {

                //TODO: Start ASYNK task to get deatils, in onPost, call something here to pass info down chain

                EntryDataSource entryDataSource = new EntryDataSource(this);

                entryDataSource.open();

                Entry entry = entryDataSource.readEntry("" + _entry_api_id);

                entryDataSource.close();

                ((DetailUpdateListener) frag).update_display(entry);
            } else {
                throw new IllegalArgumentException("Fragment must implement DetailUpdateListener");
            }
        }
    }

    /**
     * Sets the Title at the top.
     */
    public void onSectionAttached(int number) {
//        switch (number) {
//            case 1:
//                mTitle = "Title 1";
//                break;
//            case 2:
//                mTitle = getString(R.string.title_section2);
//                break;
//            case 3:
//                mTitle = getString(R.string.title_section3);
//                break;
//        }

        if (name_list == null) {
            mTitle = "Loading Names...";
        } else {
            mTitle = name_list[number - 1];
        }

        restoreActionBar();
    }

    public void restoreActionBar() {
        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }

    @Override
    public void set_names(String[] names) {

        Fragment frag = getFragmentManager()
                .findFragmentById(R.id.navigation_drawer);

        if (frag instanceof DisplayNameListener) {
            ((NavigationDrawerFragment) frag).set_names(names);
            name_list = names;
        }

    }

    @Override
    public void add_names_to_list(ArrayList<String> _names, String _next) {

//        name_array_list.addAll(_names);
        for (int i = 0; i < _names.size(); i++) {
            name_array_list.add(_names.get(i));
            progress_tracker++;
            progressDialog.setProgress(progress_tracker);
        }

        if (_next.equals("null")) {

            String[] names = new String[name_array_list.size()];
            names = name_array_list.toArray(names);

            set_names(names);
            progressDialog.dismiss();
            mDoneLoading = true;
        } else {
            GetStarWarsNamesTask getStarWarsNamesTask = new GetStarWarsNamesTask(this);
            getStarWarsNamesTask.execute("" + ++api_page_num);
        }

    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((MainActivity) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }
    }

}
