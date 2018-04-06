package com.bferrari.mvvmsample.injection

import android.app.Application
import com.bferrari.mvvmsample.AppApplication
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [AndroidInjectionModule::class,
                        ApiModule::class,
                        DataModule::class,
                        AppModule::class,
                        ViewModelModule::class,
                        ActivityModule::class])
interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    fun inject(app: AppApplication)
}