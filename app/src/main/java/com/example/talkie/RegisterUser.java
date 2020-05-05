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
import com.google.firebase.database.FirebaseDatabase;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.HashMap;

public class RegisterUser extends AppCompatActivity {

    private MaterialEditText Email;
    private MaterialEditText Password,Username;
    private Button RegisterBtn;

    private FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Register");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Email = findViewById(R.id.Email);
        Password = findViewById(R.id.password);
        RegisterBtn = (Button) findViewById(R.id.RegisterBtn);
        Username = findViewById(R.id.username);

        auth = FirebaseAuth.getInstance();

        RegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = Email.getText().toString();
                String password = Password.getText().toString();
                String userName = Username.getText().toString();

                if(TextUtils.isEmpty(email) || TextUtils.isEmpty(password) || TextUtils.isEmpty(userName)){
                    Toast.makeText(RegisterUser.this, "Empty Creditiantials", Toast.LENGTH_SHORT).show();
                }
                else if(password.length() < 6){
                    Toast.makeText(RegisterUser.this,"Password too Short",Toast.LENGTH_SHORT).show();
                }
                else{
                    registerUser(email,password,userName);
                }
            }
        });
    }

    private void registerUser(String email, String password, final String UserNam) {

        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(RegisterUser.this,new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    HashMap<String , Object> map = new HashMap<>();
                    map.put("id", auth.getCurrentUser().getUid());
                    map.put("username",UserNam);
                    map.put("imageURL","default");

                    FirebaseDatabase.getInstance().getReference().child("users").child(auth.getCurrentUser().getUid()).updateChildren(map);

                    Toast.makeText(RegisterUser.this, "Registeration successful", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(RegisterUser.this, StartActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
                    finish();
                }

                else{
                    Toast.makeText(RegisterUser.this, "OOPs! Registration met a problem", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
