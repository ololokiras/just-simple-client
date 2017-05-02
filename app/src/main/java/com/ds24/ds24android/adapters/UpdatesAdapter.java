package com.ds24.ds24android.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ds24.ds24android.R;
import com.ds24.ds24android.retrofit.model.updates.UpdatesData;

import java.util.ArrayList;

/**
 * Created by well on 01.05.2017.
 */

public class UpdatesAdapter extends RecyclerView.Adapter<UpdatesAdapter.ViewHolder>{

    Context ctx;
    ArrayList<UpdatesData> updatesDatas;

    public UpdatesAdapter(ArrayList<UpdatesData> updatesDatas, Context ctx){
        this.updatesDatas =updatesDatas;
        this.ctx=ctx;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.updates_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.setData(updatesDatas.get(position));
    }

    @Override
    public int getItemCount() {
        return updatesDatas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView user;
        TextView createdAt;
        TextView note;

        public ViewHolder(View itemView) {
            super(itemView);
        }

        public void setData(UpdatesData changesData) {
            user=(TextView)itemView.findViewById(R.id.user_text);
            createdAt=(TextView)itemView.findViewById(R.id.created_at_text);
            note=(TextView)itemView.findViewById(R.id.note_text);

            user.setText(changesData.createdBy);
            createdAt.setText(changesData.createdAt);
            note.setText(changesData.note);
        }
    }
}
