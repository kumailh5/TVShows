package com.kumail.tvshows.discover.trending;

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

import com.kumail.tvshows.db.entity.TrendingEntity;
import com.kumail.tvshows.showdetails.ExpandedShowActivity;
import com.kumail.tvshows.R;
import com.kumail.tvshows.tmdb.ShowDetailsResponse;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Locale;

import jp.wasabeef.picasso.transformations.gpu.VignetteFilterTransformation;
import timber.log.Timber;


/**
 * Created by kumail on 11/11/2017.
 */

public class TrendingAdapter extends RecyclerView.Adapter<TrendingAdapter.ViewHolder>
{
	private List<ShowDetailsResponse> showResponse;
	private String imgUrl;
	private Context mContext;
//	private List<String> imgUrls;
	private List<TrendingEntity> trendingShows;
//	private final ShowClickListener showClickListener;


	public TrendingAdapter(List<TrendingEntity> trendingShows, Context context)
	{
		this.trendingShows = trendingShows;
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
		View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_trending_card, parent, false);

		return new ViewHolder(v);
	}

	@Override
	public void onBindViewHolder(final ViewHolder viewHolder, int position)
	{
		TrendingEntity te = trendingShows.get(position);
		String url = te.getBackdropUrl();

		viewHolder.titleText.setText(te.getName());
		viewHolder.ratingText.setText(String.format(Locale.ENGLISH, "%.1f", te.getVoteAverage()));
		viewHolder.yearText.setText(String.format(Locale.ENGLISH, "%s", te.getFirstAirDate().substring(0,4)));

		Picasso.with(mContext)

				.load(url)
				.fit()
				.centerCrop()
				.transform(new VignetteFilterTransformation(
						mContext, new PointF(0.5f, 0.5f),
						new float[] { 0.0f, 0.0f, 0.0f }, 0.0f, 0.9f))
				.into(viewHolder.backgroundImage);

		ViewCompat.setTransitionName(viewHolder.backgroundImage, te.getName());

		viewHolder.itemView.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				Intent i = new Intent(mContext, ExpandedShowActivity.class);

				i.putExtra("EXTRA_SHOW_TITLE", te.getName());
				i.putExtra("EXTRA_POSTER_URL", te.getPosterUrl());
				i.putExtra("EXTRA_SHOW_TMDB_ID", te.getTmdbId());
//				i.putExtra("EXTRA_IMG_URL", url);
				mContext.startActivity(i);

//				View sharedView = holder.backgroundImage;
//				String transitionName = mContext.getString(R.string.card_trans);
//
//				ActivityOptions transitionActivityOptions = ActivityOptions.makeSceneTransitionAnimation((Activity) mContext, sharedView, transitionName);
//				mContext.startActivity(i, transitionActivityOptions.toBundle());

//				showClickListener.onShowClick(holder.getAdapterPosition(), sdr, holder.backgroundImage);

//				Intent intent = new Intent(mContext, ExpandedShowActivity.class);
//				ActivityOptionsCompat options = ActivityOptionsCompat.
//						makeSceneTransitionAnimation(mContext,
//								holder.backgroundImage,
//								ViewCompat.getTransitionName(holder.backgroundImage));
//				mContext.startActivity(intent, options.toBundle());

//				ExpShowFrag.
//				ExpShowFrag expShowFrag = ExpShowFrag.newInstance();
//				getActivity().getFragmentManager()
//						.beginTransaction()
//						.addSharedElement(holder.backgroundImage, ViewCompat.getTransitionName(holder.backgroundImage))
//						.addToBackStack(TAG)
//						.replace(R.id.content, simpleFragmentB)
//						.commit();
			}
		});
	}

	@Override
	public int getItemCount()
	{
		Timber.d(String.valueOf(trendingShows.size()));
		return trendingShows.size();
	}

	public void addItems(List<TrendingEntity> lte) {
		this.trendingShows = lte;
		Timber.d("addItems");
		notifyDataSetChanged();
	}
}
