package com.kumail.tvshows.discover.trending;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.GsonBuilder;
import com.kumail.tvshows.ApiUtils;
import com.kumail.tvshows.R;
import com.kumail.tvshows.TraktService;
import com.kumail.tvshows.discover.trending.data.RatingsApiResponse;
import com.kumail.tvshows.discover.trending.data.ShowExtendedResponse;
import com.kumail.tvshows.discover.trending.data.TrendingResponse;

import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by kumail on 11/11/2017.
 */

public class TrendingAdapter extends RecyclerView.Adapter<TrendingAdapter.ViewHolder>
{
	private List<ShowExtendedResponse> showResponse;
	private TraktService mService;
	private int rowLayout;

	public TrendingAdapter(List<ShowExtendedResponse> lser, int layout)
	{
		showResponse = lser;
		rowLayout = layout;
	}

	public static class ViewHolder extends RecyclerView.ViewHolder
	{
		TextView title;
		TextView rating;
		TextView year;
		ImageView image;

		public ViewHolder(View itemView)
		{
			super(itemView);

			title = itemView.findViewById(R.id.title);
			rating = itemView.findViewById(R.id.rating);
			year = itemView.findViewById(R.id.year);
			image = itemView.findViewById(R.id.image);
		}
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType)
	{
		View v = LayoutInflater.from(viewGroup.getContext()).inflate(rowLayout, viewGroup, false);
		mService = ApiUtils.getTraktService();

		return new ViewHolder(v);
	}

	@Override
	public void onBindViewHolder(TrendingAdapter.ViewHolder holder, int position)
	{
		final ShowExtendedResponse ser = showResponse.get(position);

		holder.title.setText(ser.getTitle());
		holder.rating.setText(String.format(Locale.ENGLISH, "%.1f", ser.getRating()));
		holder.year.setText(String.format(Locale.ENGLISH, "%d", ser.getYear()));
	}

	@Override
	public int getItemCount()
	{
		return showResponse.size();
	}

	public void updateTrendingShows(List<ShowExtendedResponse> ltar)
	{
		showResponse = ltar;
		notifyDataSetChanged();
	}

}
