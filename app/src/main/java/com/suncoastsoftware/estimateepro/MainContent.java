package com.suncoastsoftware.estimateepro;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

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

public class MainContent extends AppCompatActivity {

    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser user = mAuth.getCurrentUser();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference dataRef = database.getReference();

    List<Customer> cust_list;
    List<String> company_list;

    List<Estimate> estimate_list;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_estimate:

                    Load_Estimates_Fragment loadEstimates = new Load_Estimates_Fragment();
                    android.support.v4.app.FragmentTransaction loadEstimatesTransaction = getSupportFragmentManager().beginTransaction();
                    loadEstimatesTransaction.replace(R.id.content, loadEstimates);
                    loadEstimatesTransaction.commit();
                    return true;
                case R.id.navigation_new_customer:
                   // mTextMessage.setText(R.string.title_dashboard);
                    New_Customer_Fragment newCust = new New_Customer_Fragment();
                    android.support.v4.app.FragmentTransaction fTransaction = getSupportFragmentManager().beginTransaction();
                    fTransaction.replace(R.id.content, newCust);
                    fTransaction.commit();
                    return true;
                case R.id.navigation_new_estimate:
                   // mTextMessage.setText(R.string.title_notifications);
                    New_Estimate_Fragment newEstimate = new New_Estimate_Fragment();
                    android.support.v4.app.FragmentTransaction estimateTransaction = getSupportFragmentManager().beginTransaction();
                    estimateTransaction.replace(R.id.content, newEstimate);
                    estimateTransaction.commit();
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_content);

        cust_list = new ArrayList<>();
        company_list = new ArrayList<>();
        estimate_list = new ArrayList<>();
        company_list.add("Choose Customer");

        LoadCustomers();
        LoadEstimates();

       // mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    private List<String> LoadCustomers() {

        dataRef.child("users").child(user.getUid()).child("customers").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                for (DataSnapshot child : children) {
                    //find the companyName node and add it to the cust_list.
                   // Iterable<DataSnapshot>  custChild = child.getChildren();
                  //  for (DataSnapshot curChild : custChild) {
                        Customer cust = child.getValue(Customer.class);
                        String company = cust.companyName;
                        cust_list.add(cust);
                        company_list.add(company);
                   // }
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return company_list;
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
