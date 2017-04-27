package com.ds24.ds24android.adapters.filterAdapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ds24.ds24android.R;
import com.ds24.ds24android.retrofit.model.responsible.ResponsibleResponseData;

import java.util.ArrayList;

/**
 * Created by well on 26.04.2017.
 */

public class ResponsibleAdapter extends RecyclerView.Adapter<ResponsibleAdapter.ViewHolder>{

    Context ctx;
    ArrayList<ResponsibleResponseData> responsibleDatas;
    public interface ResponsibleSelectListener{void onResponsibleSelect(ResponsibleResponseData responsibleData);}
    ResponsibleSelectListener responsibleSelectListener;

    public ResponsibleAdapter(Context ctx, ArrayList<ResponsibleResponseData> responsibleDatas, ResponsibleSelectListener responsibleSelectListener){
        this.ctx=ctx;
        this.responsibleDatas=responsibleDatas;
        this.responsibleSelectListener=responsibleSelectListener;
    }

    @Override
    public ResponsibleAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(ctx).inflate(R.layout.just_text_item,parent,false);
        return new ResponsibleAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ResponsibleAdapter.ViewHolder holder, int position) {
        holder.setData(responsibleDatas.get(position));
        holder.itemView.setOnClickListener(v -> responsibleSelectListener.onResponsibleSelect(responsibleDatas.get(position)));
    }

    @Override
    public int getItemCount() {
        return responsibleDatas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        public ViewHolder(View itemView) {
            super(itemView);
            textView=(TextView)itemView.findViewById(R.id.text_item);
        }

        public void setData(ResponsibleResponseData data) {
            textView.setText(data.resp);
        }
    }
}
