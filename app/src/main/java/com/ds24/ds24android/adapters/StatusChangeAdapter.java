package com.ds24.ds24android.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ds24.ds24android.R;
import com.ds24.ds24android.adapters.filterAdapters.StatusAdapter;
import com.ds24.ds24android.retrofit.model.status.StatusData;
import com.ds24.ds24android.retrofit.model.statusTree.StatusResponseData;

import java.util.ArrayList;

/**
 * Created by well on 02.05.2017.
 */

public class StatusChangeAdapter extends RecyclerView.Adapter<StatusChangeAdapter.ViewHolder>{

    Context ctx;
    ArrayList<StatusData> statusDatas;
    public interface StatusSelectListener{void onStatusSelect(StatusData statusData);}
    StatusSelectListener statusSelectListener;

    public StatusChangeAdapter(Context ctx,ArrayList<StatusData> statusDatas, StatusSelectListener statusSelectListener){
        this.ctx=ctx;
        this.statusDatas=statusDatas;
        this.statusSelectListener=statusSelectListener;
    }

    @Override
    public StatusChangeAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(ctx).inflate(R.layout.just_text_item,parent,false);
        return new StatusChangeAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(StatusChangeAdapter.ViewHolder holder, int position) {
        holder.setData(statusDatas.get(position));
        holder.itemView.setOnClickListener(v -> statusSelectListener.onStatusSelect(statusDatas.get(position)));
    }

    @Override
    public int getItemCount() {
        return statusDatas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        public ViewHolder(View itemView) {
            super(itemView);
            textView=(TextView)itemView.findViewById(R.id.text_item);
        }

        public void setData(StatusData data) {
            textView.setText(data.status);
        }
    }
}
