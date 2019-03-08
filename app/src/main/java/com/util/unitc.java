package com.util;

import android.content.Context;
import android.content.res.Resources;
import android.util.TypedValue;

public class unitc {

    private Resources r;

    public unitc(Context context){
        this.r = context.getResources();
    }

    public int dpToPx(float dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics());
    }

    public int spToPx(float sp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, r.getDisplayMetrics());
    }

    public int dpToSp(float dp) {
        return (int) (dpToPx(dp) / r.getDisplayMetrics().scaledDensity);
    }
}
