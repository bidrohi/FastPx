package com.bidyut.app.fastpx.di;

import android.content.Context;

import com.bidyut.app.fastpx.data.PxPhotoRepository;
import com.bidyut.app.fastpx.service.PxService;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {PxServiceModule.class})
public interface ApplicationComponent {
    Context context();

    PxService service();

    PxPhotoRepository repository();
}
