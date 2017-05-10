package com.ds24.ds24android.adapters.filterAdapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ds24.ds24android.R;
import com.ds24.ds24android.retrofit.model.workType.WorkTypeResponseData;

import java.util.ArrayList;

/**
 * Created by well on 26.04.2017.
 */

public class WorkTypeAdapter extends RecyclerView.Adapter<WorkTypeAdapter.ViewHolder>{

    Context ctx;
    ArrayList<WorkTypeResponseData> workTypeDatas;
    public interface WorkTypeSelectListener{void onWorkTypeSelect(WorkTypeResponseData workTypeData);}
    WorkTypeSelectListener workTypeSelectListener;
    int selectedId;

    public WorkTypeAdapter(Context ctx, ArrayList<WorkTypeResponseData> workTypeDatas,
                           WorkTypeSelectListener workTypeSelectListener, int selectedId){
        this.ctx=ctx;
        this.workTypeDatas=workTypeDatas;
        this.workTypeSelectListener=workTypeSelectListener;
        this.selectedId=selectedId;
    }

    @Override
    public WorkTypeAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(ctx).inflate(R.layout.just_text_item,parent,false);
        return new WorkTypeAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(WorkTypeAdapter.ViewHolder holder, int position) {
        holder.setData(workTypeDatas.get(position));
        holder.itemView.setOnClickListener(v -> workTypeSelectListener.onWorkTypeSelect(workTypeDatas.get(position)));
    }

    @Override
    public int getItemCount() {
        return workTypeDatas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        ImageView selectedImage;
        public ViewHolder(View itemView) {
            super(itemView);
            textView=(TextView)itemView.findViewById(R.id.text_item);
            selectedImage=(ImageView)itemView.findViewById(R.id.selected_image);
        }

        public void setData(WorkTypeResponseData data) {
            textView.setText(data.workType);
            if(selectedId==data.workTypeId)
                selectedImage.setVisibility(View.VISIBLE);
            else
                selectedImage.setVisibility(View.INVISIBLE);
        }
    }
}
