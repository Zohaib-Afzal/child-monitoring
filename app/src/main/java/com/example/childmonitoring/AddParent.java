package com.example.childmonitoring;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.childmonitoring.model.Parent;
import com.example.childmonitoring.validator.Validation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AddParent extends AppCompatActivity {
    EditText parentName;
    EditText parentEmail;
    EditText parentPassword;
    EditText parentConfirmPassword;
    EditText parentsChildName;
    Button createUser;
    Parent parent = new Parent();
    Validation validation = new Validation();
    private FirebaseAuth firebaseAuthenticator;
    FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_parent);
        firebaseAuthenticator = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        findViews();
        clickListeners();
    }

    private void findViews() {
        parentName = findViewById(R.id.edit_text_parent_name);
        parentEmail = findViewById(R.id.edit_text_parent_email);
        parentPassword = findViewById(R.id.edit_text_password);
        parentConfirmPassword = findViewById(R.id.edit_text_confirm_password);
        parentsChildName = findViewById(R.id.edit_text_child_name);
        createUser = findViewById(R.id.button_create_user);
    }

    private void clickListeners() {
        createUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getData();
            }
        });

    }

    private void getData() {
        parent.setParentName(parentName.getText().toString());
        parent.setChildName(parentsChildName.getText().toString());
        parent.setParentEmail(parentEmail.getText().toString());
        parent.setParentPassword(parentPassword.getText().toString());
        validateData();
    }

        private void validateData (){
            if (parent.getParentEmail().isEmpty() && parent.getParentPassword().isEmpty() && parent.getParentName().isEmpty() && parent.getChildName().isEmpty()) {
                parentEmail.setError("Field is Required");
                parentPassword.setError("Field is Required");
                parentName.setError("Field is Required");
                parentConfirmPassword.setError("Field is Required");
                parentEmail.setError("Field is Required");
                parentsChildName.setError("Field is Required");
            } else {
                if(parent.getParentEmail().isEmpty()){
                    parentEmail.setError("Field is Required");
                }
                if(parent.getParentName().isEmpty()){
                    parentName.setError("Field is Required");
                }
                if(parent.getChildName().isEmpty()){
                    parentsChildName.setError("Field is Required");
                }
                if(parent.getParentPassword().isEmpty()){
                    parentPassword.setError("Field is Required");
                }
                if(!parent.getParentEmail().isEmpty() && !validation.validateEmail(parent.getParentEmail())){
                    parentEmail.setError("Invalid Email Address");
                }
                if(parent.getParentPassword().compareTo(parentConfirmPassword.getText().toString()) > 0){
                    parentConfirmPassword.setError("Passwords don't Match");
                }
                if (!parent.getParentEmail().isEmpty() && !parent.getParentPassword().isEmpty() && !parent.getParentName().isEmpty() && !parent.getChildName().isEmpty() && validation.validateEmail(parent.getParentEmail()) && parent.getParentPassword().compareTo(parentConfirmPassword.getText().toString())== 0) {
                    addToAuthenticator();
                }
            }

        }
        private void addToAuthenticator(){
            firebaseAuthenticator.createUserWithEmailAndPassword(parent.getParentEmail(), parent.getParentPassword())
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d("TAG", "createUserWithEmail:success");
                                FirebaseUser user = firebaseAuthenticator.getCurrentUser();
                                addToDatabase();
                                Toast.makeText(AddParent.this, "Added to database", Toast.LENGTH_SHORT).show();

                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w("TAG", "createUserWithEmail:failure", task.getException());
                                Toast.makeText(AddParent.this, "Parent Already Exists",
                                        Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
        }
         private void addToDatabase(){
             Map<String, Object> user = new HashMap<>();
             user.put("name", parent.getParentName());
             user.put("email", parent.getParentEmail());
             user.put("child", parent.getChildName());

             firebaseFirestore.collection("User").document("1")
                     .set(user)
                     .addOnSuccessListener(new OnSuccessListener<Void>() {
                         @Override
                         public void onSuccess(Void aVoid) {
                             Log.d("TAG", "DocumentSnapshot successfully written!");
                         }
                     })
                     .addOnFailureListener(new OnFailureListener() {
                         @Override
                         public void onFailure(@NonNull Exception e) {
                             Log.w("TAG", "Error writing document", e);
                         }
                     });
        }

    }
