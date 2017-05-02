package com.ds24.ds24android.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ds24.ds24android.R;
import com.ds24.ds24android.retrofit.model.comments.CommentsData;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by well on 01.05.2017.
 */

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {

    ArrayList<CommentsData> commentsData;
    Context ctx;

    public CommentAdapter(ArrayList<CommentsData> commentsData, Context ctx){
        this.commentsData=commentsData;
        this.ctx=ctx;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.setData(commentsData.get(position));
    }

    @Override
    public int getItemCount() {
        return commentsData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView user;
        TextView created_at;
        TextView noteText;
        ImageView noteImage;

        public ViewHolder(View itemView) {
            super(itemView);
        }

        public void setData(CommentsData commentsData) {
            user=(TextView)itemView.findViewById(R.id.user_text);
            created_at=(TextView)itemView.findViewById(R.id.created_at_text);
            noteText=(TextView)itemView.findViewById(R.id.note_text);
            noteImage=(ImageView) itemView.findViewById(R.id.note_image);

            user.setText(commentsData.createdBy);
            created_at.setText(commentsData.createdAt);

            if(commentsData.mtype==0){
                noteText.setVisibility(View.VISIBLE);
                noteText.setText(commentsData.note);
                noteImage.setVisibility(View.GONE);
            }
            if(commentsData.mtype==1){
                noteText.setVisibility(View.GONE);
                noteImage.setVisibility(View.VISIBLE);
                Picasso.with(ctx).load(commentsData.note).into(noteImage);
            }


        }
    }
}
