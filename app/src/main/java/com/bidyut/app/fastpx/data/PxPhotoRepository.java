package com.bidyut.app.fastpx.data;

import android.support.annotation.NonNull;

import com.bidyut.app.fastpx.service.PxService;
import com.bidyut.app.fastpx.service.SearchResults;

import javax.inject.Inject;

import rx.Observable;
import rx.schedulers.Schedulers;

public class PxPhotoRepository {
    @NonNull
    private final PxService mService;

    @Inject
    public PxPhotoRepository(@NonNull PxService service) {
        mService = service;
    }

    @NonNull
    public Observable<SearchResults> getItems(@NonNull String term) {
        return mService.searchPhotos(term)
                .subscribeOn(Schedulers.io());
    }
}
