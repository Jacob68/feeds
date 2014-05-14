package com.utaharts.app;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.utaharts.app.iface.IInflate;
import com.utaharts.app.iface.ISetFonts;

/**
 * Standardized dialog layouts
 */
public class UAFDialog extends Dialog implements ISetFonts, IInflate {

    public static final int BUTTON_FIRST = 0;
    public static final int BUTTON_SECOND = 1;
    public static final int BUTTON_THIRD = 2;

    private int mLayoutId;

    public UAFDialog(final Context context) {
        super(context, R.style.CustomDialogTheme);
    }

    public static UAFDialog oneButtonDialog(final Context context) {
        final UAFDialog dialog = new UAFDialog(context);
        dialog.inflate(context, R.layout.dialog_onebutton);
        dialog.setFonts(context);
        return dialog;
    }

    public static UAFDialog oneButtonDialog(final Context context, final int titleId, final int bodyId, final int firstButtonTitleId,
                                                final OnClickListener firstButtonListener) {
        final UAFDialog dialog = new UAFDialog(context);
        dialog.inflate(context, R.layout.dialog_onebutton);
        dialog.setDialogTitle(titleId);
        dialog.setDialogBody(bodyId);
        dialog.setDialogFirstButton(firstButtonTitleId, firstButtonListener);
        dialog.setFonts(context);
        return dialog;
    }

    public static UAFDialog twoButtonDialog(final Context context) {
        final UAFDialog dialog = new UAFDialog(context);
        dialog.inflate(context, R.layout.dialog_twobutton);
        dialog.setFonts(context);
        return dialog;
    }

    public static UAFDialog twoButtonDialog(final Context context, final int titleId, final int bodyId, final int firstButtonTitleId,
                                                final OnClickListener firstButtonListener, final int secondButtonTitleId,
                                                final OnClickListener secondButtonListener) {
        final UAFDialog dialog = new UAFDialog(context);
        dialog.inflate(context, R.layout.dialog_twobutton);
        dialog.setDialogTitle(titleId);
        dialog.setDialogBody(bodyId);
        dialog.setDialogFirstButton(firstButtonTitleId, firstButtonListener);
        dialog.setDialogSecondButton(secondButtonTitleId, secondButtonListener);
        dialog.setFonts(context);
        return dialog;
    }

    public static UAFDialog threeButtonDialog(final Context context) {
        final UAFDialog dialog = new UAFDialog(context);
        dialog.inflate(context, R.layout.dialog_threebutton);
        dialog.setFonts(context);
        return dialog;
    }

    public static UAFDialog promptDialog(final Context context) {
        final UAFDialog dialog = new UAFDialog(context);
        dialog.inflate(context, R.layout.dialog_prompt);
        dialog.setFonts(context);
        return dialog;
    }

    public static UAFDialog promptDialog(final Context context, final int titleId, final int bodyId, final int firstButtonTitleId,
                                             final OnClickListener firstButtonListener, final int secondButtonTitleId,
                                             final OnClickListener secondButtonListener, final int defaultTextValueId) {
        final UAFDialog dialog = new UAFDialog(context);
        dialog.inflate(context, R.layout.dialog_prompt);
        dialog.setDialogTitle(titleId);
        dialog.setDialogBody(bodyId);
        dialog.setDialogFirstButton(firstButtonTitleId, firstButtonListener);
        dialog.setDialogSecondButton(secondButtonTitleId, secondButtonListener);
        dialog.setDialogTextValue(defaultTextValueId);
        dialog.setFonts(context);
        return dialog;
    }

    protected boolean inflate(final Context context, final int layoutId) {
        this.mLayoutId = layoutId;
        return this.inflate(context);
    }

    @Override
    public boolean inflate(Context context) {
        final int id = this.getLayoutId();
        if (id != 0) {
            this.setContentView(id);
            return true;
        } else {
            return false;
        }
    }

    public UAFDialog setDialogTitle(final int textId) {
        String text = this.getStringResource(textId);
        return this.setDialogTitle(text);
    }

    public UAFDialog setDialogTitle(final String text) {
        final TextView textView = this.getTitleTextView();
        if (text == null || text.length() == 0) {
            textView.setVisibility(View.GONE);
        } else {
            textView.setVisibility(View.VISIBLE);
            textView.setText(text);
        }
        return this;
    }

    public TextView getTitleTextView() {
        return (TextView) this.findViewById(R.id.label_title);

    }

    public UAFDialog setDialogBody(final int textId) {
        String text = this.getStringResource(textId);
        return this.setDialogBody(text);
    }

    public UAFDialog setDialogBody(final String text) {
        final TextView textView = this.getBodyTextView();
        if (text == null || text.length() == 0) {
            textView.setVisibility(View.GONE);
        } else {
            textView.setVisibility(View.VISIBLE);
            textView.setText(text);
        }
        return this;
    }

    public TextView getBodyTextView() {
        return (TextView) this.findViewById(R.id.label_body);

    }

    public UAFDialog setDialogTextValue(final int textId) {
        String text = this.getStringResource(textId);
        return this.setDialogTextValue(text);
    }

    public UAFDialog setDialogTextValue(final String text) {
        final EditText editText = this.getEditText();
        if (editText != null) {
            editText.setText(text);
        }
        return this;
    }

    public EditText getEditText() {
        return (EditText) this.findViewById(R.id.text_value);
    }

    public String getTextValue() {
        final EditText editText = this.getEditText();
        if (editText != null) {
            return editText.getText().toString();
        }
        return null;
    }

    private void setButton(final int button_id, final String text, final OnClickListener listener, final int buttonNumber) {
        Button btn = (Button) this.findViewById(button_id);
        final View.OnClickListener closeListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UAFDialog.this.dismiss();
                if (listener != null) {
                    listener.onClick(UAFDialog.this, buttonNumber);
                }
            }
        };
        btn.setText(text);
        btn.setOnClickListener(closeListener);
    }

    public UAFDialog setDialogFirstButton(final int textId, final OnClickListener listener) {
        String text = this.getStringResource(textId);
        return this.setDialogFirstButton(text, listener);
    }

    public UAFDialog setDialogFirstButton(final String text, final OnClickListener listener) {
        this.setButton(R.id.button_first, text, listener, BUTTON_FIRST);
        return this;
    }

    public UAFDialog setDialogSecondButton(final int textId, final OnClickListener listener) {
        String text = this.getStringResource(textId);
        return this.setDialogSecondButton(text, listener);
    }

    public UAFDialog setDialogSecondButton(final String text, final OnClickListener listener) {
        this.setButton(R.id.button_second, text, listener, BUTTON_SECOND);
        return this;
    }

    public UAFDialog setDialogThirdButton(final int textId, final OnClickListener listener) {
        String text = this.getStringResource(textId);
        return this.setDialogThirdButton(text, listener);
    }

    public UAFDialog setDialogThirdButton(final String text, final OnClickListener listener) {
        this.setButton(R.id.button_third, text, listener, BUTTON_THIRD);
        return this;
    }

    @Override
    public int getLayoutId() {
        return this.mLayoutId;
    }

    private String getStringResource(int textId) {
        Context context = this.getContext();
        Resources resources = context.getResources();
        return resources.getString(textId);
    }

    @Override
    public void setFonts(final Context context) {
        // TODO set fonts
//        final Typeface font = UAFFonts.getFont(context, UAFFonts.FONT_NORMAL);
//        View root = this.findViewById(R.id.root);
//        ((TextView) this.findViewById(R.id.label_title)).setTypeface(font);
//        ((TextView) this.findViewById(R.id.label_body)).setTypeface(font);
//
//        this.setButtonFonts(root, font, R.id.button_first, R.id.button_second, R.id.button_third);
    }

    private void setButtonFonts(View parent, Typeface font, int... view_ids) {
        for (int view_id : view_ids) {
            View view = parent.findViewById(view_id);
            if (view != null && view instanceof Button) {
                ((Button) view).setTypeface(font);
            }
        }
    }

}