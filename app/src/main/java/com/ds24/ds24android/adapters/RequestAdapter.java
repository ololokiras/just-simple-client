package com.ds24.ds24android.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ds24.ds24android.R;
import com.ds24.ds24android.retrofit.model.request.DataRequest;

import java.util.ArrayList;

/**
 * Created by well on 24.04.2017.
 */

public class RequestAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public final int TYPE_REQUEST=0;
    public final int TYPE_LOAD=1;

    Context ctx;
    ArrayList<DataRequest> dataRequests;
    OnLoadMoreListener loadMoreListener;
    OnRequestSelectedListener requestSelectedListener;
    boolean isLoading=false, isMoreDataAvailable=true;

    public interface OnLoadMoreListener{
        void onLoadMore();
    }

    public interface OnRequestSelectedListener{
        void onRequestSelect(int requestId);
    }

    public void setLoadMoreListener(OnLoadMoreListener loadMoreListener){
        this.loadMoreListener=loadMoreListener;
    }

    public RequestAdapter(Context ctx, ArrayList<DataRequest> dataRequests, OnRequestSelectedListener requestSelectedListener){
        this.ctx=ctx;
        this.dataRequests=dataRequests;
        this.requestSelectedListener=requestSelectedListener;
    }



    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(ctx);
        if(viewType==TYPE_REQUEST)
            return new RequestHolder(inflater.inflate(R.layout.request_item,parent,false));

        else
            return new LoadHolder(inflater.inflate(R.layout.row_load,parent,false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(position>=getItemCount()-1 && isMoreDataAvailable && !isLoading && loadMoreListener!=null){
            isLoading=true;
            loadMoreListener.onLoadMore();
        }

        if(getItemViewType(position)==TYPE_REQUEST){
            ((RequestHolder) holder).setData(dataRequests.get(position));
            holder.itemView.setOnClickListener(v->startRequestDetail(position));
        }
    }

    private void startRequestDetail(int position){
        requestSelectedListener.onRequestSelect(dataRequests.get(position).requestId);

    }

    @Override
    public int getItemViewType(int position){
        if(dataRequests.get(position)!=null){
            return TYPE_REQUEST;
        }
        else {
            return TYPE_LOAD;
        }
    }

    @Override
    public long getItemId(int position) {
        return dataRequests.get(position).requestId;
    }


    @Override
    public int getItemCount() {
        return dataRequests.size();
    }

    private class RequestHolder extends RecyclerView.ViewHolder {
        TextView idText;
        TextView dataText;
        TextView addressText;
        TextView reasonText;
        TextView statusText;

        public RequestHolder(View itemView) {
            super(itemView);
            idText=(TextView)itemView.findViewById(R.id.request_id);
            dataText=(TextView)itemView.findViewById(R.id.request_date);
            addressText=(TextView)itemView.findViewById(R.id.address);
            reasonText=(TextView)itemView.findViewById(R.id.reason);
            statusText=(TextView)itemView.findViewById(R.id.status);
        }

        public void setData(DataRequest dataRequest) {
            idText.setText("#"+dataRequest.requestId);
            dataText.setText(dataRequest.createdAt);

            String addressString="";
            addressString+=dataRequest.house;
            if(!TextUtils.isEmpty(dataRequest.flat))
                addressString+=" кв. "+dataRequest.flat;
            addressText.setText(addressString);

            reasonText.setText(dataRequest.note);
            statusText.setText(dataRequest.status);
        }
    }

    private class LoadHolder extends RecyclerView.ViewHolder {
        public LoadHolder(View itemView) {
            super(itemView);
        }
    }

    public void setMoreDataAvailable(boolean moreDataAvailable){
        isMoreDataAvailable=moreDataAvailable;
    }

    public void notifyDataChanged(){
        notifyDataSetChanged();
        isLoading=false;
    }

}
