package com.rivas.testandroiddeveloper

import android.app.Application
import android.content.Context
import com.rivas.testandroiddeveloper.di.AppComponent
import com.rivas.testandroiddeveloper.di.DaggerAppComponent
import com.rivas.testandroiddeveloper.services.ServiceSaveLocation
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

class AndroidApp: Application(), HasAndroidInjector {
    companion object {
        lateinit var daggerAppComponent: AppComponent
        lateinit var appContext: Context
    }

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>

    override fun onCreate() {
        super.onCreate()

        appContext = this
        daggerAppComponent = DaggerAppComponent.builder()
            .application(this)
            .context(this)
            .build()
        daggerAppComponent.inject(this)

    }

    override fun androidInjector(): AndroidInjector<Any> = dispatchingAndroidInjector

}