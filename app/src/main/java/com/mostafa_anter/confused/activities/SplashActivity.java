package com.mostafa_anter.confused.activities;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.transition.TransitionInflater;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.mostafa_anter.confused.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SplashActivity extends AppCompatActivity {
    @BindView(R.id.image_logo) ImageView imageLogo;
    @BindView(R.id.text_tile) TextView appNameTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);

        Animation fade = AnimationUtils.loadAnimation(this, R.anim.fade_in_enter);
        imageLogo.startAnimation(fade);
        fade.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                startActivity(new Intent(SplashActivity.this, SignInActivity.class)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        changeAppNameFont();
    }

    private void changeAppNameFont(){
        Typeface confusedFont = Typeface.createFromAsset(getAssets(), "fonts/splash_title.ttf");
        appNameTitle.setTypeface(confusedFont);
    }

}
