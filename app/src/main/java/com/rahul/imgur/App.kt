package com.rahul.imgur

import androidx.multidex.MultiDexApplication
import com.rahul.imgur.di.databaseModule
import com.rahul.imgur.di.networkModule
import com.rahul.imgur.di.repoModule
import com.rahul.imgur.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class App : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            //inject Android context
            androidContext(applicationContext)
            // use Android logger - Level.INFO by default
            androidLogger()
            koin.loadModules(listOf(databaseModule, networkModule, viewModelModule, repoModule))
            koin.createRootScope()
        }
    }
}