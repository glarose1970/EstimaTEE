package com.suncoastsoftware.estimateepro.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.suncoastsoftware.estimateepro.R;
import com.suncoastsoftware.estimateepro.model.Estimate;

import java.util.List;

/**
 * Created by Command Center on 10/13/2017.
 */

public class EstimatesRecyclerAdapter extends RecyclerView.Adapter<EstimatesRecyclerAdapter.EstimateViewHolder>{

    private Context context;
    private List<Estimate> estimateList;

    public EstimatesRecyclerAdapter(Context context, List<Estimate> _estimateList) {
        this.context = context;
        this.estimateList = _estimateList;
    }

    @Override
    public EstimateViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.estimate_row_item, parent, false);

        return new EstimateViewHolder(view);
    }

    @Override
    public void onBindViewHolder(EstimateViewHolder holder, int position) {

        Estimate estimate = estimateList.get(position);
        holder.estimateID.setText("ID : " + estimate.getEstimateID());
        holder.company.setText(estimate.getCustName());
        holder.title.setText("Title : " + estimate.getTitle());
        holder.price.setText("Price : " + estimate.getTotal_price());
    }

    @Override
    public int getItemCount() {
        return estimateList.size();
    }

    public class EstimateViewHolder extends RecyclerView.ViewHolder {

        public TextView estimateID, company, title, price;

        public EstimateViewHolder(View itemView) {
            super(itemView);

            estimateID = (TextView) itemView.findViewById(R.id.row_item_tv_estimate_id);
            company    = (TextView) itemView.findViewById(R.id.row_item_tv_company_id);
            title      = (TextView) itemView.findViewById(R.id.row_item_tv_title);
            price      = (TextView) itemView.findViewById(R.id.row_item_tv_price);
        }
    }


}
