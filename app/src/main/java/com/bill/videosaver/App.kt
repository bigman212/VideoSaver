package com.bill.videosaver

import androidx.viewbinding.BuildConfig
import android.app.Application
import com.bill.videosaver.data.network.NetworkModule
import com.bill.videosaver.home.HomeModule
import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.android.java.KoinAndroidApplication
import org.koin.core.context.GlobalContext.startKoin
import org.koin.core.logger.Level
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module
import timber.log.Timber

class App : Application() {
    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        startKoin {
            androidLogger(level = Level.ERROR)
            androidContext(this@App)
            modules(AppModule.create())
        }
    }
}

@Suppress("RemoveExplicitTypeArguments")
object AppModule {
    fun create() = module {
        single<Cicerone<Router>> { Cicerone.create() }
        single<Router> { get<Cicerone<Router>>().router }
        single<NavigatorHolder> { get<Cicerone<Router>>().getNavigatorHolder() }

        includes(HomeModule.create(), NetworkModule.create())
    }
}