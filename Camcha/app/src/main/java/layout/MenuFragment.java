package layout;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.softmilktea.camcha.LockableViewPager;
import com.softmilktea.camcha.MenuItemsViewManager;
import com.softmilktea.camcha.R;
import com.softmilktea.camcha.SlideMenuItem;

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
    private LockableViewPager mPager;
    private RootFragment rootFragment;
    private List<View> menuItemViewList = new ArrayList<>();

	public MenuFragment(){}
    public void setMPager(LockableViewPager mPager) {
        this.mPager = mPager;
    }
    public void setRootFragment(RootFragment rootFragment) {
        this.rootFragment = rootFragment;
    }
	public void setSavedInstanceState(Bundle savedInstanceState) {
		this.menuItemManager = new MenuItemsViewManager(MenuFragment.this, savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		createMenuList();
		return menuItemManager.showMenuContent(mPager, rootFragment, menuList);
	}

	private void createMenuList() {
		menuList = new ArrayList();
		SlideMenuItem menuItem0 = new SlideMenuItem(new ReportFragment(), "신고", R.drawable.icon_report);
		menuList.add(menuItem0);
        SlideMenuItem menuItem1 = new SlideMenuItem(new SettingFragment(), "설정", R.drawable.icon_setting);
        menuList.add(menuItem1);
//        SlideMenuItem menuItem2 = new SlideMenuItem(Fragment.BUILDING, R.drawable.icon_setting);
//        menuList.add(menuItem2);

	}
}
