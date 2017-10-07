package com.softmilktea.camcha;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import layout.ReportFragment;
import layout.RootFragment;

/**
 * Created by SEJIN on 2017-10-05.
 */

public class MenuItemsViewManager {
    private static final String TAG = "MenuItemsViewManager";
    private final int height = 50;
    private Fragment menuFragment;
    private Bundle savedInstanceState;

    public MenuItemsViewManager(Fragment menuFragment, Bundle savedInstanceState) {
        this.menuFragment = menuFragment;
        this.savedInstanceState = savedInstanceState;
    }

    public View showMenuContent(final LockableViewPager mPager, final RootFragment rootFragment, List<SlideMenuItem> menuList) {
        int size = menuList.size();

        View menuView = menuFragment.getLayoutInflater(savedInstanceState).inflate(R.layout.fragment_menu, null);
        for (int i = 0; i < size; i++) {
            final SlideMenuItem logicalMenuItem = menuList.get(i);
            LinearLayout menuItemLayout = createMenuItem(menuView, logicalMenuItem);
            menuItemLayout.setOnClickListener(new LinearLayout.OnClickListener() {
                @Override
                public void onClick(View v) {
                    rootFragment.replaceRootView(logicalMenuItem.getTargetFragment());
                    mPager.setCurrentItem(1);
                }
            });
        }
        return menuView;
    }

    public LinearLayout createMenuItem(View menuView, SlideMenuItem menuInfo) {
        final float scale = menuView.getResources().getDisplayMetrics().density;
        View menuItemView = menuFragment.getLayoutInflater(savedInstanceState).inflate(R.layout.layout_menu_item, null);
//        menuView.findViewById(R.id.menu_item_button);
        View menuItemIconView = menuItemView.findViewById(R.id.menu_item_icon);
        View menuItemTextView = menuItemView.findViewById(R.id.menu_item_text);

        ImageView menuItemIcon = new ImageView(menuItemIconView.getContext());
        menuItemIcon.setImageResource(menuInfo.getIcon());
        LinearLayout.LayoutParams iconLayoutParams  = new LinearLayout.LayoutParams((int)(height*scale), (int)(height*scale));
        menuItemIcon.setLayoutParams(iconLayoutParams);

        menuItemIcon.setVisibility(View.VISIBLE);
        menuItemIcon.setEnabled(false);

        TextView menuItemText = new TextView(menuItemTextView.getContext());
        menuItemText.setText(menuInfo.getName());
        menuItemText.setTextColor(Color.parseColor("#000000"));
        menuItemText.setPadding((int)(20*scale), 0, 0, 0);
        menuItemText.setVisibility(View.VISIBLE);
        menuItemText.setEnabled(false);

        LinearLayout menuItem = (LinearLayout)menuItemView;
        menuItem.addView(menuItemIcon);
        menuItem.addView(menuItemText);
        menuItem.setVisibility(View.VISIBLE);
        menuItem.setEnabled(true);
        ((LinearLayout)menuView).addView(menuItem);

        return menuItem;
    }



}
