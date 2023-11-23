package com.rahul.imgur.di

import android.content.Context
import androidx.room.Room
import com.rahul.imgur.data.local.AppDatabase
import com.rahul.imgur.data.remote.ApiList
import kotlinx.coroutines.Dispatchers
import okhttp3.ConnectionSpec
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import kotlin.coroutines.CoroutineContext

fun getLogInterceptor(): HttpLoggingInterceptor {
    return HttpLoggingInterceptor().apply {
        if (com.rahul.imgur.BuildConfig.DEBUG) {
            setLevel(HttpLoggingInterceptor.Level.BODY)
        } else {
            setLevel(HttpLoggingInterceptor.Level.NONE)
        }
    }

}

fun returnRetrofit(interceptor: HttpLoggingInterceptor): Retrofit {
    val client = OkHttpClient.Builder()
        .connectionSpecs(Arrays.asList(ConnectionSpec.COMPATIBLE_TLS))
        .addInterceptor(interceptor)
        .build()

    return Retrofit.Builder().baseUrl(ApiList.BASE_PATH)
        .addConverterFactory(GsonConverterFactory.create())
        .client(client).build()
}

fun getApi(retrofit: Retrofit): ApiList {
    return retrofit.create(ApiList::class.java)
}


fun getDb(context: Context): AppDatabase {
    return synchronized(context) {
        Room.databaseBuilder(
            context,
            AppDatabase::class.java, "database-images"
        ).build()
    }
}

fun getCoroutineContext(): CoroutineContext {
    return Dispatchers.IO
}
