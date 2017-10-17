package com.suncoastsoftware.estimateepro;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Register_Activity extends AppCompatActivity {

    final String TAG = "REGISTER ACTIVITY";
    Button btn_register, btn_cancel;
    EditText et_email, et_password;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    Log.d(TAG + user, " Logged In with UID : " + user.getUid());
                } else {
                    Log.d(TAG, "Firebase User: Not Logged In");
                }
            }
        };

        et_email    = (EditText) findViewById(R.id.et_register_email);
        et_password = (EditText) findViewById(R.id.et_register_password);

        btn_register = (Button) findViewById(R.id.btn_register_register);
        btn_cancel   = (Button) findViewById(R.id.btn_register_cancel);

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.exit(0);
            }
        });
    }


    //creates a new user in firebase with email and password
    private void CreateAccount(final String email, final String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(!task.isSuccessful()) {
                            Toast.makeText(Register_Activity.this, "Unable to Create Account!!!" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                        }else {
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                        }
                    }
                });
    }

    //validate the user inputs
    private boolean isValid(String input) {
        return false;
    }
}
