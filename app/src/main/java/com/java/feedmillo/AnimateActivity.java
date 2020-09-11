package com.java.feedmillo;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

import static android.view.View.INVISIBLE;

public class AnimateActivity extends AppCompatActivity {

    ImageView mealImage;
    ImageView milo;
    ImageView tub;
    ImageView eat_milo;
    ImageView burp;
    AlphaAnimation fadeout;
    AlphaAnimation fadeout2;
    AlphaAnimation fadein;
    Animation fadeinburp;
    Button feed_again;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animate);

        MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.sound2);
        mediaPlayer.start();

         feed_again =findViewById(R.id.feed_again);
         mealImage = findViewById(R.id.sharemeal);
         milo = findViewById(R.id.milo);
         tub = findViewById(R.id.imageView3);
         eat_milo = findViewById(R.id.eatmilo);
         burp = findViewById(R.id.burp);
         burp.setVisibility(View.GONE);
         feed_again.setVisibility(View.GONE);

        Intent intent = getIntent();
        final Bitmap bmp = (Bitmap)intent.getParcelableExtra("imageBmp");
        mealImage.setImageBitmap(bmp);
;
        Animation animScale2 = AnimationUtils.loadAnimation(this , R.anim.meal_into_tub);
        fadeinburp = AnimationUtils.loadAnimation(this , R.anim.fade_in);
        mealImage.startAnimation(animScale2);



        fadeout = new AlphaAnimation(1.f,0.f);
        fadeout.setDuration(1000);
        fadeout.setFillAfter(true);
        milo.startAnimation(fadeout);
        tub.startAnimation(fadeout);


        fadein = new AlphaAnimation(0.f,1.f);
        fadein.setDuration(2000);
        eat_milo.startAnimation(fadein);
        fadein.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                tub.startAnimation(fadeout);
                eat_milo.startAnimation(fadeout);
                milo.startAnimation(fadeinburp);
                mealImage.startAnimation(fadeout);
                burp.setVisibility(View.VISIBLE);
                feed_again.setVisibility(View.VISIBLE);
            }
            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        feed_again.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AnimateActivity.this , MainActivity.class);
                AnimateActivity.this.startActivity(intent);
                finish();
            }
        });
    }
}