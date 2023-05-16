package com.example.clayshootinggame;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    ImageView iv_gun;
    ImageView iv_bullet;
    ImageView iv_clay;

    double screen_width, screen_height;

    float bullet_width, bullet_height;
    float gun_width, gun_height;
    float clay_width, clay_height;

    float bullet_center_x, bullet_center_y;
    float clay_center_x, clay_center_y;

    double gun_x,gun_y;
    double gun_center_x;

    final int NO_OF_CLAYS = 5;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar ac = getSupportActionBar();
        ac.hide();

        Button btnStart = findViewById(R.id.btnStart);
        Button btnEnd = findViewById(R.id.btnEnd);
        btnStart.setOnClickListener(this);
        btnEnd.setOnClickListener(this);

        ConstraintLayout layout = (ConstraintLayout) findViewById(R.id.layout);

        screen_height = Resources.getSystem().getDisplayMetrics().heightPixels;
        screen_width = Resources.getSystem().getDisplayMetrics().widthPixels;

        iv_gun = new ImageView(this);
        iv_clay = new ImageView(this);
        iv_bullet = new ImageView(this);

        iv_bullet.setImageResource(R.drawable.bullet);
        iv_clay.setImageResource(R.drawable.clay);
        iv_gun.setImageResource(R.drawable.gun);

        iv_gun.measure(View.MeasureSpec.UNSPECIFIED,View.MeasureSpec.UNSPECIFIED);
        gun_height= iv_gun.getMeasuredHeight();
        gun_width= iv_gun.getMeasuredWidth();

        layout.addView(iv_gun);

        iv_bullet.setImageResource(R.drawable.bullet);
        iv_bullet.measure(View.MeasureSpec.UNSPECIFIED,View.MeasureSpec.UNSPECIFIED);
        bullet_height= iv_bullet.getMeasuredHeight();
        bullet_width = iv_bullet.getMeasuredWidth();

        iv_bullet.setVisibility(View.INVISIBLE);
        layout.addView(iv_bullet);

        iv_clay.setImageResource(R.drawable.clay);
        iv_clay.setScaleX(0.8f);
        iv_clay.setScaleY(0.8f);
        iv_clay.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        clay_height=iv_clay.getHeight();
        clay_width = iv_clay.getMeasuredWidth();

        layout.addView(iv_clay);

        gun_center_x=screen_width * 0.7;
        gun_center_x = gun_center_x - gun_width*0.5;
        gun_y = screen_height - gun_height;

        iv_gun.setX((float) gun_x);
        iv_gun.setY((float) gun_y);

        iv_gun.setClickable(true);
        iv_gun.setOnClickListener(this);




    }

    @Override
    public void onClick(View view) {

        if(view.getId() ==R.id.btnStart){
            gameStart();
        } else if (view.getId() == R.id.btnEnd) {
            gameStop();
        } else if (view == iv_gun) {
            shootingStart();
        }

    }

    private void shootingStart() {

        iv_bullet.setVisibility(View.VISIBLE);
        ObjectAnimator bullet_sdx = ObjectAnimator.ofFloat(iv_bullet,"scaleX",1.0f , 0f);
        ObjectAnimator bullet_sdy = ObjectAnimator.ofFloat(iv_bullet,"scaleY",1.0f,0f);

        double bullet_x =gun_center_x-bullet_width/2;
        iv_bullet.setX((float)bullet_x);
        ObjectAnimator bullet_tx = ObjectAnimator.ofFloat(iv_bullet,"translationX",(float)bullet_x, (float)bullet_x);
        ObjectAnimator bullet_ty = ObjectAnimator.ofFloat(iv_bullet,"translationY", (float)gun_y,0f );
        bullet_ty.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(@NonNull ValueAnimator valueAnimator) {
                bullet_center_x=iv_bullet.getX() + bullet_width*0.5f;
                bullet_center_y=iv_bullet.getY() +  bullet_height*0.5f;

                clay_center_x =iv_clay.getX() +clay_width *0.5f;
                clay_center_y = iv_clay.getY() +clay_height* 0.5f;
                //////
            }
        });

    }

    private void gameStop() {
        finish();
    }

    private void gameStart() {
        ObjectAnimator clay_t_x= ObjectAnimator.ofFloat(iv_clay, "translationX", -100f, (float)screen_width);
        ObjectAnimator clay_t_y= ObjectAnimator.ofFloat(iv_clay, "translationY",0f,0f);
        ObjectAnimator clay_r = ObjectAnimator.ofFloat(iv_clay,"rotation",0f,360f * 5f);

        clay_t_x.setRepeatCount(NO_OF_CLAYS-1);
        clay_t_y.setRepeatCount(NO_OF_CLAYS-1);
        clay_r.setRepeatCount(NO_OF_CLAYS-1);
        clay_t_x.setDuration(3000);
        clay_r.setDuration(3000);
        clay_t_y.setDuration(3000);

        clay_t_x.addListener(new Animator.AnimatorListener(){

            @Override
            public void onAnimationStart(@NonNull Animator animator) {
                iv_clay.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(@NonNull Animator animator) {

                Toast.makeText(getApplicationContext(),"게임종료",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAnimationCancel(@NonNull Animator animator) {


            }

            @Override
            public void onAnimationRepeat(@NonNull Animator animator) {
                iv_clay.setVisibility(View.VISIBLE);
            }
        });

        clay_t_x.start();
        clay_r.start();
        clay_t_y.start();


    }
}