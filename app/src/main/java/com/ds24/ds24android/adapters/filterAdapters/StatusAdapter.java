package com.ds24.ds24android.adapters.filterAdapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ds24.ds24android.R;
import com.ds24.ds24android.retrofit.model.statusTree.StatusResponseData;

import java.util.ArrayList;

/**
 * Created by well on 26.04.2017.
 */

public class StatusAdapter extends RecyclerView.Adapter<StatusAdapter.ViewHolder>{

    Context ctx;
    ArrayList<StatusResponseData> statusDatas;
    public interface StatusSelectListener{void onStatusSelect(StatusResponseData statusData);}
    StatusSelectListener statusSelectListener;
    String selectedStatus;

    public StatusAdapter(Context ctx, ArrayList<StatusResponseData> statusDatas,
                         StatusSelectListener statusSelectListener, String selectedStatus){
        this.ctx=ctx;
        this.statusDatas=statusDatas;
        this.statusSelectListener=statusSelectListener;
        this.selectedStatus=selectedStatus;
    }

    @Override
    public StatusAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(ctx).inflate(R.layout.just_text_item,parent,false);
        return new StatusAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(StatusAdapter.ViewHolder holder, int position) {
        holder.setData(statusDatas.get(position));
        holder.itemView.setOnClickListener(v -> statusSelectListener.onStatusSelect(statusDatas.get(position)));
    }

    @Override
    public int getItemCount() {
        return statusDatas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        ImageView selectedImage;
        public ViewHolder(View itemView) {
            super(itemView);
            textView=(TextView)itemView.findViewById(R.id.text_item);
            selectedImage=(ImageView)itemView.findViewById(R.id.selected_image);
        }

        public void setData(StatusResponseData data) {
            textView.setText(data.status);
            if(selectedStatus.equals(data.statusId))
                selectedImage.setVisibility(View.VISIBLE);
            else
                selectedImage.setVisibility(View.INVISIBLE);
        }
    }
}
