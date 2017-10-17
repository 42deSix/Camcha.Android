package com.softmilktea.camcha;

import android.support.v4.app.Fragment;
import android.content.res.Resources;
import android.graphics.drawable.Icon;

/**
 * Created by SEJIN on 2017-10-03.
 */

public class SlideMenuItem {
    private Fragment targetFragment;
    private String mIconName;
    private int mIcon;

    public SlideMenuItem(Fragment targetFragment, String name, int icon) {
        this.targetFragment = targetFragment;
        this.mIconName = name;
        this.mIcon = icon;
    }

    public String getName() {
        return mIconName;
    }
    public void setName(String name) {
        this.mIconName = name;
    }
    public int getIcon() {
        return mIcon;
    }
    public void setIcon(int icon) {
        this.mIcon = icon;
    }
    public Fragment getTargetFragment() {
        return targetFragment;
    }
    public void setTargetFragment(Fragment targetFragment) {
        this.targetFragment = targetFragment;
    }

}
