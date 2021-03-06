package com.rivas.testandroiddeveloper.di

import android.app.Application
import android.content.Context
import com.rivas.testandroiddeveloper.AndroidApp
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton


@Singleton
@Component(modules = [AndroidInjectionModule::class, ActivityBuilder::class, RetrofitModule::class, RoomModule::class, FragmentBuilder::class, FirebaseModule::class, ServiceModule::class])
interface AppComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(app: Application): Builder
        @BindsInstance
        fun context(context: Context): Builder
        fun build(): AppComponent
    }

    fun inject(app: AndroidApp)

}