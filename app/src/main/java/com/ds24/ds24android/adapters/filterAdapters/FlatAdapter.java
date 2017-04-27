package com.ds24.ds24android.adapters.filterAdapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ds24.ds24android.R;
import com.ds24.ds24android.retrofit.model.flat.FlatResponseData;

import java.util.ArrayList;

/**
 * Created by well on 26.04.2017.
 */

public class FlatAdapter extends RecyclerView.Adapter<FlatAdapter.ViewHolder>{

    Context ctx;
    ArrayList<FlatResponseData> flatDatas;
    public interface FlatSelectListener{void onFlatSelect(FlatResponseData flatData);}
    FlatSelectListener flatSelectListener;

    public FlatAdapter(Context ctx, ArrayList<FlatResponseData> flatDatas, FlatSelectListener flatSelectListener){
        this.ctx=ctx;
        this.flatDatas=flatDatas;
        this.flatSelectListener=flatSelectListener;
    }

    @Override
    public FlatAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(ctx).inflate(R.layout.just_text_item,parent,false);
        return new FlatAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FlatAdapter.ViewHolder holder, int position) {
        holder.setData(flatDatas.get(position));
        holder.itemView.setOnClickListener(v -> flatSelectListener.onFlatSelect(flatDatas.get(position)));
    }

    @Override
    public int getItemCount() {
        return flatDatas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        public ViewHolder(View itemView) {
            super(itemView);
            textView=(TextView)itemView.findViewById(R.id.text_item);
        }

        public void setData(FlatResponseData data) {
            textView.setText(data.flat);
        }
    }
}
