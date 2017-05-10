package com.ds24.ds24android.adapters.filterAdapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.ds24.ds24android.R;
import com.ds24.ds24android.retrofit.model.contractors.ContractorResponseData;

import java.util.ArrayList;

/**
 * Created by well on 24.04.2017.
 */

public class ContractorsAdapter extends RecyclerView.Adapter<ContractorsAdapter.ViewHolder>{

    Context ctx;
    ArrayList<ContractorResponseData> contractorDatas;
    public interface ContractorSelectListener{void onContractorSelect(ContractorResponseData contractorResponseData);}
    ContractorSelectListener contractorSelectListener;
    int selectedId;

    public ContractorsAdapter(Context ctx,ArrayList<ContractorResponseData> contractorDatas,
                              ContractorSelectListener contractorSelectListener, int selectedId){
        this.ctx=ctx;
        this.contractorDatas=contractorDatas;
        this.contractorSelectListener=contractorSelectListener;
        this.selectedId=selectedId;
    }

    @Override
    public ContractorsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(ctx).inflate(R.layout.just_text_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ContractorsAdapter.ViewHolder holder, int position) {
        holder.setData(contractorDatas.get(position));
        holder.itemView.setOnClickListener(v->contractorSelectListener.onContractorSelect(contractorDatas.get(position)));
    }

    @Override
    public int getItemCount() {
        return contractorDatas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        ImageView selectedImage;
        public ViewHolder(View itemView) {
            super(itemView);
            textView=(TextView)itemView.findViewById(R.id.text_item);
            selectedImage=(ImageView)itemView.findViewById(R.id.selected_image);
        }

        public void setData(ContractorResponseData contractorResponseData) {
            textView.setText(contractorResponseData.cnt);
            if(selectedId==contractorResponseData.cntId)
                selectedImage.setVisibility(View.VISIBLE);
            else
                selectedImage.setVisibility(View.INVISIBLE);
        }
    }
}
