package com.utaharts.app.fragment;

import android.content.Context;
import android.view.View;

import com.utaharts.app.R;
import com.utaharts.app.fragment.base.BaseFragment;
import com.utaharts.app.view.ActionBarView;

/**
 * Created by jacoblafazia on 5/14/14.
 */
public class ArtistsFragment extends BaseFragment {

    public ArtistsFragment() {
    }

    @Override
    public void onRootViewCreated(View rootView) {

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_artists;
    }

    @Override
    public void setFonts(Context context) {

    }

    @Override
    public int getActionFlags() {
        return 0;
    }

    @Override
    public void prepareActionBarTitle(ActionBarView actionBar) {
        actionBar.setTitleText(R.string.artists);
    }
}
