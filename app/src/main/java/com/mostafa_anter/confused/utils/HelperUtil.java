package com.mostafa_anter.confused.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.view.View;

public class HelperUtil {

    /**
     * check if device is online or not
     * @param context activity or fragment
     * @return boolean value
     */
    public static boolean isOnline(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * replace dot with coma
     * @param userEmail that will use in create primary key of user
     * @return replace dot with comma
     */
    public static String encodeEmail(String userEmail) {
        return userEmail.replace(".", ",");
    }



}
