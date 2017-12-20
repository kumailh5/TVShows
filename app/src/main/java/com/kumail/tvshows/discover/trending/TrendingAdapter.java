package com.kumail.tvshows.discover.trending;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kumail.tvshows.R;
import com.kumail.tvshows.discover.trending.data.ShowExtendedResponse;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


/**
 * Created by kumail on 11/11/2017.
 */

public class TrendingAdapter extends RecyclerView.Adapter<TrendingAdapter.ViewHolder>
{
	private List<ShowExtendedResponse> showResponse;
	private String imgUrl;
	private Context mContext;
	private List<String> imgUrls = new ArrayList<>();


	public TrendingAdapter(List<ShowExtendedResponse> lser, Context context)
	{
		showResponse = lser;
		mContext = context;
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
			image = itemView.findViewById(R.id.bg_image);
		}
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType)
	{
		View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_trending_card, viewGroup, false);

		return new ViewHolder(v);
	}

	@Override
	public void onBindViewHolder(TrendingAdapter.ViewHolder holder, int position)
	{
		final ShowExtendedResponse ser = showResponse.get(position);
//		final String url = imgUrls.get(0);

		holder.title.setText(ser.getTitle());
		holder.rating.setText(String.format(Locale.ENGLISH, "%.1f", ser.getRating()));
		holder.year.setText(String.format(Locale.ENGLISH, "%d", ser.getYear()));

//		Picasso.with(mContext)
//				.load(url)
//				.fit()
//				.centerCrop()
//				.into(holder.image);
	}

	@Override
	public int getItemCount()
	{
		return showResponse.size();
	}

	public void updateTrendingShows(List<ShowExtendedResponse> lser)
	{
		showResponse = lser;
//		for(int i =0; i< iurls.size(); i++){
//			Log.d("TrendingAdapterls", iurls.get(i));
//		}
		notifyDataSetChanged();
	}

	public void updateImageUrl(List<String> urls)
	{
//		imgUrl = url;
		imgUrls = urls;
//		List<String> ls = new ArrayList<>();
//		ls.add(url);
		for(int i =0; i< imgUrls.size(); i++){
			Log.d("TrendingAdapterls", imgUrls.get(i));
		}
		notifyDataSetChanged();
	}

}
