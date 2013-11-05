package com.corylucasjeffery.couponassistant;

import java.util.Locale;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.FragmentPagerAdapter;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class MainActivity extends ActionBarActivity implements ActionBar.TabListener {

    SectionsPagerAdapter mSectionsPagerAdapter;

    ViewPager mViewPager;
    private static String TAG = "MAIN";

    //private Button button_scan;
    private Button buttonScan;
    private TextView txtFormat, txtContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Set up the action bar.
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(), buttonScan, txtFormat, txtContent, this);

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.pager);
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
            actionBar.addTab(
                    actionBar.newTab()
                            .setText(mSectionsPagerAdapter.getPageTitle(i))
                            .setTabListener(this));
        }
    }
    /*
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {

        //retrieve result of scanning - instantiate ZXing object
        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        //check we have a valid result
        if (scanningResult != null) {
            //get content from Intent Result
            String scanContent = scanningResult.getContents();
            //get format name of data scanned
            String scanFormat = scanningResult.getFormatName();
            //output to UI
            Log.v(TAG, "Setting format and content");
            txtFormat.setText("FORMAT: "+scanFormat);
            txtContent.setText("CONTENT: "+scanContent);
            Log.v(TAG, "onActivityResult finished");
        }
        else{
            //invalid scan data or scan canceled
            Toast toast = Toast.makeText(getApplicationContext(),
                    "No scan data received!", Toast.LENGTH_SHORT);
            toast.show();
        }
    }
    */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.action_search:
                openSearch();
                return true;
            case R.id.action_settings:
                openSettings();
                return true;
            case R.id.action_refresh:
                openRefresh();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void openSearch() {
        Toast.makeText(this, "Search", Toast.LENGTH_LONG).show();
    }

    private void openSettings() {
        Toast.makeText(this, "Settings", Toast.LENGTH_LONG).show();
    }

    private void openRefresh() {
        Toast.makeText(this, "Refresh", Toast.LENGTH_LONG).show();
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


    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {
        private Button b;
        private TextView f, c;
        private Activity a;
        public SectionsPagerAdapter(FragmentManager fm, Button b, TextView f, TextView c, Activity a) {
            super(fm);
            this.b = b;
            this.f = f;
            this.c = c;
            this.a = a;
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).

            Fragment fragment = null;
            Bundle args = new Bundle();
            switch(position)
            {
                case 0:
                    fragment = new ItemFragment(f,c,b,a);
                    args.putInt(ItemFragment.ARG_SECTION_NUMBER, position + 1);
                    break;
                case 1:
                    fragment = new CouponFragment(f,c,b,a);
                    args.putInt(CouponFragment.ARG_SECTION_NUMBER, position + 1);
                    break;
                default:
                    return null;
            }

            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
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


    /**
     * A Fragment class that is used for scanning in Items
     */
    public static class ItemFragment extends Fragment {

        private static final String ARG_SECTION_NUMBER = "section_number_1";
        private Button buttonScan;
        private TextView txtFormat, txtContent;
        private Activity thisActivity;

        public ItemFragment() {

        }

        public ItemFragment(TextView f, TextView c, Button b, Activity a) {
            txtFormat = f;
            txtContent = c;
            buttonScan = b;
            thisActivity = a;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View v = inflater.inflate(R.layout.fragment_item, container, false);
            Button b = (Button) v.findViewById(R.id.scan_button);
            txtFormat = (TextView) v.findViewById(R.id.scan_format);
            txtContent = (TextView) v.findViewById(R.id.scan_content);
            if(b != null)
            {
                b.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //check for scan button
                        if(v.getId()==R.id.scan_button){
                            Log.v(TAG, "Item scan button");
                            //instantiate ZXing integration class
                            IntentIntegrator scanIntegrator = new IntentIntegrator(thisActivity);
                            //start scanning
                            Log.v(TAG, "before scan");
                            scanIntegrator.initiateScan();
                            Log.v(TAG, "finished scan");
                        }
                    }
                    public void onActivityResult(int requestCode, int resultCode, Intent intent) {

                        //retrieve result of scanning - instantiate ZXing object
                        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
                        //check we have a valid result
                        if (scanningResult != null) {
                            //get content from Intent Result
                            String scanContent = scanningResult.getContents();
                            //get format name of data scanned
                            String scanFormat = scanningResult.getFormatName();
                            //output to UI
                            Log.v(TAG, "Setting format and content");
                            txtFormat.setText("FORMAT: "+scanFormat);
                            txtContent.setText("CONTENT: "+scanContent);
                            Log.v(TAG, "onActivityResult finished");
                        }
                        else{
                            //invalid scan data or scan canceled
                            Toast toast = Toast.makeText(thisActivity.getApplicationContext(),
                                    "No scan data received!", Toast.LENGTH_SHORT);
                            toast.show();
                        }
                    }
                });
            }
            return v;
        }

        public void onActivityResult(int requestCode, int resultCode, Intent intent) {

            //retrieve result of scanning - instantiate ZXing object
            IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
            //check we have a valid result
            if (scanningResult != null) {
                //get content from Intent Result
                String scanContent = scanningResult.getContents();
                //get format name of data scanned
                String scanFormat = scanningResult.getFormatName();
                //output to UI
                Log.v(TAG, "Setting format and content");
                txtFormat.setText("FORMAT: "+scanFormat);
                txtContent.setText("CONTENT: "+scanContent);
                Log.v(TAG, "onActivityResult finished");
            }
            else{
                //invalid scan data or scan canceled
                Toast toast = Toast.makeText(thisActivity.getApplicationContext(),
                        "No scan data received!", Toast.LENGTH_SHORT);
                toast.show();
            }
        }

    }


    /**
     * A fragment class that is used for scanning in coupons
     */

    public static class CouponFragment extends Fragment {

        private static final String ARG_SECTION_NUMBER = "section_number_2";
        private Button buttonScan;
        private TextView txtFormat, txtContent;
        private Activity thisActivity;

        public CouponFragment() {

        }
        public CouponFragment(TextView f, TextView c, Button b, Activity a) {
            txtFormat = f;
            txtContent = c;
            buttonScan = b;
            thisActivity = a;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View v = inflater.inflate(R.layout.fragment_item, container, false);
            Button b = (Button) v.findViewById(R.id.scan_button);
            txtFormat = (TextView) v.findViewById(R.id.scan_format);
            txtContent = (TextView) v.findViewById(R.id.scan_content);
            if(b != null)
            {
                b.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //check for scan button
                        if(v.getId()==R.id.scan_button){
                            //instantiate ZXing integration class
                            IntentIntegrator scanIntegrator = new IntentIntegrator(thisActivity);
                            //start scanning
                            scanIntegrator.initiateScan();
                        }
                    }
                });
            }
            return v;
        }

        public void onActivityResult(int requestCode, int resultCode, Intent intent) {

            //retrieve result of scanning - instantiate ZXing object
            IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
            //check we have a valid result
            if (scanningResult != null) {
                //get content from Intent Result
                String scanContent = scanningResult.getContents();
                //get format name of data scanned
                String scanFormat = scanningResult.getFormatName();
                //output to UI
                Log.v(TAG, "Setting format and content");
                txtFormat.setText("FORMAT: "+scanFormat);
                txtContent.setText("CONTENT: "+scanContent);
                Log.v(TAG, "onActivityResult finished");
            }
            else{
                //invalid scan data or scan canceled
                Toast toast = Toast.makeText(thisActivity.getApplicationContext(),
                        "No scan data received!", Toast.LENGTH_SHORT);
                toast.show();
            }
        }
    }
}
