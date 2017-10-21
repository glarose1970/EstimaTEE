package com.suncoastsoftware.estimateepro;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.suncoastsoftware.estimateepro.controller.DBCustomerHelper;
import com.suncoastsoftware.estimateepro.model.Customer;

import java.util.ArrayList;
import java.util.Random;

public class New_Customer_Fragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    EditText et_custID;
    EditText et_company;
    EditText et_contactName;
    EditText et_phone;

    // reference the Firebase Database
    FirebaseDatabase database;
    DatabaseReference ref;
    private FirebaseAuth mAuth;

    DBCustomerHelper dbCustHelper;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private Context context;
    public New_Customer_Fragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static New_Customer_Fragment newInstance(String param1, String param2) {
        New_Customer_Fragment fragment = new New_Customer_Fragment();
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

        context = getActivity();
        mAuth = FirebaseAuth.getInstance();
        dbCustHelper = new DBCustomerHelper(context);
        //dbCustHelper.Delete();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_new_customer, container, false);
        et_custID = (EditText) view.findViewById(R.id.fragment_et_new_cust_custID);
        et_company = (EditText) view.findViewById(R.id.fragment_et_new_cust_company);
        et_contactName = (EditText) view.findViewById(R.id.fragment_et_new_cust_contact_name);
        et_phone = (EditText) view.findViewById(R.id.fragment_et_new_cust_phone);

        Button btn_generate = (Button) view.findViewById(R.id.fragment_new_cust_btn_generate);
        Button btn_save = (Button) view.findViewById(R.id.fragment_new_cust_btn_save);
        Button btn_cancel = (Button) view.findViewById(R.id.fragment_new_cust_btn_cancel);

        btn_generate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {et_custID.setText(Generate_CustID());
            }
        });

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Validate_Input()) {
                    Customer cust = new Customer(et_company.getText().toString(), et_custID.getText().toString(), et_contactName.getText().toString(), et_phone.getText().toString());
                    addCustomer(cust.getCustomerID(), cust.getCompanyName(), cust.getContactName(), cust.getPhone());
                }else {
                    Toast.makeText(getActivity(), "Customer Save Error!!", Toast.LENGTH_LONG).show();
                }

            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getData();
            }
        });

        return view;
    }
    private void addCustomer(String id, String customerName, String contact, String phone) {
        boolean insertData = dbCustHelper.addData(id, customerName,contact, phone);

        try {
            if (insertData) {
                Toast.makeText(getActivity(), "Customer Saved!!!!", Toast.LENGTH_LONG).show();

            }else {
                Toast.makeText(getActivity(), "Customer Not Saved!!!!", Toast.LENGTH_LONG).show();

            }
        }catch (Exception e) {
            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();

        }

    }

    private void getData() {
        Cursor data = dbCustHelper.getData();
        ArrayList<String> dataList = new ArrayList<>();
        while (data.moveToNext()) {
            Customer cust = new Customer(data.getString(2), data.getString(1), data.getString(3), data.getString(4));
            String test = "";
        }
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

  /*  @Override
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
        LinearLayout layout = (LinearLayout) getView().findViewById(R.id.new_cust_layout_input);
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
