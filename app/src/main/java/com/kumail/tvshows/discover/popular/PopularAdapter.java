package com.kumail.tvshows.discover.popular;

import android.content.Context;
import android.content.Intent;
import android.graphics.PointF;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kumail.tvshows.R;
import com.kumail.tvshows.db.entity.PopularEntity;
import com.kumail.tvshows.showdetails.ExpandedShowActivity;
import com.kumail.tvshows.tmdb.ShowDetailsResponse;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Locale;

import jp.wasabeef.picasso.transformations.gpu.VignetteFilterTransformation;
import timber.log.Timber;


/**
 * Created by kumail on 11/11/2017.
 */

public class PopularAdapter extends RecyclerView.Adapter<PopularAdapter.ViewHolder>
{
	private List<ShowDetailsResponse> showResponse;
	private String imgUrl;
	private Context mContext;
	//	private List<String> imgUrls;
	private List<PopularEntity> popularShows;
//	private final ShowClickListener showClickListener;


	public PopularAdapter(List<PopularEntity> popularShows, Context context)
	{
		this.popularShows = popularShows;
//		imgUrls = iurls;
//		showClickListener = scl;
		mContext = context;
	}

	public static class ViewHolder extends RecyclerView.ViewHolder
	{
		TextView titleText;
		TextView ratingText;
		TextView yearText;
		ImageView backgroundImage;

		public ViewHolder(View itemView)
		{
			super(itemView);

			titleText = itemView.findViewById(R.id.text_title);
			ratingText = itemView.findViewById(R.id.text_rating);
			yearText = itemView.findViewById(R.id.text_year);
			backgroundImage = itemView.findViewById(R.id.image_background);
		}
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
	{
		View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_popular_card, parent, false);

		return new ViewHolder(v);
	}

	@Override
	public void onBindViewHolder(final ViewHolder viewHolder, int position)
	{
		PopularEntity pe = popularShows.get(position);
		String url = pe.getBackdropUrl();

		viewHolder.titleText.setText(pe.getName());
		viewHolder.ratingText.setText(String.format(Locale.ENGLISH, "%.1f", pe.getVoteAverage()));
		viewHolder.yearText.setText(String.format(Locale.ENGLISH, "%s", pe.getFirstAirDate().substring(0,4)));

		Picasso.with(mContext)

				.load(url)
				.fit()
				.centerCrop()
				.transform(new VignetteFilterTransformation(
						mContext, new PointF(0.5f, 0.5f),
						new float[] { 0.0f, 0.0f, 0.0f }, 0.0f, 0.9f))
				.into(viewHolder.backgroundImage);

		ViewCompat.setTransitionName(viewHolder.backgroundImage, pe.getName());

		viewHolder.itemView.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				Intent i = new Intent(mContext, ExpandedShowActivity.class);

				i.putExtra("EXTRA_SHOW_TITLE", pe.getName());
				i.putExtra("EXTRA_POSTER_URL", pe.getPosterUrl());
				i.putExtra("EXTRA_SHOW_TMDB_ID", pe.getTmdbId());
				mContext.startActivity(i);

			}
		});
	}

	@Override
	public int getItemCount()
	{
		Timber.d(String.valueOf(popularShows.size()));
		return popularShows.size();
	}

	public void addItems(List<PopularEntity> lpe) {
		this.popularShows = lpe;
		Timber.d("addItems");
		notifyDataSetChanged();
	}
}
