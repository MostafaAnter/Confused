package com.mostafa_anter.confused.utils;

import android.support.annotation.NonNull;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

public class HelperUtil {
    /**
     * Google sign out
     */
    public static void googleSignOut(GoogleApiClient mGoogleApiClient){
        // Google sign out
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(@NonNull Status status) {
                        // do some thing
                    }
                });
    }
}
