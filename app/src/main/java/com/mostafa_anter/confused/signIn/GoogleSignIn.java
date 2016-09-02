package com.mostafa_anter.confused.signIn;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.mostafa_anter.confused.R;
import com.mostafa_anter.confused.dialog.SweetDialogHelper;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by mostafa_anter on 9/2/16.
 */
public class GoogleSignIn implements
        GoogleApiClient.OnConnectionFailedListener {
    private static final int RC_SIGN_IN = 9001;
    private static final String TAG = "GoogleSignIn";

    private FragmentActivity mContext;
    private GoogleApiClient mGoogleApiClient;

    private FirebaseAuth mAuth;

    public GoogleSignIn(FragmentActivity context, GoogleApiClient mGoogleApiClient) {
        this.mContext = mContext;
        this.mGoogleApiClient = mGoogleApiClient;
        mAuth = FirebaseAuth.getInstance();
        configureGoogleSignIn();
    }

    private void configureGoogleSignIn() {
        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(mContext.getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(mContext)
                .enableAutoManage(mContext /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    public void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        mContext.startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    public void manpulateResultReturnedFromIntent(int requestCode, Intent data) {
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = result.getSignInAccount();
                firebaseAuthWithGoogle(account);
            } else {
                // Google Sign In failed, update UI appropriately
                // [START_EXCLUDE]
                SweetAlertDialog pDialog = null;
                new SweetDialogHelper(mContext, pDialog)
                        .showErrorMessage("oops!", "Google Sign In failed, try again");
                // [END_EXCLUDE]
            }
        }
    }
    public void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());
        // [START_EXCLUDE silent]
        SweetAlertDialog pDialog = null;
        final SweetDialogHelper sweetDialogHelper = new SweetDialogHelper(mContext, pDialog);
        sweetDialogHelper.showMaterialProgress("loading...");
        // [END_EXCLUDE]

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(mContext, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithCredential:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "signInWithCredential", task.getException());

                        }
                        // [START_EXCLUDE]
                        sweetDialogHelper.dismissDialog();
                        // [END_EXCLUDE]
                    }
                });
    }
}
