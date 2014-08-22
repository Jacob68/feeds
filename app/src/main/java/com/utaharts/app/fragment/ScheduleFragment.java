package com.utaharts.app.fragment;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.utaharts.app.R;
import com.utaharts.app.UAFActivity;
import com.utaharts.app.data.Event;
import com.utaharts.app.fragment.base.BaseFragment;
import com.utaharts.app.view.ActionBarView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Displays event schedule data.
 *
 * Created by jacoblafazia on 5/14/14.
 */
public class ScheduleFragment extends BaseFragment {

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


    private View mLayout;
    private String rawSchedule;
    private TextView textView;
    private ListView itemList;

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
        this.itemList = (ListView) this.mLayout.findViewById(R.id.item_list);

        this.displayItems();
    }

    private void displayItems() {
        ArrayList<String> items = new ArrayList<String>();
        List<Event> events = this.getUAFActivity().getEvents();
        for (Event e : events) {
            String output = String.format("%s\n%s at %s.\nLocation: %s", e.name, e.day, e.starttime, e.stage);
            items.add(output);
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
