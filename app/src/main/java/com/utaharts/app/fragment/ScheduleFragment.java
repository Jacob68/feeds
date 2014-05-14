package com.utaharts.app.fragment;

import android.content.Context;
import android.view.View;

import com.utaharts.app.R;
import com.utaharts.app.fragment.base.BaseFragment;
import com.utaharts.app.view.ActionBarView;

/**
 * Created by jacoblafazia on 5/14/14.
 */
public class ScheduleFragment extends BaseFragment {

    private View mLayout;

    public ScheduleFragment() {
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_schedule;
    }

    @Override
    public void onRootViewCreated(View rootView) {
        this.mLayout = rootView;
    }

    @Override
    public void setFonts(Context context) {
        // TODO set fonts
    }

    @Override
    public int getActionFlags() {
        return ActionBarView.ACTION_ALL;
    }

    @Override
    public void prepareActionBarTitle(ActionBarView actionBar) {
        actionBar.setTitleText(R.string.schedule);
    }

}
