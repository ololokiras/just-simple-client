package com.ds24.ds24android.adapters.filterAdapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ds24.ds24android.R;
import com.ds24.ds24android.retrofit.model.house.HouseResponseData;

import java.util.ArrayList;

/**
 * Created by well on 26.04.2017.
 */

public class HouseAdapter extends RecyclerView.Adapter<HouseAdapter.ViewHolder>{

    Context ctx;
    ArrayList<HouseResponseData> houseDatas;
    public interface HouseSelectListener{void onHouseSelect(HouseResponseData houseData);}
    HouseSelectListener houseSelectListener;
    int selectedId;

    public HouseAdapter(Context ctx,ArrayList<HouseResponseData> houseDatas,
                        HouseSelectListener houseSelectListener, int selectedId){
        this.ctx=ctx;
        this.houseDatas=houseDatas;
        this.houseSelectListener=houseSelectListener;
        this.selectedId=selectedId;
    }

    @Override
    public HouseAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(ctx).inflate(R.layout.just_text_item,parent,false);
        return new HouseAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(HouseAdapter.ViewHolder holder, int position) {
        holder.setData(houseDatas.get(position));
        holder.itemView.setOnClickListener(v->houseSelectListener.onHouseSelect(houseDatas.get(position)));
    }

    @Override
    public int getItemCount() {
        return houseDatas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        ImageView selectedImage;
        public ViewHolder(View itemView) {
            super(itemView);
            textView=(TextView)itemView.findViewById(R.id.text_item);
            selectedImage=(ImageView)itemView.findViewById(R.id.selected_image);
        }

        public void setData(HouseResponseData data) {
            textView.setText(data.house);
            if(data.houseId==selectedId)
                selectedImage.setVisibility(View.VISIBLE);
            else
                selectedImage.setVisibility(View.INVISIBLE);
        }
    }
}
