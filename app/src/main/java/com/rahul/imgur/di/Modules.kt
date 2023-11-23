package com.rahul.imgur.di

import androidx.lifecycle.SavedStateHandle
import com.rahul.imgur.data.repository.ImgurPostsRepository
import com.rahul.imgur.ui.main.MainActivityViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * modules for dependency injection where [single] represents singleton class
 */
var networkModule = module {
    single { getLogInterceptor() }
    single { returnRetrofit(get()) }
    single { getApi(get()) }
}

var databaseModule = module {
    single { getDb(androidApplication()) }
}

var repoModule = module {
    single { getCoroutineContext() }
    single { ImgurPostsRepository(androidApplication(), get(), get(), get()) }
}

var viewModelModule = module {
    viewModel { (state: SavedStateHandle) -> MainActivityViewModel(handle = state, get()) }
}