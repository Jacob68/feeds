package com.utaharts.app.fragment;

import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.utaharts.app.R;
import com.utaharts.app.UAFActivity;
import com.utaharts.app.fragment.base.BaseFragment;
import com.utaharts.app.view.ActionBarView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by jacoblafazia on 5/13/14.
 */
public class HomeFragment extends BaseFragment {

    // inner

    private class NavListAdapter extends ArrayAdapter<String> {
        HashMap<String, Integer> mIdMap = new HashMap<String, Integer>();

        public NavListAdapter(Context context, int textViewResourceId, List<String> objects) {
            super(context, textViewResourceId, objects);
            for (int i = 0; i < objects.size(); ++i) {
                mIdMap.put(objects.get(i), i);
            }
        }

        @Override
        public long getItemId(int position) {
            String item = getItem(position);
            return mIdMap.get(item);
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

    }

    // instance

    private View mLayout;
    private ListView navList;

    public HomeFragment() {
    }

    @Override
    public void onRootViewCreated(View rootView) {
        this.mLayout = rootView;
        this.navList = (ListView) this.mLayout.findViewById(R.id.nav_list);

        final Resources res = this.getResources();
        String[] navItems = res.getStringArray(R.array.nav_items);

        final ArrayList<String> list = new ArrayList<String>();
        for (int i = 0; i < navItems.length; ++i) {
            list.add(navItems[i]);
        }
        final NavListAdapter adapter = new NavListAdapter(this.getActivity(), R.layout.nav_list_item, list);
        navList.setAdapter(adapter);

        this.navList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // TODO click listening
                UAFActivity activity = HomeFragment.this.getUAFActivity();
                switch (position) {
                    case 0:
                        activity.runAction(UAFActivity.ACTION_SCHEDULE, -1);
                        break;
                    case 1:
                        activity.runAction(UAFActivity.ACTION_ARTISTS, -1);
                        break;
                }
            }
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    public void setFonts(Context context) {
        // TODO set fonts
//        Typeface font = UAFFonts.getFont(context, UAFFonts.FONT_NORMAL);
    }

    @Override
    public int getActionFlags() {
        return ActionBarView.ACTION_HIDE_BAR;
    }

    @Override
    public void prepareActionBarTitle(ActionBarView actionBar) {
        actionBar.setTitleText(R.string.home);
    }
}
