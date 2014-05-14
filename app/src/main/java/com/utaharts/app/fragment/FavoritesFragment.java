package com.utaharts.app.fragment;

import android.content.Context;
import android.view.View;

import com.utaharts.app.R;
import com.utaharts.app.fragment.base.BaseFragment;
import com.utaharts.app.view.ActionBarView;

/**
 * Created by jacoblafazia on 5/14/14.
 */
public class FavoritesFragment extends BaseFragment {

    public FavoritesFragment() {
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_favorites;
    }

    @Override
    public void onRootViewCreated(View rootView) {

    }

    @Override
    public void setFonts(Context context) {
        // TODO set fonts
    }

    @Override
    public int getActionFlags() {
        return 0;
    }

    @Override
    public void prepareActionBarTitle(ActionBarView actionBar) {

    }
}
