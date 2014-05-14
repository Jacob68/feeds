package com.utaharts.app;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Point;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Display;
import android.view.Window;
import android.view.WindowManager;

import com.utaharts.app.data.datasource.UAFDataSource;
import com.utaharts.app.iface.IInflate;
import com.utaharts.app.iface.ISetFonts;

public abstract class BaseActivity extends Activity implements ISetFonts, IInflate {

    /**
     * Instance of progress dialog
     */
    private ProgressDialog mProgressDialog;

    /**
     * Api instance
     */
    private UAFDataSource uafDataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        final boolean inflated = this.inflate(this);
        if (inflated) {
            this.setFonts(this);
        }
    }

    @Override
    public boolean inflate(Context context) {
        int id = this.getLayoutId();
        if (id != 0) {
            this.setContentView(id);
            return true;
        } else {
            return false;
        }
    }

    /**
     * @return Return this activity's JaybirdApi instance
     */
    public UAFDataSource getUafDataSource() {
        if (this.uafDataSource == null) {
            this.uafDataSource = new UAFDataSource();
        }
        return this.uafDataSource;
    }

    /**
     * @return Return the width of the display in pixels.
     */
    protected int getDisplayWidth() {
        // acquire the device display width
        final WindowManager windowManager = this.getWindowManager();
        final Display display = windowManager.getDefaultDisplay();
        final Point size = new Point();
        display.getSize(size);
        return size.x;
    }

    /**
     * Method to check Internet and network connections.
     */
    public void checkInternetConnection(final int tag) {
        if (isNetworkAvailable()) {
            this.onConnectionTestResult(true, tag);
        } else {
            this.onConnectionTestResult(false, tag);
        }
    }

    /**
     * Default implementation does nothing, subclasses should override this to
     * perform their own handle.
     *
     * @param hasConnection
     * @param tag
     */
    protected void onConnectionTestResult(boolean hasConnection, int tag) {
    }

    /**
     * Checks if device is connected to a network.
     *
     * @return true if device connected to network.
     */
    public boolean isNetworkAvailable() {
        ConnectivityManager connectivity = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                for (int i = 0; i < info.length; i++)
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
        }
        return false;
    }

    public void showNoInternetDialog(Context context, DialogInterface.OnClickListener listener) {
        final UAFDialog dialog = UAFDialog.oneButtonDialog(context);
        dialog.setDialogTitle(R.string.notice);
        dialog.setDialogBody(R.string.no_internet_dialog);
        dialog.setDialogFirstButton(android.R.string.ok, listener);
        dialog.show();
    }

    // Progress Dialogs

    /**
     * Show a progress dialog with the default title
     */
    public void showProgressDialog() {
        this.showProgressDialog(R.string.loading);
    }

    /**
     * Show a progress dialog with a specific title
     *
     * @param titleId
     */
    public void showProgressDialog(int titleId) {
        if (this.mProgressDialog == null) {
            this.mProgressDialog = new ProgressDialog(this);
            this.mProgressDialog.setCancelable(false);
        }
        this.mProgressDialog.setTitle(titleId);
        if (!this.isProgressDialogShowing()) {
            this.mProgressDialog.show();
        }
    }

    /**
     * Get the display status of the current dialog
     *
     * @return
     */
    public boolean isProgressDialogShowing() {
        return this.mProgressDialog != null && this.mProgressDialog.isShowing();
    }

    /**
     * Hide the progress dialog
     */
    public boolean hideProgressDialog() {
        if (this.isProgressDialogShowing()) {
            this.mProgressDialog.dismiss();
            return true;
        }
        return false;
    }

}