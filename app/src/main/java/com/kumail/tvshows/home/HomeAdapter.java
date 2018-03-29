package com.kumail.tvshows.home;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kumail.tvshows.R;
import com.kumail.tvshows.trakt.Show;

import java.util.ArrayList;

/**
 * Created by kumail on 11/11/2017.
 */

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder>
{
	private ArrayList<Show> shows;
	private int rowLayout;

	public HomeAdapter(ArrayList<Show> list, int layout) {
		shows = list;
		rowLayout = layout;
	}


	public static class ViewHolder extends RecyclerView.ViewHolder
	{
		TextView title;
		TextView rating;
		ImageView image;

		public ViewHolder(View itemView)
		{
			super(itemView);

			title = itemView.findViewById(R.id.title);
			rating = itemView.findViewById(R.id.rating);
			image = itemView.findViewById(R.id.image);
		}
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType)
	{
		View v = LayoutInflater.from(viewGroup.getContext()).inflate(rowLayout, viewGroup, false);

		return new ViewHolder(v);
	}

	@Override
	public void onBindViewHolder(ViewHolder viewHolder, int position)
	{
	}

	@Override
	public int getItemCount()
	{
		return shows.size();
	}


}
