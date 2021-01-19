package com.example.childmonitoring;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.childmonitoring.validator.Validation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private EditText email, pass;
    private FirebaseAuth firebaseAuthenticator;
    private ProgressBar loader;
    private Button buttonLogin;
    private Validation validation = new Validation();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        firebaseAuthenticator = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_main);
        findViews();
        clickListener();
    }

    private void delay() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                clearFields();
            }
        }, 800);
    }

    private void clickListener() {
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLoader();
                if(email.getText().toString().isEmpty() && pass.getText().toString().isEmpty()){
                    hideLoader();
                    email.setError("Field is Required");
                    pass.setError("Field is Required");
                } else if(pass.getText().toString().isEmpty()){
                    hideLoader();
                    pass.setError("Field is Required");
                }  else if (email.getText().toString().isEmpty()) {
                    hideLoader();
                    email.setError("Field is Required");
                }  else if(!validation.validateEmail(email.getText().toString())){
                    hideLoader();
                    email.setError("Invalid Email Address");

                } else {
                    firebaseAuthenticator.signInWithEmailAndPassword(email.getText().toString(), pass.getText().toString())
                            .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        Log.d("", "signInWithEmail:success");
                                        FirebaseUser user = firebaseAuthenticator.getCurrentUser();
                                        //Toast.makeText(MainActivity.this,  user.getEmail(), Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(MainActivity.this, Dashboard.class);
                                        assert user != null;
                                        intent.putExtra("Email", user.getEmail());

                                        startActivity(intent);
                                        delay();
                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Log.w("", "signInWithEmail:failure", task.getException());
                                        Toast.makeText(MainActivity.this, "Authentication failed.",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                    hideLoader();
                                }
                            });
                }
            }
        });
    }

    private void showLoader() {
        //making loader visible
        loader.setVisibility(View.VISIBLE);

        //disabling users interaction with the screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    private void clearFields() {
        email.setText("");
        pass.setText("");
    }

    private void hideLoader() {
        //making loader visible
        loader.setVisibility(View.INVISIBLE);

        //enabling users interaction with the screen
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    private void findViews() {
        buttonLogin = findViewById(R.id.button_login);
        email = findViewById(R.id.edit_text_email);
        pass = findViewById(R.id.edit_text_password);
        loader = findViewById(R.id.loader);
    }
}