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

import com.ds24.ds24android.R;
import com.ds24.ds24android.adapters.UpdatesAdapter;
import com.ds24.ds24android.repository.Constants;
import com.ds24.ds24android.repository.PreferencesBuffer;
import com.ds24.ds24android.retrofit.ServerAPI;
import com.ds24.ds24android.retrofit.model.comments.CommentAsk;
import com.ds24.ds24android.retrofit.model.comments.Req;
import com.ds24.ds24android.retrofit.model.updates.Updates;
import com.ds24.ds24android.retrofit.model.updates.UpdatesData;
import com.ds24.ds24android.utils.Functions;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link UpdatesFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link UpdatesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UpdatesFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    ServerAPI serverAPI;
    RecyclerView recyclerView;
    int requestId;
    SwipeRefreshLayout swipeRefreshLayout;

    private OnFragmentInteractionListener mListener;

    public UpdatesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UpdatesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UpdatesFragment newInstance(String param1, String param2) {
        UpdatesFragment fragment = new UpdatesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
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
        View rootView=inflater.inflate(R.layout.fragment_updates, container, false);
        recyclerView=(RecyclerView)rootView.findViewById(R.id.recycler_changes);
        LinearLayoutManager layoutManager=new LinearLayoutManager(getActivity());
        layoutManager.setReverseLayout(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        swipeRefreshLayout =(SwipeRefreshLayout)rootView.findViewById(R.id.swipe_updates);
        swipeRefreshLayout.setOnRefreshListener(()->doUpdateRequest(requestId));

        doUpdateRequest(requestId);
        return rootView;
    }

    @Override
    public void onViewCreated(View rootView, Bundle savedInstanceState) {
        super.onViewCreated(rootView, savedInstanceState);
    }

    private void doUpdateRequest(int requestId) {
        //модель json'a та же, поэтому не стал запариваться
        CommentAsk changesAsk =new CommentAsk();
        changesAsk.act=Constants.getUpdatesList;
        changesAsk.req=new Req();
        changesAsk.req.start=1;
        changesAsk.req.count=Constants.paginationSize;
        changesAsk.req.requestId=requestId;

        Call<Updates> callChanges=serverAPI.getRequestChanges(
                Functions.encodeToBase64(PreferencesBuffer.getToken(getActivity().getApplicationContext())),
                changesAsk);

        callChanges.enqueue(new Callback<Updates>() {
            @Override
            public void onResponse(Call<Updates> call, Response<Updates> response) {
                if(response.isSuccessful())
                    if(response.body().ok)
                        if(response.body().token)
                            fillContent(response.body().data);
            }

            @Override
            public void onFailure(Call<Updates> call, Throwable t) {

            }
        });
    }

    private void fillContent(ArrayList<UpdatesData> data) {
        UpdatesAdapter changesAdapter=new UpdatesAdapter(data,getActivity().getApplicationContext());
        recyclerView.setAdapter(changesAdapter);
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
