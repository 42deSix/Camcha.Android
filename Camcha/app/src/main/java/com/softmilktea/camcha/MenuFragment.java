package com.softmilktea.camcha;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Example about replacing fragments inside a ViewPager. I'm using
 * android-support-v7 to maximize the compatibility.
 * 
 * @author Dani Lao (@dani_lao)
 * 
 */
public class MenuFragment extends Fragment {
	private static final String TAG = "MenuFragment";

	private List<SlideMenuItem> menuList;
	private MenuItemsViewManager menuItemManager;
    private List<View> menuItemViewList = new ArrayList<>();

	public MenuFragment(){}
//	public MenuFragment(Bundle savedInstanceState, List<SlideMenuItem> menuList) {
	public void setSavedInstanceState(Bundle savedInstanceState) {
		this.menuItemManager = new MenuItemsViewManager(MenuFragment.this, savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
//		View view = inflater.inflate(R.layout.fragment_menu, container, false);
		createMenuList();
		return menuItemManager.showMenuContent(menuList);
//		return view;
	}

	private void createMenuList() {
		menuList = new ArrayList();
		SlideMenuItem menuItem0 = new SlideMenuItem(new ReportFragment(), "신고", R.drawable.icon_report);
		menuList.add(menuItem0);
        SlideMenuItem menuItem1 = new SlideMenuItem(new ReportFragment(), "설정", R.drawable.icon_setting);
        menuList.add(menuItem1);
//        SlideMenuItem menuItem2 = new SlideMenuItem(Fragment.BUILDING, R.drawable.icon_setting);
//        menuList.add(menuItem2);

	}
}
