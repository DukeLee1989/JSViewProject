package com.stickers.jsviewsdk.util;


import android.content.Context;
import android.os.Build;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by LiYong on 2017/9/11.
 * Email:lee131483@gmail.com
 * Version:
 */

public class ViewUtil {

    private static final AtomicInteger sNextGeneratedId = new AtomicInteger(1);

    private ViewUtil() {
    }

    public static ViewGroup.LayoutParams getLayoutParams(View view) {
        if (view != null && view.getLayoutParams() != null) {
            return view.getLayoutParams();
        }
        return new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    public static RelativeLayout.LayoutParams getRLayoutParams(View view) {
        if (view != null && view.getLayoutParams() != null) {
            ViewGroup.LayoutParams params = view.getLayoutParams();
            if (params instanceof RelativeLayout.LayoutParams) {
                return (RelativeLayout.LayoutParams) params;
            } else {
                RelativeLayout.LayoutParams rlParam = new RelativeLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
                rlParam.width= params.width;
                rlParam.height=params.height;
                return rlParam;
            }
        }
        return new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
    }


    public static void setId(View view) {
        if (view != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                try {//samsung SM-N9009(4.3) crash here, so protected
                    view.setId(View.generateViewId());
                } catch (Exception e) {
                    view.setId(generateViewId());
                }
            } else {
                view.setId(generateViewId());
            }
        }
    }

    private static int generateViewId() {
        for (; ; ) {
            final int result = sNextGeneratedId.get();
            // aapt-generated IDs have the high byte nonzero; clamp to the range under that.
            int newValue = result + 1;
            if (newValue > 0x00FFFFFF) newValue = 1; // Roll over to 1, not 0.
            if (sNextGeneratedId.compareAndSet(result, newValue)) {
                return result;
            }
        }
    }

    public static int px2dp(Context context, float pixelValue) {
        float density = context.getResources().getDisplayMetrics().density;
        return (int) (pixelValue / density + 0.5f);
    }

    public static int dp2px(Context context, float dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                context.getResources().getDisplayMetrics());
    }

    public static int sp2px(Context context, float sp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp,
                context.getResources().getDisplayMetrics());
    }
}
