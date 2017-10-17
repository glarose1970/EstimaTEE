package com.suncoastsoftware.estimateepro;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    final static String TAG = "MainActivity :";

    TextView tv_create_acct, tv_forgot_password;
    LinearLayout layout_input;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

        layout_input       = (LinearLayout) findViewById(R.id.layout_input);
        tv_create_acct     = (TextView) findViewById(R.id.tv_create_account);
        tv_forgot_password = (TextView) findViewById(R.id.tv_forgot_password);

        final EditText et_email = (EditText) findViewById(R.id.et_email);
        final EditText et_password = (EditText) findViewById(R.id.et_password);

        Button btn_login = (Button) findViewById(R.id.btn_login);
        Button btn_cancel = (Button) findViewById(R.id.btn_cancel);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Valid()) {
                    Login(et_email.getText().toString(), et_password.getText().toString());
                } else {
                    Toast.makeText(getApplicationContext(), "All Fields Required!", Toast.LENGTH_LONG).show();
                }
            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                System.exit(0);
            }
        });
    }


    private void Login(final String username, final String password) {
        mAuth.signInWithEmailAndPassword(username, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(!task.isSuccessful()) {
                            Toast.makeText(MainActivity.this, "No Account matches the email provided, if you did not create an account , use the [create account] button to create an account!", Toast.LENGTH_SHORT).show();

                        }else {
                           
                            Intent intent = new Intent(getApplicationContext(), MainContent.class);
                            startActivity(intent);
                        }
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
                            Toast.makeText(MainActivity.this, "Unable to Create Account!!!", Toast.LENGTH_SHORT).show();

                        }else {
                            Login(email, password);
                        }
                    }
                });
    }

    private boolean Valid() {

       for (int i = 0; i < layout_input.getChildCount(); i++) {
            View v = layout_input.getChildAt(i);
           if (v instanceof  EditText) {
               if (TextUtils.isEmpty(((EditText) v).getText().toString())) {
                   return false;
               }
           }
        }
        return true;
    }
}
