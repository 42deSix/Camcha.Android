package com.softmilktea.camcha;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

/**
 * Created by SEJIN on 2017-10-03.
 */

public class MainActivity extends AppCompatActivity {
    static final String TAG = "MainActivity";
    static final int NUM_ITEMS = 2;
    private LockableViewPager mPager;
    private SlidePagerAdapter mPagerAdapter;
    private MenuItemsViewManager menuItemsManager;

//    private ReportFragment reportFragment = new ReportFragment();
    private MenuFragment menuFragment;


    public class SlidePagerAdapter extends FragmentPagerAdapter {
        private Bundle savedInstanceState;
        public SlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
			/*
			 * IMPORTANT: This is the point. We create a MainFragment acting as
			 * a container for other fragments
			 */
            if (position == 0)
                return menuFragment;
            else
                return new MainFragment();
        }

        @Override
        public int getCount() {
            return NUM_ITEMS;
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* side menu */
        menuFragment = new MenuFragment();
        menuFragment.setSavedInstanceState(savedInstanceState);
        mPager = (LockableViewPager) findViewById(R.id.pager);
        mPager.setSwipeable(false);
        mPagerAdapter = new SlidePagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);
        mPager.setCurrentItem(1);

        final FragmentTransaction transaction = getSupportFragmentManager()
                .beginTransaction();

        ImageButton menuButton = (ImageButton) findViewById(R.id.menu_item_icon);
        menuButton.setOnClickListener(
                new ImageButton.OnClickListener() {
                    public void onClick(View v) {
                        int currentItem = mPager.getCurrentItem();
                        if(currentItem == 0) {
                            mPager.setCurrentItem(1);
                        }
                        else {
                            mPager.setCurrentItem(0);
                        }
                    }
        });
    }

}