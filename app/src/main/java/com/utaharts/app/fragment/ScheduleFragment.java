package com.utaharts.app.fragment;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.utaharts.app.R;
import com.utaharts.app.UAFActivity;
import com.utaharts.app.fragment.base.BaseFragment;
import com.utaharts.app.view.ActionBarView;

/**
 * Created by jacoblafazia on 5/14/14.
 */
public class ScheduleFragment extends BaseFragment {


    private View mLayout;
    private String rawSchedule;
    private TextView textView;

    public ScheduleFragment() {
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_schedule;
    }

    @Override
    public void onRootViewCreated(View rootView) {
        this.mLayout = rootView;
        this.textView = (TextView) this.mLayout.findViewById(R.id.text_view);
        this.getUAFActivity().loadScheduleFeed(new UAFActivity.ScheduleCallback() {
            @Override
            public void onReceiveSchedules(String rawSchedules) {
                ScheduleFragment self = ScheduleFragment.this;
                self.rawSchedule = rawSchedules;
                self.textView.setText(rawSchedules);
            }
        });
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
