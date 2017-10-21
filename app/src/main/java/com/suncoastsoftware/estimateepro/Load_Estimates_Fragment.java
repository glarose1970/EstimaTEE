package com.suncoastsoftware.estimateepro;

import android.app.ProgressDialog;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.suncoastsoftware.estimateepro.adapter.EstimatesRecyclerAdapter;
import com.suncoastsoftware.estimateepro.controller.DBCustomerHelper;
import com.suncoastsoftware.estimateepro.controller.DBEstimateHelper;
import com.suncoastsoftware.estimateepro.model.Customer;
import com.suncoastsoftware.estimateepro.model.Estimate;

import java.util.ArrayList;
import java.util.List;

public class Load_Estimates_Fragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String TAG        = "Load_Customers : ";

    DBCustomerHelper dbCustHelper;

    private List<String> companyList;
    private List<Estimate> estimate_list;

    private RecyclerView recView;
    private EstimatesRecyclerAdapter recViewAdapter;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private String custID;

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
        dbCustHelper = new DBCustomerHelper(getActivity());
        companyList = new ArrayList<>();
        estimate_list = new ArrayList<>();

        new LoadCustomers().execute();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_load_estimates, container, false);
        recView = (RecyclerView) v.findViewById(R.id.load_estimates_recView);
        companySpinner = (Spinner) v.findViewById(R.id.load_estimates_spinner_company);
        et_company     = (EditText) v.findViewById(R.id.load_estimates_et_search_company);
        btn_search     = (Button) v.findViewById(R.id.load_estimate_btn_search);

        companySpinner.setSelection(-1);
        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                custID = companySpinner.getSelectedItem().toString();
                new LoadEstimates().execute();

               for (int i = 0; i < companySpinner.getCount(); i++) {
                   if (companySpinner.getItemAtPosition(i).toString().equalsIgnoreCase(et_company.getText().toString())) {
                       companySpinner.setSelection(i);

                   }
               }
            }
        });
        companySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                estimate_list.clear();
                custID = companySpinner.getSelectedItem().toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

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
            companyList = new ArrayList<>();
        }

        @Override
        protected Void doInBackground(Void... params) {


            Cursor data =  dbCustHelper.getData();
            while(data.moveToNext()) {
                Customer cust = new Customer(data.getString(2), data.getString(1), data.getString(3), data.getString(4));
                String company = cust.companyName;
                companyList.add(company);
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        /*    progress.setTitle("Loading Companies!!");
            progress.setIndeterminate(true);
            progress.setProgress(0);
            progress.show();*/
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            ArrayAdapter<String> spinAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, companyList);
            spinAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            companySpinner.setAdapter(spinAdapter);
           // progress.dismiss();
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
           // progress.setProgress(values[0]);
        }
    }

    class LoadEstimates extends AsyncTask<String, Integer, Void> {

        ProgressDialog pDialog = new ProgressDialog(getActivity());
        DBEstimateHelper dbEstimateHelper = new DBEstimateHelper(getActivity());

        @Override
        protected Void doInBackground(String... params) {
            Cursor data = dbEstimateHelper.Search(custID);
            while (data.moveToNext()) {
                Estimate estimate = new Estimate(data.getString(3), data.getString(2), data.getString(4), data.getDouble(8));
                estimate_list.add(estimate);
            }

            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog.setTitle("Loading Estimates...");
            pDialog.setMessage("Loading...");
            pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            pDialog.setIndeterminate(true);
            pDialog.setProgress(0);
            pDialog.show();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            recViewAdapter = new EstimatesRecyclerAdapter(getContext(), estimate_list);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
            recView.setLayoutManager(mLayoutManager);
            recView.setItemAnimator(new DefaultItemAnimator());
            recView.setAdapter(recViewAdapter);
            pDialog.dismiss();
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            pDialog.setProgress(values[0]);
        }
    }
}
