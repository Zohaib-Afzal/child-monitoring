package com.example.childmonitoring;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.childmonitoring.fragments.AllFragment;
import com.example.childmonitoring.fragments.ArrivedFragment;
import com.example.childmonitoring.fragments.OnWayFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Dashboard extends AppCompatActivity {

    Button buttonOnWAy;
    Button buttonArrived;
    Button buttonAll;
    Button buttonAdd;
    String userEmail = "";
    boolean isAdmin = true;
    private FirebaseAuth firebaseAuthenticator;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        findViews();
        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            userEmail = extras.getString("Email");
            isAdmin = extras.getBoolean("isAdmin");
        }
        if(!isAdmin){
            buttonAdd.setVisibility(View.INVISIBLE);
        }
        firebaseAuthenticator = FirebaseAuth.getInstance();
        checkUser();
        displayFirstFragment();
        clickListeners();
    }
    private void checkUser(){
    }

    private void findViews() {
        buttonOnWAy = findViewById(R.id.button_on_way);
        buttonArrived = findViewById(R.id.button_arrived);
        buttonAll = findViewById(R.id.button_all);
        buttonAdd = findViewById(R.id.button_add);
    }

    private void displayFirstFragment() {
        pushFragment(new OnWayFragment(), "OnWayFragment");
    }

    private void pushFragment(Fragment fragment, String TAG) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment, TAG).commit();
    }

    private void clickListeners() {
        buttonOnWAy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pushFragment(new OnWayFragment(), "OnWayFragment");
            }
        });

        buttonArrived.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pushFragment(new ArrivedFragment(), "ArrivedFragment");
            }
        });
        buttonAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pushFragment(new AllFragment(), "AllFragment");

            }
        });
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Dashboard.this, AddParent.class);
                startActivity(intent);
            }
        });
    }
}