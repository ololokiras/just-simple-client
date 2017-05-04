package com.ds24.ds24android.fragments;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.ds24.ds24android.R;
import com.ds24.ds24android.adapters.CommentAdapter;
import com.ds24.ds24android.repository.Constants;
import com.ds24.ds24android.repository.PreferencesBuffer;
import com.ds24.ds24android.retrofit.ServerAPI;
import com.ds24.ds24android.retrofit.model.commentUpdate.RequestUpdate;
import com.ds24.ds24android.retrofit.model.commentUpdate.RequestionDetailParams;
import com.ds24.ds24android.retrofit.model.commentUpdate.UpdateAskComment;
import com.ds24.ds24android.retrofit.model.commentUpdate.UpdateAskInnerComment;
import com.ds24.ds24android.retrofit.model.comments.CommentAsk;
import com.ds24.ds24android.retrofit.model.comments.Comments;
import com.ds24.ds24android.retrofit.model.comments.CommentsData;
import com.ds24.ds24android.retrofit.model.comments.Req;
import com.ds24.ds24android.utils.Functions;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CommentsFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    ServerAPI serverAPI;
    RecyclerView recyclerView;
    int requestId;
    SwipeRefreshLayout swipeRefreshLayout;
    EditText commentEdit;
    Button commentButton;

    private OnFragmentInteractionListener mListener;

    public CommentsFragment() {
        // Required empty public constructor
    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

            requestId=getArguments().getInt(Constants.requestId);
        }
        serverAPI= ServerAPI.retrofit.create(ServerAPI.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView=inflater.inflate(R.layout.fragment_comments, container, false);
        recyclerView=(RecyclerView)rootView.findViewById(R.id.comments_recycler);
        LinearLayoutManager layoutManager=new LinearLayoutManager(getActivity());
        layoutManager.setReverseLayout(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        swipeRefreshLayout=(SwipeRefreshLayout)rootView.findViewById(R.id.swipe_comments);
        swipeRefreshLayout.setOnRefreshListener(()->doRequest(requestId));

        commentEdit=(EditText)rootView.findViewById(R.id.comment_edit);
        commentButton=(Button)rootView.findViewById(R.id.send_comment_button);
        commentButton.setOnClickListener(v -> sendComment());

        doRequest(requestId);
        return rootView;
    }

    private void sendComment() {
        if(commentEdit.getText().toString().length()==0)
            return;
        String comment=commentEdit.getText().toString();
        UpdateAskInnerComment updateAskInnerComment=new UpdateAskInnerComment();
        updateAskInnerComment.act=Constants.setUpdateRequest;
        updateAskInnerComment.req=new RequestionDetailParams();
        updateAskInnerComment.req.requestId=requestId;
        updateAskInnerComment.data=new UpdateAskComment();
        updateAskInnerComment.data.comment=comment;
        Call<RequestUpdate> updateCall=serverAPI.updateCommet(Functions.encodeToBase64(PreferencesBuffer.getToken(getActivity().getApplication())),
                updateAskInnerComment);
        updateCall.enqueue(new Callback<RequestUpdate>() {
            @Override
            public void onResponse(Call<RequestUpdate> call, Response<RequestUpdate> response) {
                if(response.isSuccessful())
                    if (response.body().ok)
                        if(response.body().token) {
                            doRequest(requestId);
                            commentEdit.setText("");
                        }
            }

            @Override
            public void onFailure(Call<RequestUpdate> call, Throwable t) {

            }
        });
    }

    private void doRequest(int requestId) {

        CommentAsk commentsAsk =new CommentAsk();
        commentsAsk.act=Constants.getCommentList;
        commentsAsk.req=new Req();
        commentsAsk.req.start=1;
        commentsAsk.req.count=30;
        commentsAsk.req.requestId=requestId;

        Call<Comments> callComments=serverAPI.getRequestComment(
                Functions.encodeToBase64(PreferencesBuffer.getToken(getActivity().getApplicationContext())),
                commentsAsk);
        callComments.enqueue(new Callback<Comments>() {
            @Override
            public void onResponse(Call<Comments> call, Response<Comments> response) {
                if(response.isSuccessful())
                    if(response.body().ok==true)
                        if(response.body().token==true)
                            fillContent(response.body().data);
            }

            @Override
            public void onFailure(Call<Comments> call, Throwable t) {

            }
        });
    }

    private void fillContent(ArrayList<CommentsData> data) {
      //  Collections.reverse(data);
        CommentAdapter commentAdapter=new CommentAdapter(data,getActivity().getApplication());
        recyclerView.setAdapter(commentAdapter);
        swipeRefreshLayout.setRefreshing(false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }



    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
