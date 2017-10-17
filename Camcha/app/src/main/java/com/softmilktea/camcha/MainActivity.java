package com.softmilktea.camcha;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

import layout.MenuFragment;
import layout.RootFragment;

/**
 * Created by SEJIN on 2017-10-03.
 */

public class MainActivity extends AppCompatActivity {
    private LockableViewPager mPager;
    private SlidePagerAdapter mPagerAdapter;

//    private ReportFragment mReportFragment = new ReportFragment();
    private RootFragment mRootFragment;
    private MenuFragment mMenuFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* side menu */
        mPager = (LockableViewPager) findViewById(R.id.pager);
        mPager.setmSwipeable(false);
        mPagerAdapter = new SlidePagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);
        mPager.setCurrentItem(1);

        mRootFragment = new RootFragment();
        mMenuFragment = new MenuFragment();
        mMenuFragment.setMPager(mPager);
        mMenuFragment.setmRootFragment(mRootFragment);
        mMenuFragment.setSavedInstanceState(savedInstanceState);

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

    /* If you press the back butteon when the menu is turned on,
     * this code would make you turn back to main screen.
     */
    @Override
    public void onBackPressed() {
        if(mPager.getCurrentItem() == 0) {
            mPager.setCurrentItem(1);
        }
        else {
            super.onBackPressed();
        }
    }

    public class SlidePagerAdapter extends FragmentPagerAdapter {
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
                return mMenuFragment;
            else
                return mRootFragment;
        }

        @Override
        public int getCount() {
            return BaseApplication.NUM_ITEMS;
        }
    }
}