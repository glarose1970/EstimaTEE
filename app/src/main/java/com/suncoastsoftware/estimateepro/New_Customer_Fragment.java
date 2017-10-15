package com.suncoastsoftware.estimateepro;

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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.suncoastsoftware.estimateepro.model.Customer;

import java.util.Random;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link New_Customer_Fragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link New_Customer_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
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

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public New_Customer_Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment New_Customer_Fragment.
     */
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

        mAuth = FirebaseAuth.getInstance();

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
                try {
                    Customer cust = new Customer(et_company.getText().toString(), et_custID.getText().toString(), et_contactName.getText().toString(), et_phone.getText().toString());
                    database = FirebaseDatabase.getInstance();
                    ref = database.getReference();
                    FirebaseUser user = mAuth.getCurrentUser();
                    ref.child("users").child(user.getUid()).child("customers").child(cust.customerID).push().setValue(cust);
                    Toast.makeText(getActivity(), "Customer Saved!!!!", Toast.LENGTH_LONG).show();
                }catch (Exception e) {
                    Toast.makeText(getActivity(), "Failed to save xml!", Toast.LENGTH_LONG).show();
                }


            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return view;
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
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
