package com.kumail.tvshows.discover.trending;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.graphics.PointF;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kumail.tvshows.showdetails.ExpShowAct;
import com.kumail.tvshows.R;
import com.kumail.tvshows.tmdb.ShowDetailsResponse;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Locale;

import jp.wasabeef.picasso.transformations.gpu.VignetteFilterTransformation;


/**
 * Created by kumail on 11/11/2017.
 */

public class TrendingAdapter extends RecyclerView.Adapter<TrendingAdapter.ViewHolder>
{
	private List<ShowDetailsResponse> showResponse;
	private String imgUrl;
	private Context mContext;
	private List<String> imgUrls;
//	private final ShowClickListener showClickListener;


	public TrendingAdapter(List<ShowDetailsResponse> lsdr, List<String> iurls, Context context)
	{
		showResponse = lsdr;
		imgUrls = iurls;
//		showClickListener = scl;
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
	public void onBindViewHolder(final TrendingAdapter.ViewHolder holder, int position)
	{
		final ShowDetailsResponse sdr = showResponse.get(position);
		String url = imgUrls.get(position);
		Log.d("TrendingAdapterTst", url);
		Log.d("TrendingAdapterTst", sdr.getName());


		holder.title.setText(sdr.getName());
		holder.rating.setText(String.format(Locale.ENGLISH, "%.1f", sdr.getVoteAverage()));
		holder.year.setText(String.format(Locale.ENGLISH, "%s", sdr.getFirstAirDate().substring(0,4)));

		Picasso.with(mContext)

				.load(url)
				.fit()
				.centerCrop()
				.transform(new VignetteFilterTransformation(
						mContext, new PointF(0.5f, 0.5f),
						new float[] { 0.0f, 0.0f, 0.0f }, 0.0f, 0.9f))
				.into(holder.image);

		ViewCompat.setTransitionName(holder.image, sdr.getName());

		holder.itemView.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				Intent i = new Intent(mContext, ExpShowAct.class);

				View sharedView = holder.image;
				String transitionName = mContext.getString(R.string.card_trans);

				ActivityOptions transitionActivityOptions = ActivityOptions.makeSceneTransitionAnimation((Activity) mContext, sharedView, transitionName);
				mContext.startActivity(i, transitionActivityOptions.toBundle());

//				showClickListener.onShowClick(holder.getAdapterPosition(), sdr, holder.image);

//				Intent intent = new Intent(mContext, ExpShowAct.class);
//				ActivityOptionsCompat options = ActivityOptionsCompat.
//						makeSceneTransitionAnimation(mContext,
//								holder.image,
//								ViewCompat.getTransitionName(holder.image));
//				mContext.startActivity(intent, options.toBundle());

//				ExpShowFrag.
//				ExpShowFrag expShowFrag = ExpShowFrag.newInstance();
//				getActivity().getFragmentManager()
//						.beginTransaction()
//						.addSharedElement(holder.image, ViewCompat.getTransitionName(holder.image))
//						.addToBackStack(TAG)
//						.replace(R.id.content, simpleFragmentB)
//						.commit();
			}
		});
	}

	@Override
	public int getItemCount()
	{
		return imgUrls.size();
	}
}
