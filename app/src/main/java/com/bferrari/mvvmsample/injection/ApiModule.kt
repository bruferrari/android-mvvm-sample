package com.bferrari.mvvmsample.injection

import android.content.Context
import android.net.ConnectivityManager
import com.bferrari.mvvmsample.BuildConfig
import com.bferrari.mvvmsample.exceptions.NoNetworkException
import com.bferrari.mvvmsample.extensions.isConnected
import com.bferrari.mvvmsample.service.remote.AppApi
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.experimental.CoroutineCallAdapterFactory
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
open class ApiModule {

    open fun getOkHttpBuilder(): OkHttpClient.Builder = OkHttpClient.Builder()

    @Provides
    @Singleton
    fun providesOkHttpClient(@Named("ApplicationContext") context: Context): OkHttpClient {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val logging = HttpLoggingInterceptor()

        val builder = getOkHttpBuilder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .addInterceptor { chain ->
                    val original = chain.request()

                    if (!connectivityManager.isConnected) {
                        throw NoNetworkException
                    }

                    val requestBuilder = original.newBuilder()

                    // Store token if needed in sharedPreferences

                    val response = chain.proceed(requestBuilder.method(original.method(), original.body()).build())

                    response
                }

        if (BuildConfig.DEBUG) {
            logging.level = HttpLoggingInterceptor.Level.BODY
            builder.addInterceptor(logging)
        }

        return builder.build()
    }

    @Provides
    @Singleton
    fun providesAppApi(okHttpClient: OkHttpClient): AppApi
            = Retrofit.Builder()
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BuildConfig.API_URL)
                .client(okHttpClient)
                .build()
                .create(AppApi::class.java)

    @Provides
    @Singleton
    fun providesGson(): Gson = GsonBuilder().create()

}