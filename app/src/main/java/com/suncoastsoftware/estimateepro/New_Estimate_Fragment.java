package com.suncoastsoftware.estimateepro;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;


public class New_Estimate_Fragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;

    EditText et_estimateID, et_custID, et_title, et_desc, et_notes;
    EditText et_due_date, et_shirt_sizes, et_quantity, et_shop_base_charge;
    EditText et_per_piece_price, et_screen_charge, et_numColors, et_total_price;

    CheckBox cb_both_sides;

    Button  btn_calculate, btn_save, btn_cancel;

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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_new__estimate, container, false);
        et_estimateID       = (EditText) view.findViewById(R.id.et_new_estimate_estimateID);
        et_custID           = (EditText) view.findViewById(R.id.et_new_estimate_custID);
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

        btn_calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btn_calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        return view;
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

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
