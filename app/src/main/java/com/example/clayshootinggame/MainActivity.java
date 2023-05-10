package com.example.clayshootinggame;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar ac = getSupportActionBar();
        ac.hide();

        Button btn1 = findViewById(R.id.btnStart);
        Button btn2 = findViewById(R.id.btnEnd);
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);

        ConstraintLayout layout = (ConstraintLayout) findViewById(R.id.layout);






    }

    @Override
    public void onClick(View view) {

    }
}