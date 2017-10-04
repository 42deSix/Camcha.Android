package com.softmilktea.camcha;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SEJIN on 2017-10-03.
 */

public class MainActivity extends AppCompatActivity {
    static final String TAG = "MainActivity";
    static final int NUM_ITEMS = 2;
    private ViewPager mPager;
    private SlidePagerAdapter mPagerAdapter;

    private ActionBarDrawerToggle drawerToggle;
    private List<SlideMenuItems> menuList = new ArrayList<>();
//    private Fragment reportFragment = new ReportFragment();



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
                return new MenuFragment();
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
        mPager = (ViewPager) findViewById(R.id.pager);
        mPagerAdapter = new SlidePagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);
        mPager.setCurrentItem(1);

        final FragmentTransaction transaction = getSupportFragmentManager()
                .beginTransaction();

        ImageButton menuButton = (ImageButton) findViewById(R.id.menu_button);
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
//        createMenuList();

    }

//    private void createMenuList() {
//        SlideMenuItems menuItem0 = new SlideMenuItems(reportFragment, "신고", R.drawable.icon_report);
//        menuList.add(menuItem0);
////        SlideMenuItems menuItem1 = new SlideMenuItems(Fragment.CLOSE, R.drawable.icon_report);
////        menuList.add(menuItem1);
////        SlideMenuItems menuItem2 = new SlideMenuItems(Fragment.BUILDING, R.drawable.icon_setting);
////        menuList.add(menuItem2);
//
//    }

}