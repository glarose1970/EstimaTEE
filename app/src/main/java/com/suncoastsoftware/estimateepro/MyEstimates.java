package com.suncoastsoftware.estimateepro;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.suncoastsoftware.estimateepro.model.Customer;
import com.suncoastsoftware.estimateepro.model.Estimate;

import java.util.ArrayList;
import java.util.List;

public class MyEstimates extends AppCompatActivity {

    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser user = mAuth.getCurrentUser();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference dataRef = database.getReference();

    List<Customer> cust_list;
    List<String> company_list;

    List<Estimate> estimate_list;

    Button btn_search;
    EditText et_search_query;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_estimates);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        cust_list = new ArrayList<>();
        company_list = new ArrayList<>();
        estimate_list = new ArrayList<>();
        company_list.add("Choose Customer");

        LoadCustomers();
        LoadEstimates();

        et_search_query = (EditText) findViewById(R.id.estimates_et_search);

        btn_search = (Button) findViewById(R.id.estimates_btn_search);
        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        Spinner spin_customers = (Spinner) findViewById(R.id.spin_customers);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(MyEstimates.this, android.R.layout.simple_list_item_1, company_list);
        spin_customers.setAdapter(adapter);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setImageDrawable(getResources().getDrawable(R.drawable.ic_add_person));
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Add New Customer", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                NewCustomerIntent();

            }
        });
    }

    private List<String> LoadCustomers() {

        dataRef.child("users").child(user.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                for (DataSnapshot child : children) {
                    //find the companyName node and add it to the cust_list.
                    Iterable<DataSnapshot>  custChild = child.getChildren();
                    for (DataSnapshot curChild : custChild) {
                        Iterable<DataSnapshot> thisChild = curChild.getChildren();
                        for (DataSnapshot data : thisChild) {
                            Customer cust = data.getValue(Customer.class);
                            String company = cust.companyName;
                            cust_list.add(cust);
                            company_list.add(company);
                        }

                    }
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return company_list;
    }

    private Intent NewCustomerIntent() {
        Intent newCustIntent = new Intent(this.getApplicationContext(), Activity_New_Customer.class);
        this.startActivity(newCustIntent);

        return newCustIntent;
    }

    private List<Estimate> LoadEstimates() {

        dataRef.child("users").child(user.getUid()).child("estimates").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                for (DataSnapshot child : children) {
                    //find the estimate node and add it to the estimate_list.
                    Estimate estimate = child.getValue(Estimate.class);
                    estimate_list.add(estimate);
                    String test = "";
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return estimate_list;
    }
}
