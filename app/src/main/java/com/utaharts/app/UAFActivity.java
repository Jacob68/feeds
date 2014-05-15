package com.utaharts.app;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;

import com.utaharts.app.data.Schedule;
import com.utaharts.app.data.datasource.UAFDataSource;
import com.utaharts.app.fragment.ArtFanCamFragment;
import com.utaharts.app.fragment.ArtistsFragment;
import com.utaharts.app.fragment.FavoritesFragment;
import com.utaharts.app.fragment.HomeFragment;
import com.utaharts.app.fragment.InfoFragment;
import com.utaharts.app.fragment.ScheduleFragment;
import com.utaharts.app.fragment.VoteLocalFragment;
import com.utaharts.app.fragment.base.BaseFragment;
import com.utaharts.app.view.ActionBarView;

import java.util.List;

public class UAFActivity extends BaseActivity implements ActionBarView.IActionBarViewListener, UAFDataSource.IUAFDatasourceCallback {


    // inner

    public interface ScheduleCallback {
        public void onReceiveSchedules(String rawSchedules);
    }


    public static final int ACTION_HEADER = -1;
    public static final int ACTION_HOME = 0;
    public static final int ACTION_SCHEDULE = 1;
    public static final int ACTION_ARTISTS = 2;
    public static final int ACTION_ART_FAN_CAM = 3;
    public static final int ACTION_VOTE_LOCAL = 4;
    public static final int ACTION_FAVORITES = 5;
    public static final int ACTION_INFO = 6;

    private static final int NUM_FRAGMENTS = 7;

    private BaseFragment[] mFragments;
    private int currentFrag;
    private int prevFrag;
    private ActionBarView mActionBar;
    private UAFDataSource dataSource;
    private List<Schedule> schedules;
    private String rawSchedules;
    private ScheduleCallback scheduleCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setUpActionBar();
        this.dataSource = new UAFDataSource();

        this.mFragments = new BaseFragment[NUM_FRAGMENTS];
        this.runAction(ACTION_HOME, -1);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_uaf;
    }

    @Override
    public void setFonts(Context context) {
        // fonts exist in fragments
    }

    private void setUpActionBar() {
        this.mActionBar = (ActionBarView) this.findViewById(R.id.action_bar);
        this.mActionBar.setActionBarViewListener(this);
    }

    /**
     * Swaps fragments in the main content view.
     */
    public void runAction(int actionId, int settingsType) {
        // Fragment option
        BaseFragment fragment;
        if (this.mFragments[actionId] != null) {
            fragment = this.mFragments[actionId];
        } else {
            this.mFragments[actionId] = fragment = this.createFragment(actionId, settingsType);
        }
        if (fragment != null) {
            // action bar prep
            final int actionFlags = fragment.getActionFlags();
            this.mActionBar.setActionFlags(actionFlags);
            this.prevFrag = this.currentFrag;
            this.currentFrag = actionId;
            this.setActionBarBackText();
            fragment.prepareActionBarTitle(this.mActionBar);
            // show fragment
            final FragmentManager fragmentManager = this.getFragmentManager();
            final FragmentTransaction fragTrans = fragmentManager.beginTransaction();
            fragTrans.replace(R.id.container, fragment, null);
            fragTrans.commit();
        }
        // here is a good place to clean up old fragments to save memory.
    }

    private BaseFragment createFragment(int fragmentId, int settingsType) {
        switch (fragmentId) {
            case ACTION_HOME:
                return new HomeFragment();
            case ACTION_SCHEDULE:
                return new ScheduleFragment();
            case ACTION_ARTISTS:
                return new ArtistsFragment();
            case ACTION_ART_FAN_CAM:
                return new ArtFanCamFragment();
            case ACTION_VOTE_LOCAL:
                return new VoteLocalFragment();
            case ACTION_FAVORITES:
                return new FavoritesFragment();
            case ACTION_INFO:
                return new InfoFragment();
        }
        return null;
    }

    public void goToSettingsFrag(int settingsType) {
        // Remove old fragment first
        if (this.mFragments[ACTION_FAVORITES] != null) {
            this.mFragments[ACTION_FAVORITES] = null;
        }
        runAction(ACTION_FAVORITES, settingsType);
    }

    public ActionBarView getActionBarView() {
        return this.mActionBar;
    }

    private void setActionBarBackText() {
        switch (this.prevFrag) {
            case ACTION_HOME:
                this.mActionBar.setBackText(R.string.home);
                break;
            case ACTION_SCHEDULE:
                this.mActionBar.setBackText(R.string.schedule);
                break;
            case ACTION_ARTISTS:
                this.mActionBar.setBackText(R.string.artists);
                break;
            case ACTION_ART_FAN_CAM:
                this.mActionBar.setBackText(R.string.art_fan_cam);
                break;
            case ACTION_VOTE_LOCAL:
                this.mActionBar.setBackText(R.string.vote_local);
                break;
            case ACTION_FAVORITES:
                this.mActionBar.setBackText(R.string.favorites);
                break;
            case ACTION_INFO:
                this.mActionBar.setBackText(R.string.info);
                break;
        }
    }

    public void loadScheduleFeed(ScheduleCallback callback) {
        // todo load feeds
        this.scheduleCallback = callback;
        this.dataSource.getScheduleFeed(this);
    }

//    public String getSchedules(ScheduleCallback callback) {
//        if (this.rawSchedules == null) {
//            this.loadScheduleFeed();
//        }
//
//        return this.rawSchedules;
//    }


    @Override
    public void onBackPressed() {
        // TODO handle back button press dependant on current fragment/view in focus
        if (this.currentFrag == ACTION_SCHEDULE) {
            this.runAction(ACTION_HOME, -1);
        } else if (this.currentFrag == ACTION_FAVORITES) {
            this.runAction(ACTION_ARTISTS, -1);
        } else if (this.currentFrag == ACTION_ARTISTS) {
            this.runAction(ACTION_HOME, -1);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void clickBack(ActionBarView sender) {
        switch (this.prevFrag) {
            case ACTION_HOME:
                this.runAction(ACTION_HOME, -1);
                break;
            case ACTION_SCHEDULE:
                this.runAction(ACTION_SCHEDULE, -1);
                break;
            case ACTION_ARTISTS:
                this.runAction(ACTION_ARTISTS, -1);
                break;
            case ACTION_ART_FAN_CAM:
                this.runAction(ACTION_ART_FAN_CAM, -1);
                break;
            case ACTION_VOTE_LOCAL:
                this.runAction(ACTION_VOTE_LOCAL, -1);
                break;
            case ACTION_FAVORITES:
                this.runAction(ACTION_FAVORITES, -1);
                break;
            case ACTION_INFO:
                this.runAction(ACTION_INFO, -1);
                break;
        }

//        final UAFDialog dialog = UAFDialog.oneButtonDialog(this);
//        dialog.setDialogTitle("Action bar back button clicked!");
//        dialog.setDialogFirstButton(R.string.close, new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                dialog.dismiss();
//            }
//        });
//        dialog.show();
    }

    @Override
    public void dataReceived(String result, String error) {
        if (error == null) {
            // todo save result
            if (this.scheduleCallback != null) {
                this.scheduleCallback.onReceiveSchedules(result);
            }
            this.rawSchedules = result;
        }
    }
}