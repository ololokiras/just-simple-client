package com.ds24.ds24android.adapters.filterAdapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ds24.ds24android.R;
import com.ds24.ds24android.retrofit.model.streets.StreetResponseData;

import java.util.ArrayList;

/**
 * Created by well on 25.04.2017.
 */

public class StreetAdapter extends RecyclerView.Adapter<StreetAdapter.ViewHolder> {

    Context ctx;
    ArrayList<StreetResponseData> streetDatas;
    public interface StreetSelectListener{void onStreetSelect(StreetResponseData streetData);}
    StreetSelectListener streetSelectListener;

    public StreetAdapter(Context ctx,ArrayList<StreetResponseData> streetDatas, StreetSelectListener streetSelectListener){
        this.ctx=ctx;
        this.streetDatas=streetDatas;
        this.streetSelectListener=streetSelectListener;
    }

    @Override
    public StreetAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(ctx).inflate(R.layout.just_text_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(StreetAdapter.ViewHolder holder, int position) {
        holder.setData(streetDatas.get(position));
        holder.itemView.setOnClickListener(v->streetSelectListener.onStreetSelect(streetDatas.get(position)));
    }

    @Override
    public int getItemCount() {
        return streetDatas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        public ViewHolder(View itemView) {
            super(itemView);
            textView=(TextView)itemView.findViewById(R.id.text_item);
        }

        public void setData(StreetResponseData streetResponseData) {
            textView.setText(streetResponseData.street);
        }
    }
}
