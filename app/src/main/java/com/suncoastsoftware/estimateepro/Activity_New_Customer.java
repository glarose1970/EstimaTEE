package com.suncoastsoftware.estimateepro;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.suncoastsoftware.estimateepro.controller.XMLHelper;
import com.suncoastsoftware.estimateepro.model.Customer;

import java.util.Random;

public class Activity_New_Customer extends AppCompatActivity {

    XMLHelper xmlHelper = new XMLHelper();
    EditText et_custID;
    EditText et_company;
    EditText et_contactName;
    EditText et_phone;

    // reference the Firebase Database
    FirebaseDatabase database;
    DatabaseReference ref;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__new__customer);

        mAuth = FirebaseAuth.getInstance();
        et_custID = (EditText) findViewById(R.id.et_new_cust_custID);
        et_company = (EditText) findViewById(R.id.et_new_cust_company);
        et_contactName = (EditText) findViewById(R.id.et_new_cust_contact_name);
        et_phone = (EditText) findViewById(R.id.et_new_cust_phone);

        Button btn_generate = (Button) findViewById(R.id.new_cust_btn_generate);
        Button btn_save = (Button) findViewById(R.id.new_cust_btn_save);
        Button btn_cancel = (Button) findViewById(R.id.new_cust_btn_cancel);

        btn_generate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {et_custID.setText(Generate_CustID());
            }
        });

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Customer cust = new Customer(et_company.getText().toString(), et_custID.getText().toString(), et_contactName.getText().toString(), et_phone.getText().toString());
                    database = FirebaseDatabase.getInstance();
                    ref = database.getReference();
                    FirebaseUser user = mAuth.getCurrentUser();
                    ref.child("users").child(user.getUid()).child("customers").child(cust.customerID).push().setValue(cust);
                    Toast.makeText(Activity_New_Customer.this, "Customer Saved!!!!", Toast.LENGTH_LONG).show();
                }catch (Exception e) {
                    Toast.makeText(Activity_New_Customer.this, "Failed to save xml!", Toast.LENGTH_LONG).show();
                }


            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
    private String Generate_CustID() {

        String id = "10-";
        Random rand = new Random();
        int min = 100000;
        int max = 999999;
        int _id = rand.nextInt((max - min) + 1) + min;
        id = id + String.valueOf(_id);

        return id;
    }

    private Boolean Validate_Input() {
        LinearLayout layout = (LinearLayout) findViewById(R.id.new_cust_layout_input);
        for (int i = 0; i < layout.getChildCount(); i++) {
            View view = layout.getChildAt(i);
                if (view instanceof EditText) {
                    String curString = ((EditText) view).getText().toString();
                    if (TextUtils.isEmpty(curString)) {
                        return false;
                    }
                }
        }
        return true;
    }
}










