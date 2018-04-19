package com.kumail.tvshows.showdetails.cast;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kumail.tvshows.R;
import com.kumail.tvshows.db.entity.CastEntity;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class CastAdapter extends RecyclerView.Adapter<CastAdapter.ViewHolder>
{
	private List<CastEntity> casts;
	private Context mContext;
	public CastAdapter(List<CastEntity> casts, Context mContext){
		this.casts = casts;
		this.mContext = mContext;
	}

	public static class ViewHolder extends RecyclerView.ViewHolder
	{
		TextView tvName;
		TextView tvCharacter;
//		ImageView ivProfile;
		CircleImageView ivProfile;


		public ViewHolder(View itemView)
		{
			super(itemView);

			tvName = itemView.findViewById(R.id.tv_name);
			tvCharacter = itemView.findViewById(R.id.tv_character);
			ivProfile = itemView.findViewById(R.id.iv_profile);
		}
	}

	@NonNull
	@Override
	public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
	{
		View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cast_card, parent, false);

		return new ViewHolder(v);
	}

	@Override
	public void onBindViewHolder(@NonNull CastAdapter.ViewHolder holder, int position)
	{
		CastEntity ce = casts.get(position);
		holder.tvName.setText(ce.getName());
		holder.tvCharacter.setText(ce.getCharacter());

		Picasso.with(mContext)
				.load(ce.getProfileUrl())
				.fit()
				.centerCrop()
				.into(holder.ivProfile);

	}

	@Override
	public int getItemCount()
	{
		return casts.size();
	}
}
