package com.kumail.tvshows.home;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kumail.tvshows.R;
import com.kumail.tvshows.Show;

import java.util.ArrayList;

/**
 * Created by kumail on 11/11/2017.
 */

public class HomeFragment extends Fragment
{
	private RecyclerView mRecyclerView;
	private HomeAdapter mAdapter;

	ArrayList<Show> list = new ArrayList<>();
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_home, container, false);

		Show s = new Show();

		list.add(s);
		mRecyclerView = view.findViewById(R.id.rv);
		mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
		mRecyclerView.setItemAnimator(new DefaultItemAnimator());
		mRecyclerView.setHasFixedSize(true);
		mAdapter =  new HomeAdapter(list, R.layout.item_home_card);
		mRecyclerView.setAdapter(mAdapter);

		setHasOptionsMenu(true);
		return view;
	}

}
