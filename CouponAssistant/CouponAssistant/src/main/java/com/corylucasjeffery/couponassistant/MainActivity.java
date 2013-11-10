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
import android.text.InputFilter;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class MainActivity extends ActionBarActivity implements ActionBar.TabListener {

    SectionsPagerAdapter mSectionsPagerAdapter;

    ViewPager mViewPager;
    private static String TAG = "MAIN";

    private String upc;
    private String upcFormat;
    private String description;

    private String exp = "2013-12-31";
    private String image = "imagey-wimagey";
    private String login = "user1";
    private String pass = "pass1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Set up the action bar.
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(), this);

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

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        //retrieve result of scanning - instantiate ZXing object
        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        //check we have a valid result
        if (scanningResult != null) {
            String upc = scanningResult.getContents();
            String upcFormat = scanningResult.getFormatName();
            String toastShow = upc+" "+upcFormat;
            Toast.makeText(this, toastShow, Toast.LENGTH_LONG).show();

            // here we need to find out if it is an item or coupon, by checking the fragment in view?
            storeItem(upc, upcFormat);
            storeCoupon(upc, upcFormat);
        }
    }

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
            case R.id.settings_login:
                openLogin();
                return true;
            case R.id.settings_shopping_cart:
                openCart();
                return true;
            case R.id.settings_stats:
                openStats();
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

    private void openLogin() {
        Toast.makeText(this, "Login", Toast.LENGTH_LONG).show();
    }

    private void openCart() {
        Toast.makeText(this, "Shopping Cart", Toast.LENGTH_LONG).show();
    }

    private void openStats() {
        Toast.makeText(this, "Statistics", Toast.LENGTH_LONG).show();
    }

    public void storeItem(String upc, String upcFormat) {
        //PhpWrapper db = new PhpWrapper(upc, exp, login, pass, image);
        //db.submitItem();
        Toast.makeText(this, "store item "+upc, Toast.LENGTH_LONG).show();
        DbSubmitItem db = new DbSubmitItem(login, pass, upc, description);
        db.execute();
    }

    public void storeCoupon(String upc, String upcFormat) {
        Toast.makeText(this, "store coupon "+upc, Toast.LENGTH_LONG).show();
        DbSubmitCoupon db = new DbSubmitCoupon(login, pass, upc, exp, image);
        db.execute();
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
        private Activity activity;
        public SectionsPagerAdapter(FragmentManager fm, Activity a) {
            super(fm);
            this.activity = a;
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
                    fragment = new ItemFragment(activity);
                    args.putInt(ItemFragment.ARG_SECTION_NUMBER, position + 1);
                    break;
                case 1:
                    fragment = new CouponFragment(activity);
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
        private Activity thisActivity;

        public ItemFragment() {

        }

        public ItemFragment(Activity a) {
            thisActivity = a;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View v = inflater.inflate(R.layout.fragment_item, container, false);
            Button b = (Button) v.findViewById(R.id.scan_button);
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
    }

    /**
     * A fragment class that is used for scanning in coupons
     */

    public static class CouponFragment extends Fragment {

        private static final String ARG_SECTION_NUMBER = "section_number_2";
        private Activity thisActivity;

        public CouponFragment() {

        }
        public CouponFragment(Activity a) {
            thisActivity = a;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View v = inflater.inflate(R.layout.fragment_item, container, false);
            Button b = (Button) v.findViewById(R.id.scan_button);
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
    }
}
