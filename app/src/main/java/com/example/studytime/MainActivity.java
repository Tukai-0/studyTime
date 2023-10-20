package com.example.studytime;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import com.airbnb.lottie.LottieAnimationView;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SignInButton signInButton = findViewById(R.id.signin_button);
        GoogleSignInOptions gOptions;
        GoogleSignInClient gClient;

        LottieAnimationView animationView = findViewById(R.id.animation_view);
        LottieAnimationView welcomeView = findViewById(R.id.welcome_view);
        LottieAnimationView animationViewcountdown = findViewById(R.id.animation_view_countdown);
        animationView.setAnimation("pocanimation2.json");
        animationView.playAnimation();
        animationViewcountdown.setAnimation("countdownanimation.json");
        animationViewcountdown.playAnimation();
        welcomeView.setAnimation("welcomeanimation.json");
        welcomeView.setAlpha(0f);
        signInButton.setAlpha(0f);
        ObjectAnimator fadeOutCountdown = ObjectAnimator.ofFloat(animationViewcountdown, "alpha", 1f, 0f);
        ObjectAnimator fadeOut = ObjectAnimator.ofFloat(animationView, "alpha", 1f, 0f);
        ObjectAnimator fadeIn = ObjectAnimator.ofFloat(welcomeView, "alpha", 0f, 1f);
        ObjectAnimator fadeInbutton = ObjectAnimator.ofFloat(signInButton, "alpha", 0f, 1f);
        animationViewcountdown.addAnimatorListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }
            @Override
            public void onAnimationEnd(Animator animation) {
                //Fade animation

                fadeOut.setDuration(400);
                fadeOut.start();

                fadeOutCountdown.setDuration(1000);
                fadeOutCountdown.addUpdateListener(animation1 -> {
                    if (animation1.getAnimatedFraction() == 1) {
                        fadeIn.start();
                    }
                });
                fadeOutCountdown.start();

            }
            @Override
            public void onAnimationCancel(Animator animation) {
            }
            @Override
            public void onAnimationRepeat(Animator animation) {
            }
        });
        fadeOutCountdown.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                fadeIn.start();
                fadeInbutton.start();
            }
        });

        gOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        gClient = GoogleSignIn.getClient(this, gOptions);

        GoogleSignInAccount gAccount = GoogleSignIn.getLastSignedInAccount(this);
        if (gAccount != null){
            finish();
            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
        }
        ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
                        try {
                            task.getResult(ApiException.class);
                            finish();
                            Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                            startActivity(intent);
                        } catch (ApiException e) {
                            Toast.makeText(MainActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        signInButton.setOnClickListener(view -> {
            Intent intent = gClient.getSignInIntent();
            activityResultLauncher.launch(intent);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        });

    }
}