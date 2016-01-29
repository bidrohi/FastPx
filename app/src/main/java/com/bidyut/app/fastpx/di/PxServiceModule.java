package com.bidyut.app.fastpx.di;

import android.content.Context;
import android.support.annotation.NonNull;

import com.bidyut.app.fastpx.service.PxService;

import dagger.Module;
import dagger.Provides;
import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;
import retrofit2.RxJavaCallAdapterFactory;

@Module
public class PxServiceModule {
    private final Context mContext;

    public PxServiceModule(@NonNull Context context) {
        mContext = context;
    }

    @Provides
    public Context provideContext() {
        return mContext.getApplicationContext();
    }

    @Provides
    @NonNull
    public PxService provideService() {
        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(PxService.API_URL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(PxService.class);
    }
}
