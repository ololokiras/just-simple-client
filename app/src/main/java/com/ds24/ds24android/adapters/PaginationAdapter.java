package com.ds24.ds24android.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ds24.ds24android.R;
import com.ds24.ds24android.retrofit.model.request.DataRequest;

import java.util.ArrayList;

/**
 * Created by well on 13.05.2017.
 */

public class PaginationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int ITEM = 0;
    private static final int LOADING = 1;
    Context ctx;
    ArrayList<DataRequest> dataRequests;
    private boolean isLoadingAdded = false;
    private boolean retryPageLoad = false;

    private PaginationAdapterCallback mCallback;

    private String errorMsg;

    OnRequestSelectedListener requestSelectedListener;
    public interface OnRequestSelectedListener{
        void onRequestSelect(int requestId);
    }

    public PaginationAdapter(Context ctx, OnRequestSelectedListener requestSelectedListener){
        this.ctx=ctx;
        this.mCallback=(PaginationAdapterCallback) ctx;
        dataRequests=new ArrayList<>();
        this.requestSelectedListener=requestSelectedListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        RecyclerView.ViewHolder viewHolder = null;

        switch (viewType){
            case ITEM:
                viewHolder = getViewHolder(parent, inflater);
                break;

            case LOADING:
                View v=inflater.inflate(R.layout.item_progress,parent,false);
                viewHolder=new LoadingVH(v);
                break;
        }
        return viewHolder;
    }

    @NonNull
    private RecyclerView.ViewHolder getViewHolder(ViewGroup parent, LayoutInflater inflater) {
        RecyclerView.ViewHolder viewHolder;
        View v1 = inflater.inflate(R.layout.request_item, parent, false);
        viewHolder = new RequestVH(v1);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        DataRequest result=dataRequests.get(position);
        switch (getItemViewType(position)){
            case ITEM:
                final  RequestVH requestVH=(RequestVH) holder;
                requestVH.idText.setText("#"+result.requestId);
                requestVH.dataText.setText(result.createdAt);

                String addressString="";
                addressString+=result.house;
                if(!TextUtils.isEmpty(result.flat))
                    addressString+=" кв. "+result.flat;
                requestVH.addressText.setText(addressString);

                requestVH.reasonText.setText(result.note);
                requestVH.statusText.setText(result.status);

                requestVH.itemView.setOnClickListener(v->startRequestDetail(position));

                break;

            case LOADING:
                LoadingVH loadingVH=(LoadingVH) holder;
                if(retryPageLoad){
                    loadingVH.mErrorLayout.setVisibility(View.VISIBLE);
                    loadingVH.mProgressBar.setVisibility(View.GONE);

                    loadingVH.mErrorTxt.setText(
                            errorMsg != null ?
                                    errorMsg :
                                    ///поправить
                                    ctx.getString(R.string.operation_error));

                } else {
                    loadingVH.mErrorLayout.setVisibility(View.GONE);
                    loadingVH.mProgressBar.setVisibility(View.VISIBLE);
                }
                break;

        }
    }

    private void startRequestDetail(int position){
        requestSelectedListener.onRequestSelect(dataRequests.get(position).requestId);

    }

    public void add(DataRequest r) {
        dataRequests.add(r);
        notifyItemInserted(dataRequests.size() - 1);
    }

    public void addAll(ArrayList<DataRequest> results) {
        for (DataRequest result : results) {
            add(result);
        }
    }

    public void remove(DataRequest r) {
        int position = dataRequests.indexOf(r);
        if (position > -1) {
            dataRequests.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void addLoadingFooter() {
        isLoadingAdded = true;
        add(new DataRequest());
    }

    public void removeLoadingFooter() {
        isLoadingAdded = false;

        int position = dataRequests.size() - 1;
        DataRequest result = getItem(position);

        if (result != null) {
            dataRequests.remove(position);
            notifyItemRemoved(position);
        }
    }

    public DataRequest getItem(int position) {
        return dataRequests.get(position);
    }

    @Override
    public int getItemViewType(int position) {
        return (position == dataRequests.size() - 1 && isLoadingAdded) ? LOADING : ITEM;
    }


    @Override
    public int getItemCount() {
        return dataRequests == null ? 0 : dataRequests.size();
    }


    protected class RequestVH extends RecyclerView.ViewHolder {
        private TextView idText;
        TextView dataText;
        TextView addressText;
        TextView reasonText;
        TextView statusText;

        public RequestVH(View itemView) {
            super(itemView);
            idText=(TextView)itemView.findViewById(R.id.request_id);
            dataText=(TextView)itemView.findViewById(R.id.request_date);
            addressText=(TextView)itemView.findViewById(R.id.address);
            reasonText=(TextView)itemView.findViewById(R.id.reason);
            statusText=(TextView)itemView.findViewById(R.id.status);
        }
    }

    public void showRetry(boolean show, @Nullable String errorMsg) {
        retryPageLoad = show;
        notifyItemChanged(dataRequests.size() - 1);

        if (errorMsg != null) this.errorMsg = errorMsg;
    }

    protected class LoadingVH extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ProgressBar mProgressBar;
        private ImageButton mRetryBtn;
        private TextView mErrorTxt;
        private LinearLayout mErrorLayout;

        public LoadingVH(View itemView) {
            super(itemView);
            mProgressBar = (ProgressBar) itemView.findViewById(R.id.loadmore_progress);
            mRetryBtn = (ImageButton) itemView.findViewById(R.id.loadmore_retry);
            mErrorTxt = (TextView) itemView.findViewById(R.id.loadmore_errortxt);
            mErrorLayout = (LinearLayout) itemView.findViewById(R.id.loadmore_errorlayout);

            mRetryBtn.setOnClickListener(this);
            mErrorLayout.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.loadmore_retry:
                case R.id.loadmore_errorlayout:

                    showRetry(false, null);
                    mCallback.retryPageLoad();

                    break;
            }
        }
    }
}
