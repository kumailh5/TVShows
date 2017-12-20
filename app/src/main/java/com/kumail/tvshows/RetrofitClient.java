package com.kumail.tvshows;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by kumail on 12/11/2017.
 */

public class RetrofitClient
{

	private static Retrofit retrofit = null;
	private static final String TraktClientID = "5d98be13eb1a05de1416f2dfacbaab5d7d8fad905a1ded33a48eb201e345a976";
	private static final String TmdbApiKey= "0145e269d6f9647893ca62fcde80871e";

	public static Retrofit getTraktClient(String baseUrl)
	{
		OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
		httpClient.addInterceptor(new Interceptor()
		{
			@Override
			public Response intercept(Interceptor.Chain chain) throws IOException
			{
				Request original = chain.request();

				Request request = original.newBuilder()
						.header("trakt-api-key", TraktClientID)
						.header("trakt-api-version", "2")
						.header("Content-type", "application/json")
						.method(original.method(), original.body())
						.build();

				return chain.proceed(request);
			}
		});
		OkHttpClient client = httpClient.build();
		retrofit = new Retrofit.Builder()
				.baseUrl(baseUrl)
				.addConverterFactory(GsonConverterFactory.create())
				.client(client)
				.build();
//		if (retrofit == null)
//		{
//
//		}

		return retrofit;
	}

	public static Retrofit getTmdbClient(String baseUrl)
	{
		retrofit = new Retrofit.Builder()
				.baseUrl(baseUrl)
				.addConverterFactory(GsonConverterFactory.create())
				.build();
//		if (retrofit == null)
//		{
//
//		}

		return retrofit;
	}

}
