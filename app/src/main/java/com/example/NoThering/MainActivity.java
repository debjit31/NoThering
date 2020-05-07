package com.example.NoThering;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    // Categories Activity
    private BottomNavigationView bottomNavigationView;
    private DatabaseReference mdb;

    private String name, email, dob, phone, state, city, street, aadhar, uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        name = getIntent().getStringExtra("Name");
        email = getIntent().getStringExtra("Email");
        dob = getIntent().getStringExtra("DOB");
        phone = getIntent().getStringExtra("Phone");
        state = getIntent().getStringExtra("State");
        city = getIntent().getStringExtra("City");
        street = getIntent().getStringExtra("Street");
        aadhar = getIntent().getStringExtra("Aadhar");
        uid = getIntent().getStringExtra("UID");

        mdb = FirebaseDatabase.getInstance().getReference().child("users");

        if (name != null && email != null && dob != null &&
                phone != null && state != null && city != null && street != null && aadhar != null && uid != null)
        {
            addtoDatabase();
        }

        bottomNavigationView = findViewById(R.id.btmNavView);
        bottomNavigationView.setSelectedItemId(R.id.itemCategory);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.itemCategory:
                        return true;
                    case R.id.itemMyAccount:
                        startActivity(new Intent(getApplicationContext(), AccountActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.itemMyCart:
                        startActivity(new Intent(getApplicationContext(), CartActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.itemNotifications:
                        startActivity(new Intent(getApplicationContext(), NotificationsActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                }
                return false;
            }
        });
    }

    public void addtoDatabase() {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("name", name);
        map.put("email", email);
        map.put("dob", dob);
        map.put("phone", phone);
        map.put("state", state);
        map.put("city", city);
        map.put("street", street);
        map.put("aadhar", aadhar);
        mdb.child(uid).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(MainActivity.this, "Successfully Registered", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(MainActivity.this, "Error in Registration Process", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}
