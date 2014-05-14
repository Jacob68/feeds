package com.utaharts.app.view;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.utaharts.app.R;
import com.utaharts.app.fragment.base.EasyRelativeLayout;

public class ActionBarView extends EasyRelativeLayout implements View.OnClickListener {

    // inner

    // interface for the view
    public static interface IActionBarViewListener {
        public void clickBack(ActionBarView sender);
    }

    // static

    public static final int ACTION_HIDE_BAR = 0x1;
    public static final int ACTION_NONE = 0x2;
    public static final int ACTION_ALL = 0x4;

    // instance

    private IActionBarViewListener listener;
    private TextView labelBack;
    private TextView labelTitle;
    private View buttonBack;

    public ActionBarView(Context context) {
        super(context);
    }

    public ActionBarView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ActionBarView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void start(final Context context) {
        super.start(context);
        this.labelBack = (TextView) this.findViewById(R.id.text_back_label);
        this.labelTitle = (TextView) this.findViewById(R.id.text_label);
        this.buttonBack = this.findViewById(R.id.button_back);
        this.buttonBack.setOnClickListener(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.view_action_bar;
    }

    @Override
    public void setFonts(Context context) {
        // TODO set fonts
//        Typeface font = UAFFonts.getFont(context, UAFFonts.FONT_NORMAL);
//        this.labelBack.setTypeface(font);
//        this.labelTitle.setTypeface(font);
    }

    public void setActionBarViewListener(IActionBarViewListener listener) {
        this.listener = listener;
    }

    public void setBackText(String text) {
        final String upper = text.toUpperCase();
        this.labelBack.setText(upper);
    }

    public void setBackText(int resId) {
        final Resources res = this.getResources();
        final String text = res.getString(resId);
        this.setBackText(text);
    }

    public void setTitleText(String text) {
        final String upper = text.toUpperCase();
        this.labelTitle.setText(upper);
    }

    public void setTitleText(int resId) {
        final Resources res = this.getResources();
        final String text = res.getString(resId);
        this.setTitleText(text);
    }

    public void setActionFlags(int actionFlags) {
        if ((actionFlags & ACTION_HIDE_BAR) == ACTION_HIDE_BAR) {
            // hide the whole action bar
            this.setVisibility(View.GONE);
        } else {
            // show the whole action bar
            this.setVisibility(View.VISIBLE);
            if ((actionFlags & ACTION_NONE) == ACTION_NONE) {
                // hide all actions
                this.buttonBack.setVisibility(View.GONE);
            } else {
                // enable each individual action
                if ((actionFlags & ACTION_ALL) == ACTION_ALL) {
                    this.buttonBack.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    @Override
    public void onClick(View view) {
        if (this.listener != null) {
            if (this.buttonBack == view) {
                this.listener.clickBack(this);
                return;
            }
        }
    }
}
