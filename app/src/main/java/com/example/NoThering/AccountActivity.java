package com.example.NoThering;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;

public class AccountActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private TextView accountDetails, myHistory, recentSearches, help, contactUs, signOut, accountName;
    private CircleImageView accountImage;
    private DatabaseReference mdb;
    private FirebaseAuth mAuth;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        mAuth = FirebaseAuth.getInstance();
        mdb = FirebaseDatabase.getInstance().getReference().child("users");

        bottomNavigationView = findViewById(R.id.btmNavView);

        accountName = findViewById(R.id.accountName);
        accountImage = findViewById(R.id.accountImage);
        accountDetails = findViewById(R.id.tvAccountDetails);
        myHistory = findViewById(R.id.tvMyHistory);
        recentSearches = findViewById(R.id.tvRecentSearches);
        help = findViewById(R.id.tvHelp);
        contactUs = findViewById(R.id.tvContactUs);
        signOut = findViewById(R.id.tvSignOut);

        // get current user uid
        userId = mAuth.getUid();
        // move to account details page
        accountDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AccountActivity.this, detailedAccountDetails.class));
            }
        });
        // sign out
        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logoutUser();
            }
        });
        // fetch logged in username from realtime database and display
        mdb.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                accountName.setText(dataSnapshot.child("name").getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.itemCategory:
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.itemNotifications:
                        startActivity(new Intent(getApplicationContext(), NotificationsActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.itemMyAccount:
                        return true;
                    case R.id.itemMyCart:
                        startActivity(new Intent(getApplicationContext(), CartActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                }
                return false;
            }
        });
    }

    public void logoutUser() {

        mAuth.signOut();
        startActivity(new Intent(AccountActivity.this, Welcome.class));
        Toast.makeText(this, "Signout Successfull", Toast.LENGTH_SHORT).show();

    }
}
