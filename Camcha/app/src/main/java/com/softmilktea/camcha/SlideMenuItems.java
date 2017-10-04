package com.softmilktea.camcha;

import android.support.v4.app.Fragment;
import android.content.res.Resources;
import android.graphics.drawable.Icon;

/**
 * Created by SEJIN on 2017-10-03.
 */

public class SlideMenuItems {
    private Fragment targetFragment;
    private String name;
    private int icon;

    public SlideMenuItems(Fragment targetFragment, String name, int icon) {
        this.targetFragment = targetFragment;
        this.name = name;
        this.icon = icon;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getIcon() {
        return icon;
    }
    public void setIcon(int icon) {
        this.icon = icon;
    }
    public Fragment getTargetFragment() {
        return targetFragment;
    }
    public void setTargetFragment(Fragment targetFragment) {
        this.targetFragment = targetFragment;
    }

}
