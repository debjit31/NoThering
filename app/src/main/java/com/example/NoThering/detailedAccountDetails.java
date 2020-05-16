package com.example.NoThering;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class detailedAccountDetails extends AppCompatActivity {


    private FirebaseAuth mAuth;
    private String userID, pass;
    private DatabaseReference mdb;
    private TextView mUserName, mUserEmail, mUserPhone, mUserDOB, mUserState, mUserCity, mUserStreet, mUserAadhar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_account_details);

        mAuth = FirebaseAuth.getInstance();
        mdb = FirebaseDatabase.getInstance().getReference().child("users");

        userID = mAuth.getUid();
        mUserName = findViewById(R.id.tvUserName);
        mUserEmail = findViewById(R.id.tvUserEmail);
        mUserPhone = findViewById(R.id.tvUserPhone);
        mUserDOB = findViewById(R.id.tvUserDOB);
        mUserState = findViewById(R.id.tvUserState);
        mUserCity = findViewById(R.id.tvUserCity);
        mUserStreet = findViewById(R.id.tvUserStreet);
        mUserAadhar = findViewById(R.id.tvUserAadharId);

        mdb.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mUserName.setText(dataSnapshot.child("name").getValue().toString());
                mUserEmail.setText(dataSnapshot.child("email").getValue().toString());
                mUserPhone.setText(dataSnapshot.child("phone").getValue().toString());
                mUserDOB.setText(dataSnapshot.child("dob").getValue().toString());
                mUserState.setText(dataSnapshot.child("state").getValue().toString());
                mUserCity.setText(dataSnapshot.child("city").getValue().toString());
                mUserStreet.setText(dataSnapshot.child("street").getValue().toString());
                mUserAadhar.setText(dataSnapshot.child("aadhar").getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
