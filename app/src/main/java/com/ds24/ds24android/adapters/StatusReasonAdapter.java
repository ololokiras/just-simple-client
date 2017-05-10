package com.ds24.ds24android.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ds24.ds24android.R;
import com.ds24.ds24android.retrofit.model.status.StatusData;
import com.ds24.ds24android.retrofit.model.statusReason.StatusReasonResponseData;

import java.util.ArrayList;

/**
 * Created by well on 09.05.2017.
 */

public class StatusReasonAdapter extends RecyclerView.Adapter<StatusReasonAdapter.ViewHolder>{

    Context ctx;
    ArrayList<StatusReasonResponseData> reasonDatas;
    public interface ReasonSelectListener{void onReasonSelect(StatusReasonResponseData reasonData);}
    ReasonSelectListener reasonSelectListener;

    public StatusReasonAdapter(Context ctx, ArrayList<StatusReasonResponseData> reasonDatas,
                               ReasonSelectListener reasonSelectListener){
        this.ctx=ctx;
        this.reasonDatas=reasonDatas;
        this.reasonSelectListener=reasonSelectListener;
    }

    @Override
    public StatusReasonAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(ctx).inflate(R.layout.just_text_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(StatusReasonAdapter.ViewHolder holder, int position) {
        holder.setData(reasonDatas.get(position));
        holder.itemView.setOnClickListener(v->reasonSelectListener.onReasonSelect(reasonDatas.get(position)));
    }

    @Override
    public int getItemCount() {
        return reasonDatas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        public ViewHolder(View itemView) {
            super(itemView);
            textView=(TextView)itemView.findViewById(R.id.text_item);
        }

        public void setData(StatusReasonResponseData data) {
            textView.setText(data.reason);
        }
    }
}
