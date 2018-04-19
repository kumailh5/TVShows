package com.kumail.tvshows.discover.popular;

import android.content.Context;
import android.content.Intent;
import android.graphics.PointF;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kumail.tvshows.R;
import com.kumail.tvshows.showdetails.ExpandedShowActivity;
import com.kumail.tvshows.tmdb.ShowDetailsResponse;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Locale;

import jp.wasabeef.picasso.transformations.gpu.VignetteFilterTransformation;


/**
 * Created by kumail on 11/11/2017.
 */

public class PopularAdapter extends RecyclerView.Adapter<PopularAdapter.ViewHolder>
{
	private List<ShowDetailsResponse> showResponses;
	private ShowDetailsResponse showResponse;
	private String imgUrl;
	private Context mContext;
	private List<String> imgUrls;


	public PopularAdapter(List <ShowDetailsResponse> lsdr, List <String> iurls, Context context)
	{
		showResponses = lsdr;
		imgUrls = iurls;
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

			title = itemView.findViewById(R.id.text_title);
			rating = itemView.findViewById(R.id.text_rating);
			year = itemView.findViewById(R.id.text_year);
			image = itemView.findViewById(R.id.image_background);
		}
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType)
	{
		View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_popular_card, viewGroup, false);

		return new ViewHolder(v);
	}

	@Override
	public void onBindViewHolder(PopularAdapter.ViewHolder holder, int position)
	{
		final ShowDetailsResponse sdr = showResponses.get(position);
		final String url = imgUrls.get(position);


		holder.title.setText(sdr.getName());
		holder.rating.setText(String.format(Locale.ENGLISH, "%.1f", sdr.getVoteAverage()));
		holder.year.setText(String.format(Locale.ENGLISH, "%s", sdr.getFirstAirDate().substring(0,4)));

		Picasso.with(mContext)
				.load(url)
				.fit()
				.centerCrop()
				.transform(new VignetteFilterTransformation(
						mContext, new PointF(0.5f, 0.f),
						new float[] { 0.0f, 0.0f, 0.0f }, 0.0f, 0.9f))
				.into(holder.image);

		holder.itemView.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				Intent i = new Intent(mContext, ExpandedShowActivity.class);
				i.putExtra("EXTRA_SHOW_TITLE", sdr.getName());
				i.putExtra("EXTRA_POSTER_IMG", sdr.getPosterPath());
				i.putExtra("EXTRA_IMG_URL", url);
//				RepoDatabase
//						.getInstance(mContext)
//						.getRepoDao()
//						.insert(new Repo(1, "Cool Repo Name", "url"));

				mContext.startActivity(i);

			}
		});
	}

	@Override
	public int getItemCount()
	{
		return showResponses.size();
	}


}
