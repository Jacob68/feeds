package com.utaharts.app.fonts;

import android.content.Context;
import android.graphics.Typeface;

import java.util.HashMap;
import java.util.Map;

public class UAFFonts {

    // TODO insert custom fonts here

    public static final String FONT_REGULAR = "customfont.ttf";

    public static final String FONT_NORMAL = FONT_REGULAR;

    public static final Map<String, Typeface> fonts = new HashMap<String, Typeface>(5);

    public static final Typeface getFont(Context context, String name) {
        Typeface font = fonts.get(name);
        if (font == null) {
            font = Fonts.loadFontFromAssets(context, "fonts/" + name);
            fonts.put(name, font);
        }
        return font;
    }

    public static final Typeface getFontNormal(Context context) {
        return getFont(context, FONT_NORMAL);
    }
}
