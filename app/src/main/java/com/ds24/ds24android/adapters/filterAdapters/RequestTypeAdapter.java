package com.ds24.ds24android.adapters.filterAdapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ds24.ds24android.R;
import com.ds24.ds24android.retrofit.model.requestType.RequestTypeResponseData;

import java.util.ArrayList;

/**
 * Created by well on 26.04.2017.
 */

public class RequestTypeAdapter extends RecyclerView.Adapter<RequestTypeAdapter.ViewHolder> {

    Context ctx;
    ArrayList<RequestTypeResponseData> requestTypeDatas;
    public interface RequestTypeSelectListener {void onRequestTypeSelect(RequestTypeResponseData requestTypeData);}
    RequestTypeSelectListener requestTypeSelectListener;
    int selectedId;

    public RequestTypeAdapter(Context ctx, ArrayList<RequestTypeResponseData> requestTypeDatas,
                              RequestTypeSelectListener requestTypeSelectListener, int selectedId){
        this.ctx=ctx;
        this.requestTypeDatas=requestTypeDatas;
        this.requestTypeSelectListener=requestTypeSelectListener;
        this.selectedId=selectedId;
    }

    @Override
    public RequestTypeAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(ctx).inflate(R.layout.just_text_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RequestTypeAdapter.ViewHolder holder, int position) {
        holder.setData(requestTypeDatas.get(position));
        holder.itemView.setOnClickListener(v -> requestTypeSelectListener.onRequestTypeSelect(requestTypeDatas.get(position)));
    }

    @Override
    public int getItemCount() {
        return requestTypeDatas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        ImageView selectedImage;
        public ViewHolder(View itemView) {
            super(itemView);
            textView=(TextView)itemView.findViewById(R.id.text_item);
            selectedImage=(ImageView)itemView.findViewById(R.id.selected_image);
        }

        public void setData(RequestTypeResponseData data) {
            textView.setText(data.rtype);
            if(selectedId==data.rtypeId)
                selectedImage.setVisibility(View.VISIBLE);
            else
                selectedImage.setVisibility(View.INVISIBLE);
        }
    }
}
