package com.suncoastsoftware.estimateepro;

import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.suncoastsoftware.estimateepro.controller.DBCustomerHelper;
import com.suncoastsoftware.estimateepro.controller.DBEstimateHelper;
import com.suncoastsoftware.estimateepro.model.Customer;
import com.suncoastsoftware.estimateepro.model.Estimate;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class New_Estimate_Fragment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    // reference the Firebase Database
    DBEstimateHelper dbestimateHelper;
    DBCustomerHelper dbCustHelper;
    private FirebaseAuth mAuth;
    private FirebaseUser user;

    private String mParam1;
    private String mParam2;

    EditText et_estimateID, et_custID, et_title, et_desc, et_notes;
    EditText et_due_date, et_shirt_sizes, et_quantity, et_shop_base_charge;
    EditText et_per_piece_price, et_screen_charge, et_numColors, et_total_price;
    Spinner companySpinner;
    CheckBox cb_both_sides;

    Button  btn_calculate, btn_save, btn_cancel;
    String custID;

    List<String> custList;

    private OnFragmentInteractionListener mListener;

    public New_Estimate_Fragment() {
        // Required empty public constructor
    }


    public static New_Estimate_Fragment newInstance(String param1, String param2) {
        New_Estimate_Fragment fragment = new New_Estimate_Fragment();
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
      //  dbHelper = new DBEstimateHelper(getActivity());
        dbCustHelper = new DBCustomerHelper(getActivity());
        dbestimateHelper = new DBEstimateHelper(getActivity());
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        custList = new ArrayList<>();
        new LoadCustomers().execute();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_new__estimate, container, false);
        et_estimateID       = (EditText) view.findViewById(R.id.et_new_estimate_estimateID);
        companySpinner      = (Spinner)  view.findViewById(R.id.new_estimate_customer_spinner);
        et_title            = (EditText) view.findViewById(R.id.et_new_estimate_title);
        et_desc             = (EditText) view.findViewById(R.id.et_new_estimate_description);
        et_notes            = (EditText) view.findViewById(R.id.et_new_estimate_notes);
        et_due_date         = (EditText) view.findViewById(R.id.et_new_estimate_due_date);
        et_shirt_sizes      = (EditText) view.findViewById(R.id.et_new_estimate_shirt_sizes);
        et_quantity         = (EditText) view.findViewById(R.id.et_new_estimate_quantity);
        et_shop_base_charge = (EditText) view.findViewById(R.id.et_new_estimate_shop_base_charge);
        et_per_piece_price  = (EditText) view.findViewById(R.id.et_new_estimate_per_piece_price);
        et_screen_charge    = (EditText) view.findViewById(R.id.et_new_estimate_screen_charge);
        et_numColors        = (EditText) view.findViewById(R.id.et_new_estimate_num_colors);
        et_total_price      = (EditText) view.findViewById(R.id.et_new_estimate_total_price);
        cb_both_sides       = (CheckBox) view.findViewById(R.id.cb_new_estimate_both_sides);

        btn_calculate       = (Button) view.findViewById(R.id.btn_new_estimate_calculate);
        btn_save            = (Button) view.findViewById(R.id.new_estimate_btn_save);
        btn_cancel          = (Button) view.findViewById(R.id.new_estimate_btn_cancel);

        btn_save.setOnClickListener(this);
        btn_calculate.setOnClickListener(this);
        btn_cancel.setOnClickListener(this);

        et_estimateID.setText(String.valueOf(Generate_CustID()));

        companySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                custID = companySpinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        return view;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.new_estimate_btn_save:
                if (Validate_Input()) {

                    Estimate estimate = new Estimate(et_estimateID.getText().toString(), custID, et_title.getText().toString(), et_desc.getText().toString(),
                            et_notes.getText().toString(), Long.valueOf(et_quantity.getText().toString()), Double.parseDouble(et_total_price.getText().toString()), Double.parseDouble(et_per_piece_price.getText().toString()),
                            Double.parseDouble(et_shop_base_charge.getText().toString()), Double.parseDouble(et_screen_charge.getText().toString()), Integer.valueOf(et_numColors.getText().toString()),
                                    et_due_date.getText().toString(), et_shirt_sizes.getText().toString(), cb_both_sides.isChecked());

                    dbestimateHelper.addData(estimate.getEstimateID(), custID, estimate.getTitle(), estimate.getDescription(),
                            estimate.getNotes(), estimate.getQuantity(), estimate.getTotal_price(), estimate.getPer_piece_price(),
                            estimate.getShop_base_charge(), estimate.getScreen_charge(), estimate.getNum_colors(), estimate.getDue_date(),
                            estimate.getShirt_size(), estimate.isBoth_sides());
                    ClearInput();
                }

                break;
            case R.id.new_estimate_btn_cancel:

                break;
            case R.id.btn_new_estimate_calculate:

                break;
        }
    }

    private String Generate_CustID() {

        String id = "11-";
        Random rand = new Random();
        int min = 100000;
        int max = 999999;
        int _id = rand.nextInt((max - min) + 1) + min;
        id = id + String.valueOf(_id);

        return id;
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

/*    @Override
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

    private Boolean Validate_Input() {

        ViewGroup layout = (ViewGroup) getView().findViewById(R.id.new_estimate_layout_input);

        for (int i = 0; i < layout.getChildCount(); i++) {
            View view = layout.getChildAt(i);
            if (view instanceof LinearLayout) {
               for (int y = 0;y < ((ViewGroup)view).getChildCount(); y++) {
                   View childView = ((LinearLayout)view).getChildAt(y);
                   if (childView instanceof  EditText) {
                       String curString =  ((EditText)childView).getText().toString();
                       if (TextUtils.isEmpty(curString)) {
                          Toast.makeText(getContext(), "Fields Required" + ((EditText) childView).getHint(), Toast.LENGTH_LONG).show();
                           return false;
                       }
                   }
               }

            }

        }
        return true;
    }

    private void ClearInput() {
        ViewGroup layout = (ViewGroup) getView().findViewById(R.id.new_estimate_layout_input);

        for (int i = 0; i < layout.getChildCount(); i++) {
            View view = layout.getChildAt(i);
            if (view instanceof LinearLayout) {
                for (int y = 0;y < ((ViewGroup)view).getChildCount(); y++) {
                    View childView = ((LinearLayout)view).getChildAt(y);
                    if (childView instanceof  EditText) {
                        ((EditText)childView).setText("");

                    }
                }

            }

        }
    }

    class LoadCustomers extends AsyncTask<Void, Integer, Void> {

      //  ProgressDialog progress = new ProgressDialog(getContext());
        public LoadCustomers() {
        }

        @Override
        protected Void doInBackground(Void... params) {

            Cursor data = dbCustHelper.getData();
            while(data.moveToNext()) {
                //TODO change Customer to Estimate
                Customer cust = new Customer(data.getString(2), data.getString(1), data.getString(3), data.getString(4));
                String company = cust.companyName;
                String cID = cust.getCustomerID();
                custList.add(company);
            }


            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
           // progress.setTitle("Loading Companies!!");
           // progress.setIndeterminate(true);
           // progress.setProgress(0);
           // progress.show();

        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            ArrayAdapter<String> spinAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, custList);
            spinAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            companySpinner.setAdapter(spinAdapter);
          //  progress.dismiss();
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
           // progress.setProgress(values[0]);
        }
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

}
