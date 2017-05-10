package com.ds24.ds24android.adapters.filterAdapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ds24.ds24android.R;
import com.ds24.ds24android.retrofit.model.reason.ReasonResponseData;

import java.util.ArrayList;

/**
 * Created by well on 26.04.2017.
 */

public class ReasonAdapter extends RecyclerView.Adapter<ReasonAdapter.ViewHolder>{

    Context ctx;
    ArrayList<ReasonResponseData> reasonDatas;
    public interface ReasonSelectListener{void onReasonSelectListener(ReasonResponseData data);}
    ReasonSelectListener reasonSelectListener;
    int selectedId;

    public ReasonAdapter(Context ctx, ArrayList<ReasonResponseData> reasonDatas,
                         ReasonSelectListener reasonSelectListener, int selectedId){
        this.ctx=ctx;
        this.reasonDatas=reasonDatas;
        this.reasonSelectListener=reasonSelectListener;
        this.selectedId=selectedId;
    }

    @Override
    public ReasonAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(ctx).inflate(R.layout.just_text_item,parent,false);
        return new ReasonAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ReasonAdapter.ViewHolder holder, int position) {
        holder.setData(reasonDatas.get(position));
        holder.itemView.setOnClickListener(v -> reasonSelectListener.onReasonSelectListener(reasonDatas.get(position)));
    }

    @Override
    public int getItemCount() {
        return reasonDatas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        ImageView selectedImage;
        public ViewHolder(View itemView) {
            super(itemView);
            textView=(TextView)itemView.findViewById(R.id.text_item);
            selectedImage=(ImageView)itemView.findViewById(R.id.selected_image);
        }

        public void setData(ReasonResponseData data) {
            textView.setText(data.reason);
            if(selectedId==data.reasonId)
                selectedImage.setVisibility(View.VISIBLE);
            else
                selectedImage.setVisibility(View.INVISIBLE);
        }
    }
}
