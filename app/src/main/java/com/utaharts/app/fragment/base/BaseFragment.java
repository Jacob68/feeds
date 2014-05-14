package com.utaharts.app.fragment.base;

import android.app.Activity;
import android.graphics.Point;
import android.view.Display;
import android.view.WindowManager;

import com.utaharts.app.BaseActivity;
import com.utaharts.app.UAFActivity;
import com.utaharts.app.data.datasource.UAFDataSource;
import com.utaharts.app.view.ActionBarView;


public abstract class BaseFragment extends EasyFragment {

    public UAFActivity getUAFActivity() {
        return (UAFActivity) this.getActivity();
    }


    protected ActionBarView getActionBarView() {
        final UAFActivity activity = this.getUAFActivity();
        return activity.getActionBarView();
    }

    public abstract int getActionFlags();

    public abstract void prepareActionBarTitle(ActionBarView actionBar);

    /**
     * @return Return this activity's UAFDataSource instance
     */
    public UAFDataSource getUafDataSource() {
        BaseActivity jaybirdActivity = (BaseActivity) this.getActivity();
        return jaybirdActivity.getUafDataSource();
    }

    /**
     * @return Return the width of the display in pixels.
     */
    protected int getDisplayWidth() {
        // acquire the device display width
        final Activity activity = this.getActivity();
        final WindowManager windowManager = activity.getWindowManager();
        final Display display = windowManager.getDefaultDisplay();
        final Point size = new Point();
        display.getSize(size);
        return size.x;
    }

}
