package com.utaharts.app.fragment;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.utaharts.app.R;
import com.utaharts.app.data.Event;
import com.utaharts.app.fragment.base.BaseFragment;
import com.utaharts.app.view.ActionBarView;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by jacoblafazia on 5/14/14.
 */
public class ArtistsFragment extends BaseFragment {

    // inner

    private class FeedListAdapter extends ArrayAdapter<String> {
        HashMap<String, Integer> mIdMap = new HashMap<String, Integer>();

        public FeedListAdapter(Context context, int textViewResourceId, List<String> objects) {
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

        @Override
        public boolean areAllItemsEnabled() {
            return true;
        }
    }

    // instance

    private View mLayout;
    private TextView textView;
    private ListView itemList;

    public ArtistsFragment() {
    }

    @Override
    public void onRootViewCreated(View rootView) {
        this.mLayout = rootView;
        this.textView = (TextView) this.mLayout.findViewById(R.id.text_view);
        this.itemList = (ListView) this.mLayout.findViewById(R.id.item_list);

        this.displayItems();
    }

    /**
     * Display items as individual JSON objects
     */
    private void displayItems() {
        ArrayList<String> items = new ArrayList<String>();
        List<JSONObject> events = this.getUAFActivity().getJsonList();
        for (JSONObject e : events) {
            // TODO format json tostring to insert newline after each comma
            items.add(e.toString());
        }

        final FeedListAdapter adapter = new FeedListAdapter(this.getActivity(), R.layout.feed_list_item, items);
        this.itemList.setAdapter(adapter);
        this.itemList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // TODO handle item click
            }
        });

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
