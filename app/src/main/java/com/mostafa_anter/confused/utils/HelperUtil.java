package com.mostafa_anter.confused.utils;

import android.app.Activity;
import android.os.Build;
import android.transition.Explode;

public class HelperUtil {
    public static void setupWindowAnimations(Activity context) {
        Explode explode;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            explode = new Explode();
            explode.setDuration(1000);
            context.getWindow().setEnterTransition(explode);
        }
    }
}
