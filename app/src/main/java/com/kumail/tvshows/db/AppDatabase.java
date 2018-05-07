package com.kumail.tvshows.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.kumail.tvshows.db.dao.AccessTokenDao;
import com.kumail.tvshows.db.dao.CastDao;
import com.kumail.tvshows.db.dao.EpisodesDao;
import com.kumail.tvshows.db.dao.InfoDao;
import com.kumail.tvshows.db.dao.PopularDao;
import com.kumail.tvshows.db.dao.ShowDao;
import com.kumail.tvshows.db.dao.TrendingDao;
import com.kumail.tvshows.db.dao.UserDao;
import com.kumail.tvshows.db.dao.WatchedDao;
import com.kumail.tvshows.db.entity.CastEntity;
import com.kumail.tvshows.db.entity.EpisodesEntity;
import com.kumail.tvshows.db.entity.InfoEntity;
import com.kumail.tvshows.db.entity.PopularEntity;
import com.kumail.tvshows.db.entity.ShowEntity;
import com.kumail.tvshows.db.entity.TrendingEntity;
import com.kumail.tvshows.db.entity.UserEntity;
import com.kumail.tvshows.db.entity.WatchedEntity;
import com.kumail.tvshows.trakt.auth.AccessTokenResponse;

/**
 * Created by kumail on 29/03/2018.
 */

@Database(entities = {AccessTokenResponse.class,
        TrendingEntity.class,
        PopularEntity.class,
        UserEntity.class,
        WatchedEntity.class,
        InfoEntity.class,
        CastEntity.class,
        EpisodesEntity.class,
        ShowEntity.class},
        version = 1)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase INSTANCE;

    public abstract ShowDao showDao();

    public abstract AccessTokenDao accessTokenDao();

    public abstract TrendingDao trendingDao();

    public abstract PopularDao popularDao();

    public abstract UserDao userDao();

    public abstract WatchedDao watchedDao();

    public abstract InfoDao infoDao();

    public abstract CastDao castDao();

    public abstract EpisodesDao episodesDao();

    public static AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(
                    context.getApplicationContext(),
                    AppDatabase.class,
                    "AppDatabase")
                    .build();
//					Room.inMemoryDatabaseBuilder(context.getApplicationContext(), AppDatabase.class)
//							// To simplify the codelab, allow queries on the main thread.
//							// Don't do this on a real app! See PersistenceBasicSample for an example.
////                    .allowMainThreadQueries()
//							.build();
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }

//	@VisibleForTesting
//	public static final String DATABASE_NAME = "basic-sample-db";
//
//	public abstract ShowDao showDao();
//
//	private final MutableLiveData<Boolean> mIsDatabaseCreated = new MutableLiveData<>();
//
//	public static AppDatabase getInstance(final Context context, final AppExecutors executors) {
//		if (sInstance == null) {
//			synchronized (AppDatabase.class) {
//				if (sInstance == null) {
//					sInstance = buildDatabase(context.getApplicationContext(), executors);
//					sInstance.updateDatabaseCreated(context.getApplicationContext());
//				}
//			}
//		}
//		return sInstance;
//	}
//
//	/**
//	 * Build the database. {@link RoomDatabase.Builder#build()} only sets up the database configuration and
//	 * creates a new instance of the database.
//	 * The SQLite database is only created when it's accessed for the first time.
//	 */
//	private static AppDatabase buildDatabase(final Context appContext,
//											 final AppExecutors executors) {
//		return Room.databaseBuilder(appContext, AppDatabase.class, DATABASE_NAME)
//				.addCallback(new RoomDatabase.Callback() {
//					@Override
//					public void onCreate(@NonNull SupportSQLiteDatabase db) {
//						super.onCreate(db);
//						executors.diskIO().execute(() -> {
//							// Add a delay to simulate a long-running operation
//							addDelay();
//							// Generate the data for pre-population
//							AppDatabase database = AppDatabase.getInstance(appContext, executors);
//							List<ShowEntity> products = DataGenerator.generateProducts();
//
////							insertData(database, products);
//							// notify that the database was created and it's ready to be used
//							database.setDatabaseCreated();
//						});
//					}
//				}).build();
//	}
//
//	/**
//	 * Check whether the database already exists and expose it via {@link #getDatabaseCreated()}
//	 */
//	private void updateDatabaseCreated(final Context context) {
//		if (context.getDatabasePath(DATABASE_NAME).exists()) {
//			setDatabaseCreated();
//		}
//	}
//
//	private void setDatabaseCreated(){
//		mIsDatabaseCreated.postValue(true);
//	}
//
//	private static void insertData(final AppDatabase database, final List<ShowEntity> shows) {
//		database.runInTransaction(() -> {
//			database.showDao().insertAll(shows);
//			database.showDao().insertAll(shows);
//		});
//	}
//
//	private static void addDelay() {
//		try {
//			Thread.sleep(4000);
//		} catch (InterruptedException ignored) {
//		}
//	}
//
//	public LiveData<Boolean> getDatabaseCreated() {
//		return mIsDatabaseCreated;
//	}
}
