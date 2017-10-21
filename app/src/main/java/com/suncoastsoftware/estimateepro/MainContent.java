package com.suncoastsoftware.estimateepro;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

public class MainContent extends AppCompatActivity {

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
                case R.id.navigation_view_estimates:
                    Load_Estimates_Fragment newViewEstimate = new Load_Estimates_Fragment();
                    android.support.v4.app.FragmentTransaction viewEstimateTransaction = getSupportFragmentManager().beginTransaction();
                    viewEstimateTransaction.replace(R.id.content, newViewEstimate);
                    viewEstimateTransaction.commit();
                    break;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_content);

       // mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

}
