package com.suncoastsoftware.estimateepro;

import android.app.ProgressDialog;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import java.util.ArrayList;
import java.util.List;

public class Load_Estimates_Fragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String TAG        = "Load_Customers : ";

    private List<String> companyList;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    FirebaseUser user;

    private FirebaseDatabase database;
    private DatabaseReference custRef;
    private DatabaseReference estimateRef;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private int custID;

    private OnFragmentInteractionListener mListener;

    Spinner companySpinner;
    Button btn_search;
    EditText et_company;

    public Load_Estimates_Fragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static Load_Estimates_Fragment newInstance(String param1, String param2) {
        Load_Estimates_Fragment fragment = new Load_Estimates_Fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        custRef = database.getReference();
        user = mAuth.getCurrentUser();
        companyList = new ArrayList<>();


        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                if (user != null) {
                    Log.d(TAG + user, " Logged In with UID : " + user.getUid());
                } else {
                    Log.d(TAG, "Firebase User: Not Logged In");
                }
            }
        };

        new LoadCustomers().execute();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_load_estimates, container, false);
        companySpinner = (Spinner) v.findViewById(R.id.load_estimates_spinner_company);
        et_company     = (EditText) v.findViewById(R.id.load_estimates_et_search_company);
        btn_search     = (Button) v.findViewById(R.id.load_estimate_btn_search);

        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               for (int i = 0; i < companySpinner.getCount(); i++) {
                   if (companySpinner.getItemAtPosition(i).toString().equalsIgnoreCase(et_company.getText().toString())) {
                       companySpinner.setSelection(i);
                   }
               }
            }
        });

        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

   /* @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }*/

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    class LoadCustomers extends AsyncTask<Void, Integer, Void> {

        ProgressDialog progress = new ProgressDialog(getContext());
        public LoadCustomers() {
        }

        @Override
        protected Void doInBackground(Void... params) {

            custRef.child("users").child(user.getUid()).child("customers").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                    for (DataSnapshot child : children) {
                        //find the companyName node and add it to the cust_list.
                       // Iterable<DataSnapshot> custChild = child.getChildren();
                       // for (DataSnapshot data : custChild) {
                            Customer cust = child.getValue(Customer.class);
                            String company = cust.companyName;
                            companyList.add(company);
                        //}
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress.setTitle("Loading Companies!!");
            progress.setIndeterminate(true);
            progress.setProgress(0);
            progress.show();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            ArrayAdapter<String> spinAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, companyList);
            spinAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            companySpinner.setAdapter(spinAdapter);
            progress.dismiss();
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            progress.setProgress(values[0]);
        }
    }

    class LoadEstimates extends AsyncTask<String, Integer, Void> {

        ProgressDialog pDialog = new ProgressDialog(getContext());

        @Override
        protected Void doInBackground(String... params) {

            estimateRef.child("users").child(user.getUid()).child("customers").child("estimates").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog.setTitle("Loading Estimates...");
            pDialog.setIndeterminate(true);
            pDialog.show();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            pDialog.dismiss();
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            pDialog.setProgress(values[0]);
        }
    }
}
