package com.utaharts.app.fragment.base;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import com.utaharts.app.iface.IEasyViewGroup;
import com.utaharts.app.view.ViewFunctions;

/**
 * RelativeLayout with common tasks performed during construction, like
 * inflation, and settings fonts.
 *
 * @author evan
 */
public abstract class EasyRelativeLayout extends RelativeLayout implements IEasyViewGroup {

    public EasyRelativeLayout(Context context) {
        super(context);
        this.init(context);
    }

    public EasyRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.init(context);
    }

    public EasyRelativeLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.init(context);
    }

    protected void init(Context context) {
        ViewFunctions.init(context, this);
    }

    @Override
    public boolean inflate(Context context) {
        return ViewFunctions.inflate(context, this);
    }

    @Override
    public void start(Context context) {
        // Perform your initialization code here.
    }
}
