package com.example.talkie;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class StartActivity extends AppCompatActivity {
    private Button RegisterBtn;
    private Button LoginBtn;

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user != null){
            startActivity(new Intent(StartActivity.this,MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        RegisterBtn = (Button)findViewById(R.id.reg);
        LoginBtn = (Button)findViewById(R.id.login);

        RegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StartActivity.this, RegisterUser.class));
                finish();
            }
        });
        LoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StartActivity.this, LogInUser.class));
                finish();
            }
        });

    }
}

