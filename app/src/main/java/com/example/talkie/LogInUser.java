package com.example.talkie;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.rengwuxian.materialedittext.MaterialEditText;

public class LogInUser extends AppCompatActivity {

    private MaterialEditText Email;
    private MaterialEditText Password;
    private Button Login;

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_user);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Login");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Email = findViewById(R.id.Email1);
        Password = findViewById(R.id.password1);
        Login = findViewById(R.id.RegisterBtn1);

        auth = FirebaseAuth.getInstance();

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = Email.getText().toString();
                String password = Password.getText().toString();
                if(TextUtils.isEmpty(email) || TextUtils.isEmpty(password)){
                    Toast.makeText(LogInUser.this, "Empty Creditiantials", Toast.LENGTH_SHORT).show();
                }else{
                    loginUser(email,password); }
            }
        });

    }

    private void loginUser(String email, String password) {
        auth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(LogInUser.this, "Login Successful", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(LogInUser.this, StartActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
                    finish();
                }
                else{
                    Toast.makeText(LogInUser.this, "OOPs! There is some Authentication Problem.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
