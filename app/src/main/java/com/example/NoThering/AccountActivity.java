package com.example.NoThering;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class AccountActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private Button logout, deleteAccount, accountDetails;
    private EditText loggedEmail;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        bottomNavigationView = findViewById(R.id.btmNavView);
        loggedEmail = findViewById(R.id.loggedEmail);
        mAuth = FirebaseAuth.getInstance();
        bottomNavigationView.setSelectedItemId(R.id.itemMyAccount);
        logout = findViewById(R.id.logoutBtn);
        String currentUserEmail = mAuth.getCurrentUser().getEmail();
        loggedEmail.setText(currentUserEmail);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logoutUser();

            }
        });

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.itemCategory:
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.itemNotifications:
                        startActivity(new Intent(getApplicationContext(), NotificationsActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.itemMyAccount:
                        return true;
                    case R.id.itemMyCart:
                        startActivity(new Intent(getApplicationContext(), CartActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });
    }
    public void logoutUser(){

        mAuth.signOut();
        startActivity(new Intent(AccountActivity.this, Welcome.class));
        Toast.makeText(this, "Signout Successfull", Toast.LENGTH_SHORT).show();

    }
}
