package com.mostafa_anter.confused.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mostafa_anter.confused.R;
import com.mostafa_anter.confused.fireBaseDataModels.User;
import com.mostafa_anter.confused.localStores.ConfusedPrefStore;
import com.mostafa_anter.confused.signIn.GoogleSignIn;
import com.mostafa_anter.confused.utils.Constants;
import com.mostafa_anter.confused.utils.HelperUtil;

public class SignInActivity extends AppCompatActivity {
    private static final String TAG = "SignInActivity";

    private GoogleApiClient mGoogleApiClient;
    private GoogleSignIn googleSignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignInActivity.this, SignUpActivity.class));
                overridePendingTransition(R.anim.push_left_enter, R.anim.push_left_exit);
            }
        });

        // google sign in section
        googleSignIn = new GoogleSignIn(SignInActivity.this, mGoogleApiClient) {
            @Override
            public void onUserAuthenticatedWithGoogle(final FirebaseUser user) {
                createUserInFireBaseHelper(user);
            }
        };


    }

    @Override
    protected void onStart() {
        super.onStart();
        googleSignIn.linkFireBaseAuthWithListener();
    }

    @Override
    protected void onStop() {
        super.onStop();
        googleSignIn.removeAuthListener();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        googleSignIn.manpulateResultReturnedFromIntent(requestCode, data);
    }

    public void loginWithEmail(View view) {
        startActivity(new Intent(SignInActivity.this, HomeActivity.class));
        overridePendingTransition(R.anim.push_up_enter, R.anim.push_up_exit);
    }

    private void createUserInFireBaseHelper(final FirebaseUser user) {

        /**
         * to handle empty email address and empty photo url
         */
        String encodedEmail;
        if ((user.getEmail()) != null &&
              !user.getEmail().trim().isEmpty()) {
            encodedEmail = HelperUtil.encodeEmail(user.getEmail());
        } else {
            encodedEmail = user.getUid();
        }

        String avatarURl;
        if (user.getPhotoUrl() == null){
            avatarURl = Constants.DEFAULT_AVATAR_URL;
        }else {
            avatarURl = user.getPhotoUrl().toString();
        }

        // save user email, name, photoUrl inside preference
        new ConfusedPrefStore(SignInActivity.this).addPreference(Constants.USER_EMAIL, encodedEmail);
        new ConfusedPrefStore(SignInActivity.this).addPreference(Constants.USER_NAME, user.getDisplayName());
        new ConfusedPrefStore(SignInActivity.this).addPreference(Constants.USER_PHOTO_URL, avatarURl);

        final DatabaseReference userLocation = FirebaseDatabase.getInstance()
                .getReference()
                .child(Constants.FIREBASE_USERS_PATH)
                .child(encodedEmail);
        /**
         * See if there is already a user (for example, if they already logged in with an associated
         * Google account.
         */
        userLocation.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                /* If there is no user, make one */
                if (!dataSnapshot.exists()) {
                    String avatarURl;
                    if (user.getPhotoUrl() == null || user.getPhotoUrl().toString().isEmpty()){
                        avatarURl = Constants.DEFAULT_AVATAR_URL;
                    }else {
                        avatarURl = user.getPhotoUrl().toString();
                    }
                    User newUser = new User(user.getDisplayName(), user.getEmail(),
                            null, null, avatarURl);
                    userLocation.setValue(newUser);
                }

                // go to home page
                startActivity(new Intent(SignInActivity.this, HomeActivity.class)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
                overridePendingTransition(R.anim.push_up_enter, R.anim.push_up_exit);
                finish();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("Error", databaseError.getMessage());
            }
        });

    }

    public void loginWithFacebook(View view) {
    }

    public void loginWithGoogle(View view) {
        googleSignIn.signIn();
    }

    public void loginWithTwitter(View view) {
    }

    public void retrievePassword(View view) {
    }
}
