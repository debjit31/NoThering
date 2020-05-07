package com.example.NoThering;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.w3c.dom.Text;

public class SignIn extends AppCompatActivity {

    private TextView tvSignUp;
    private EditText muserEmail, muserPassword;
    private ImageButton loginUser;
    private String email, password;
    private ProgressBar progressBar;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        mAuth = FirebaseAuth.getInstance();
        tvSignUp = (TextView) findViewById(R.id.signUpLink);
        progressBar = findViewById(R.id.progressBar);
        muserEmail = findViewById(R.id.userEmail);
        muserPassword = findViewById(R.id.userPassword);
        loginUser = (ImageButton) findViewById(R.id.signInBtn);

        loginUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                userLogin();
            }
        });
        tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignIn.this, SignUp.class));
            }
        });
    }
    public void userLogin(){
         email = muserEmail.getText().toString().trim();
         password = muserPassword.getText().toString().trim();

         if(TextUtils.isEmpty(email) || TextUtils.isEmpty(password)){
             Toast.makeText(this, "Fields cannot b emepty", Toast.LENGTH_SHORT).show();
             progressBar.setVisibility(View.INVISIBLE);
         }
         else{
             mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                 @Override
                 public void onComplete(@NonNull Task<AuthResult> task) {
                     if(task.isSuccessful()){
                         progressBar.setVisibility(View.INVISIBLE);
                         Intent intent = new Intent(SignIn.this,  MainActivity.class);
                         startActivity(intent);
                         Toast.makeText(SignIn.this, "Welcome back", Toast.LENGTH_SHORT).show();
                     }else{
                         Toast.makeText(SignIn.this, "Could Not Login", Toast.LENGTH_SHORT).show();
                     }
                 }
             });
         }
    }
}
