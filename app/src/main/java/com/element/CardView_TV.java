package com.element;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;

public class CardView_TV extends CardView {

    public String app_content;

    public CardView_TV(@NonNull Context context) {
        super(context);
    }

    public CardView_TV(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CardView_TV(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
