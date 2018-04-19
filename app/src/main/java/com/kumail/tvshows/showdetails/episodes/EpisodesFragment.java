package com.kumail.tvshows.showdetails.episodes;

import android.arch.lifecycle.Observer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.google.gson.GsonBuilder;
import com.kumail.tvshows.ApiUtils;
import com.kumail.tvshows.R;
import com.kumail.tvshows.db.AppDatabase;
import com.kumail.tvshows.db.ShowViewModel;
import com.kumail.tvshows.db.entity.EpisodesEntity;
import com.kumail.tvshows.showdetails.ExpandedShowActivity;
import com.kumail.tvshows.trakt.TraktService;
import com.kumail.tvshows.trakt.data.EpisodeDetailedResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class EpisodesFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private EpisodesAdapter mAdapter;
    private ShowViewModel mViewModel;
    private AppDatabase appDatabase;
    List<EpisodesEntity> episodes;
    private TraktService mTraktService;
    private ProgressBar progressBar;



    public EpisodesFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTraktService = ApiUtils.getTraktService();
        appDatabase = AppDatabase.getInstance(getActivity());
        AsyncTask.execute(() -> episodes = appDatabase.episodesDao().loadEpisode(((ExpandedShowActivity) getActivity()).getTmdbId()));
//        mViewModel = ViewModelProviders.of(getActivity()).get(ShowViewModel.class);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_episodes, container, false);
        progressBar = view.findViewById(R.id.progress_bar);
        mRecyclerView = view.findViewById(R.id.rv);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setHasFixedSize(true);
        mAdapter = new EpisodesAdapter(episodes, getActivity());

        if (episodes != null) {
            progressBar.setVisibility(View.GONE);
            loadEpisodeDetails(episodes);
            Timber.d("Episodes size: %s", episodes.size());
            mRecyclerView.setAdapter(mAdapter);
        } else {
            progressBar.setVisibility(View.VISIBLE);
            mRecyclerView.setVisibility(View.GONE);
        }
        //        subscribeUiEpisodes();


        return view;
    }

    private void subscribeUiEpisodes() {
        mViewModel.getEpisodes().observe(this, new Observer<List<EpisodesEntity>>() {
            @Override
            public void onChanged(@NonNull final List<EpisodesEntity> episodes) {
                if (episodes != null) {
                    Timber.d("Watched shows isnt null " + episodes.size());
                    mAdapter.addItems(episodes);
                }
            }
        });
    }

    private void loadEpisodeDetails(List<EpisodesEntity> lee) {
        for (int i = 0; i < lee.size(); i++) {
            if (lee.get(i).getType() == 1) {
                Call<EpisodeDetailedResponse> call = mTraktService.getEpisodeDetailed(
                        lee.get(i).getSlug(),
                        lee.get(i).getSeason(),
                        lee.get(i).getNumber());

                Timber.d("UrlED %s", call.request().url().toString());
                int finalI = i;
                call.enqueue(new Callback<EpisodeDetailedResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<EpisodeDetailedResponse> call, Response<EpisodeDetailedResponse> response) {
                        Timber.tag("EDResponse").d(new GsonBuilder().setPrettyPrinting().create().toJson(response));

                        if (response.isSuccessful()) {
                            EpisodeDetailedResponse edr = response.body();
                            assert edr != null;
                            Timber.d("EDR: %d %s %s %s",
                                    edr.getNumber(),
                                    edr.getIds().getTrakt(),
                                    edr.getTitle(),
                                    edr.getOverview());
                            lee.get(finalI).setTitle(edr.getTitle());
                            lee.get(finalI).setOverview(edr.getOverview());
                            progressBar.setVisibility(View.GONE);
                            mRecyclerView.setVisibility(View.VISIBLE);
                        } else {
                            Timber.tag("ED").d(Integer.toString(response.code()));
//                            Timber.tag("ED").d(new GsonBuilder().setPrettyPrinting().create().toJson(response));
                        }
                    }
                    @Override
                    public void onFailure(@NonNull Call<EpisodeDetailedResponse> call, Throwable t) {
//                    progressBar.setVisibility(View.GONE);
                        Timber.d(String.valueOf(t));
                    }
                });
            }

            for (int j = 0; j < lee.size(); j++) {
                Timber.d(String.format("LEE: %d %s %s %s",
                        lee.get(i).getNumber(),
                        lee.get(i).getId(),
                        lee.get(i).getTitle(),
                        lee.get(i).getOverview()));
            }
        }

    }


}
