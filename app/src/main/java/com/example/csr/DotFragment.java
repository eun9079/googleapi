package com.example.csr;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;

import android.os.Vibrator;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import static android.os.Build.VERSION.SDK_INT;

public class DotFragment extends LinearLayout {
    private View view;

    private static int[] arr = {
            R.id.dot_circle_1,
            R.id.dot_circle_2,
            R.id.dot_circle_3,
            R.id.dot_circle_4,
            R.id.dot_circle_5,
            R.id.dot_circle_6
    }; // n번째 값과 각 점자에 대한 인덱스를 매칭해줌

    private TextView dotText;

    private boolean[] filled = new boolean[] { // 해당 점이 채워졌는가 true면 채워짐
            false, false, false, false, false, false
    };

    public DotFragment(Context context) {
        super(context);
        init(context);
    }

    public DotFragment(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    // 해당 인덱스에 대해 채워진 점자, 빈 점자 여부를 설정함
    public void setFill(int index, boolean fill) {
        View circle = view.findViewById(arr[index]);
        if(fill) {
            circle.setBackground(getDrawable(getResources(), R.drawable.circle_full, null));
        } else {
            circle.setBackground(getDrawable(getResources(), R.drawable.circle_empty, null));
        }
        filled[index] = fill;
    }

    public void setText(String text) {
        dotText.setText(text);
    }

    public DotFragment(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init(context);
    }

    private void init(Context context) { // Fragment를 위한 기본적인 레이아웃 inflate, 초기화
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.fragment_dot, this);
        if(view == null) {
            Log.d("handprinting", "view is null");
        }

        dotText = view.findViewById(R.id.dot_text);

        final Vibrator vibrator = (Vibrator)context.getSystemService(Context.VIBRATOR_SERVICE);

        for(int i = 0; i < 6; i++) {
            final int index = i;
            view.findViewById(arr[i]).setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(filled[index]) { // 만약 점자가 채워져있는 상태에서 터치시 1000ms간 진동
                        vibrator.vibrate(1000);
                    }
                }
            });
        }
    }

    @Nullable
    @SuppressWarnings("deprecation")
    public static Drawable getDrawable(@NonNull Resources res, @DrawableRes int id,
                                       @Nullable Resources.Theme theme) throws Resources.NotFoundException {
        if (SDK_INT >= 21) {
            return ResourcesCompat.getDrawable(res, id, theme);
        } else {
            return res.getDrawable(id);
        }
    }
}
