package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Activity_welcome_screen extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_screen);


        ImageView logo=(ImageView)findViewById(R.id.imageView);
        TextView textLogo=(TextView)findViewById((R.id.textView));



        Animation logoanimation = AnimationUtils.loadAnimation(this,R.anim.mysplashanimation);
        logo.startAnimation(logoanimation);

        Animation textanimation = AnimationUtils.loadAnimation(this,R.anim.splash_animation_text);
        textLogo.startAnimation(textanimation);
       // ((Button)findViewById(R.id.button)).setVisibility(View.GONE);

        Button b=findViewById(R.id.button);

        b.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Activity_welcome_screen.this,Login_Activity.class);

                startActivity(intent);
                finish();
            }
        });



    }
}
