package com.suncoastsoftware.estimateepro.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

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
        return null;
    }

    @Override
    public void onBindViewHolder(EstimateViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class EstimateViewHolder extends RecyclerView.ViewHolder {
        public EstimateViewHolder(View itemView) {

            super(itemView);

        }
    }
}
