package com.ds24.ds24android.adapters.filterAdapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ds24.ds24android.R;
import com.ds24.ds24android.retrofit.model.employee.EmployeeResponseData;

import java.util.ArrayList;

/**
 * Created by well on 26.04.2017.
 */

public class EmployeeAdapter extends RecyclerView.Adapter<EmployeeAdapter.ViewHolder> {

    Context ctx;
    ArrayList<EmployeeResponseData>  employeeDatas;
    public interface EmployeeSelectedListener{void onEmployeeSelect(EmployeeResponseData employeeData);}
    EmployeeSelectedListener employeeSelectedListener;
    int selectedId;

    public EmployeeAdapter(Context ctx, ArrayList<EmployeeResponseData> employeeDatas,
                           EmployeeSelectedListener employeeSelectedListener, int selectedId){
        this.ctx=ctx;
        this.employeeDatas=employeeDatas;
        this.employeeSelectedListener=employeeSelectedListener;
        this.selectedId=selectedId;
    }

    @Override
    public EmployeeAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(ctx).inflate(R.layout.just_text_item,parent,false);
        return new EmployeeAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(EmployeeAdapter.ViewHolder holder, int position) {
        holder.setData(employeeDatas.get(position));
        holder.itemView.setOnClickListener(v -> employeeSelectedListener.onEmployeeSelect(employeeDatas.get(position)));
    }

    @Override
    public int getItemCount() {
        return employeeDatas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        ImageView selectedImage;
        public ViewHolder(View itemView) {
            super(itemView);
            textView=(TextView)itemView.findViewById(R.id.text_item);
            selectedImage=(ImageView)itemView.findViewById(R.id.selected_image);
        }

        public void setData(EmployeeResponseData data) {
            textView.setText(data.emp);
            if(selectedId==data.empId)
                selectedImage.setVisibility(View.VISIBLE);
            else
                selectedImage.setVisibility(View.INVISIBLE);
        }
    }
}
