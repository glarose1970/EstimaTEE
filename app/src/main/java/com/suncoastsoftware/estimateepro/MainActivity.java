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
                    Log.i(TAG + user, " Logged In with UID : " + user.getUid());
                }else {
                    Log.i(TAG, "Firebase User: Not Logged In");
                }
            }
        };


       /* File dir = getFilesDir();
        File file = new File(dir, "customers.xml");
        file.delete();*/

        final EditText et_email = (EditText) findViewById(R.id.et_email);
        final EditText et_password = (EditText) findViewById(R.id.et_password);

        Button btn_login = (Button) findViewById(R.id.btn_login);
        Button btn_cancel = (Button) findViewById(R.id.btn_cancel);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Valid(et_email.getText().toString()) && Valid((et_password.getText().toString()))) {
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
            }
        });
    }

    private void Login(String username, String password) {
        mAuth.signInWithEmailAndPassword(username, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(!task.isSuccessful()) {
                            Toast.makeText(MainActivity.this, "Unable to Login!!!", Toast.LENGTH_SHORT).show();
                        }else {

                            Intent intent = new Intent(getApplicationContext(), MainContent.class);
                            startActivity(intent);
                        }
                    }
                });

    }

    private boolean Valid(String input) {

        if (TextUtils.isEmpty(input)) {
            return false;
        }else
            return true;

    }
}
