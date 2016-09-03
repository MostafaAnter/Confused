package com.mostafa_anter.confused.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class HelperUtil {

    /**
     * check if device is online or not
     * @param context
     * @return
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
     * @param userEmail
     * @return
     */
    public static String encodeEmail(String userEmail) {
        return userEmail.replace(".", ",");
    }


}
