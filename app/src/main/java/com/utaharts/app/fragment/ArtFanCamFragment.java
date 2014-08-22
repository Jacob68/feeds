package com.utaharts.app.fragment;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.utaharts.app.R;
import com.utaharts.app.fragment.base.BaseFragment;
import com.utaharts.app.view.ActionBarView;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jacoblafazia on 5/14/14.
 */
public class ArtFanCamFragment extends BaseFragment {

    private View mLayout;
    private TextView jsonText;

    public ArtFanCamFragment() {
    }

    @Override
    public void onRootViewCreated(View rootView) {
        this.mLayout = rootView;
        this.jsonText = (TextView) this.mLayout.findViewById(R.id.json_text_view);

        this.displayItems();
    }

    /**
     * Display items as individual JSON objects
     */
    private void displayItems() {
        JSONObject events = this.getUAFActivity().getEventsAsJson();

        // format json tostring result to be more readable
        String json = events.toString();
        StringBuilder sb = new StringBuilder();
        String[] splits = json.split("\",+");
        for (String split : splits) {
            sb.append(split).append("\",\n");
        }
        String output = sb.toString().trim();
        this.jsonText.setText(output);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_art_fan_cam;
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

    }
}
