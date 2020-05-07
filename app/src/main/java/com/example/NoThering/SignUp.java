package com.example.NoThering;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
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

public class SignUp extends AppCompatActivity {

    private ImageButton signupUser;
    private FirebaseAuth mAuth;
    private ProgressBar progressBar;
    private TextView tvLoginLink;
    private String name, email, password, confirmedPass, dob, phone, city, state, street, aadhar;
    private EditText mName, mEmail, mPassword, mConfirmPassword, mDOB, mPhone, mState, mCity, mStreet, mAadhar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        // Firebase Database Reference
        mAuth= FirebaseAuth.getInstance();

        // Connecting the components
        progressBar = findViewById(R.id.progressBar);
        signupUser = findViewById(R.id.signUpUser);
        tvLoginLink = findViewById(R.id.loginLink);
        mName = findViewById(R.id.userName);
        mEmail = findViewById(R.id.userEmail);
        mPassword = findViewById(R.id.userPassword);
        mConfirmPassword = findViewById(R.id.userConfirmPassword);
        mDOB = findViewById(R.id.userDOB);
        mPhone = findViewById(R.id.userPhoneNumber);
        mState = findViewById(R.id.userState);
        mCity = findViewById(R.id.userCity);
        mStreet = findViewById(R.id.userStreet);
        mAadhar = findViewById(R.id.userAadharId);

        // link to the Login Screen
        tvLoginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUp.this, SignIn.class));
            }
        });
        // Register or Sign Up Button
        signupUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                authenticateUser();
            }
        });
    }

    public void authenticateUser() {

        // Fetching user details
        progressBar.setVisibility(View.VISIBLE);
        name = mName.getText().toString().trim();
        email = mEmail.getText().toString().trim();
        confirmedPass = mConfirmPassword.getText().toString();
        password = mPassword.getText().toString().trim();
        dob = mDOB.getText().toString().trim();
        phone = mPhone.getText().toString().trim();
        state = mState.getText().toString().trim();
        city = mCity.getText().toString().trim();
        street = mStreet.getText().toString().trim();
        aadhar = mAadhar.getText().toString().trim();

        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(email) || TextUtils.isEmpty(confirmedPass) || TextUtils.isEmpty(password) ||
                TextUtils.isEmpty(dob) || TextUtils.isEmpty(phone) || TextUtils.isEmpty(state) || TextUtils.isEmpty(city) ||
                TextUtils.isEmpty(street) || TextUtils.isEmpty(aadhar))
        {
            Toast.makeText(this, "Fields are empty", Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.GONE);
        }
        if (!password.equals(confirmedPass)) {
            Toast.makeText(this, "Passwords should be same", Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.GONE);
        } else {
            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        progressBar.setVisibility(View.GONE);
                        String UID = mAuth.getUid();
                        Intent intent = new Intent(SignUp.this, MainActivity.class);
                        intent.putExtra("Name", name);
                        intent.putExtra("Email", email);
                        intent.putExtra("DOB", dob);
                        intent.putExtra("Phone", phone);
                        intent.putExtra("State", state);
                        intent.putExtra("City", city);
                        intent.putExtra("Street", street);
                        intent.putExtra("Aadhar", aadhar);
                        intent.putExtra("UID", UID);
                        startActivity(intent);
                    }
                }
            });
        }
    }

}
