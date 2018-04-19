package com.kumail.tvshows.showdetails.cast;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kumail.tvshows.R;
import com.kumail.tvshows.db.AppDatabase;
import com.kumail.tvshows.db.entity.CastEntity;
import com.kumail.tvshows.showdetails.ExpandedShowActivity;

import java.util.List;

/**
 * Created by kumail on 23/03/2018.
 */

public class CastFragment extends Fragment {
    private AppDatabase appDatabase;
    private List<CastEntity> casts;

    private RecyclerView mRecyclerView;
    private CastAdapter mAdapter;

    public CastFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cast, container, false);

        mRecyclerView = view.findViewById(R.id.rv);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setHasFixedSize(true);
        mAdapter = new CastAdapter(casts, getActivity());
        if (casts != null) {
            mRecyclerView.setAdapter(mAdapter);
        }


//		TextView tv = view.findViewById(R.id.name);
//		tv.setText(casts.get(0).getName());
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        appDatabase = AppDatabase.getInstance(getActivity());
        AsyncTask.execute(() -> casts = appDatabase.castDao().loadCast(((ExpandedShowActivity) getActivity()).getTmdbId()));

    }
}
