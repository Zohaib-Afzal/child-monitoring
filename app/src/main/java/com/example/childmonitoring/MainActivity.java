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

import com.example.childmonitoring.adapters.AllRecyclerViewAdapter;
import com.example.childmonitoring.model.Child;
import com.example.childmonitoring.validator.Validation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    private EditText email, pass;
    private FirebaseAuth firebaseAuthenticator;
    FirebaseFirestore firebaseFirestore;
    private ProgressBar loader;
    private Button buttonLogin;
    private Validation validation = new Validation();
    HashMap<String, Object> data;
    String adminEmail;
    boolean isAdmin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firebaseAuthenticator = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
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
                                        //checking if current user is admin or not
                                        isAdmin = adminEmail.compareTo(email.getText().toString()) == 0;

                                        //Toast.makeText(MainActivity.this,  user.getEmail(), Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(MainActivity.this, Dashboard.class);
                                        intent.putExtra("Email", user.getEmail());
                                        intent.putExtra("isAdmin",isAdmin);


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

    private void getData() {
        firebaseFirestore.collection("Admin")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                data = (HashMap<String, Object>) document.getData();
                                adminEmail = data.get("Email").toString();
                                Log.d("TAG", document.getId() + " => " + document.getData());
                            }
                        } else {
                            Log.d("TAG", "Error getting documents: ", task.getException());
                        }
                    }
                });

    }

    @Override
    protected void onResume() {
        super.onResume();
        getData();

    }
}




