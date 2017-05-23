package com.codeasylums.stockmarket;

import java.util.ArrayList;
import java.util.List;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class MyStocksFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    TextView textView;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager ShareTransactionObjectLayoutManager;
    private MyStocksAdapter                 ShareTransactionObjectAdapter;
    List<ShareTransactionObject> ShareTransactionObjectList = new ArrayList<>();
    FragmentTransaction ft;

    private OnFragmentInteractionListener mListener;

    public MyStocksFragment() {
        // Required empty public constructor
    }

    public static MyStocksFragment newInstance(String param1, String param2) {
        MyStocksFragment fragment = new MyStocksFragment();
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
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }




    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootview=inflater.inflate(R.layout.fragment_my_stocks, container, false);
        ShareTransactionObjectLayoutManager= new LinearLayoutManager(getActivity());
        recyclerView=(RecyclerView)rootview.findViewById(R.id.rview);
        //textView=(TextView) rootview.findViewById(R.id.textView2) ;
        SQLiteHandler sqliteHandler=new SQLiteHandler(getActivity());
        recyclerView.setLayoutManager(ShareTransactionObjectLayoutManager);
       ShareTransactionObjectList=sqliteHandler.getSharedData();
        ShareTransactionObjectAdapter = new MyStocksAdapter(ShareTransactionObjectList,getActivity());
        recyclerView.setAdapter(ShareTransactionObjectAdapter);
        return rootview;

    }
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("My Stocks");
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

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }







}


