@file:Suppress("DEPRECATION")

package com.obregon.luas.app.di

import android.content.Context
import com.obregon.luas.data.network.LuasApi
import com.obregon.luas.data.repository.LuasRepository
import com.obregon.luas.data.repository.LuasRepositoryImpl
import com.obregon.luas.ui.TimeHelper
import com.obregon.luas.ui.TimeHelperImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.simplexml.SimpleXmlConverterFactory
import javax.inject.Named
import javax.inject.Singleton


@InstallIn(ApplicationComponent::class)
@Module
object AppModule {

    @Singleton
    @Provides
    fun providesLuasApi(retrofit:Retrofit): LuasApi {
        return retrofit.create(LuasApi::class.java)
    }

    @Singleton
    @Provides
    fun providesLuasRepository(luasApi: LuasApi):LuasRepository{
        return LuasRepositoryImpl(luasApi)
    }

    @Singleton
    @Provides
    fun providesRetrofit(@Named("ApiUrl") apiUrl:String, client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .client(client)
            .baseUrl(apiUrl)
            .addConverterFactory(SimpleXmlConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    @Named("ApiUrl")
    fun providesApiUrl():String{
        return " https://luasforecasts.rpa.ie"
    }

    @Singleton
    @Provides
    fun provideHttpClient(interceptor: Interceptor,cache: Cache): OkHttpClient =
        OkHttpClient
            .Builder().cache(cache)
            .addInterceptor(interceptor)
            .build()


    @Provides
    fun provideInterceptor(): Interceptor {
        return  HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }


    @Provides
    fun provideCache(@ApplicationContext context: Context): Cache = Cache(context.cacheDir, 5 * 1024 * 1024)

    @Singleton
    @Provides
    fun providesTimeHelper(timeHelperImpl: TimeHelperImpl): TimeHelper {
        return timeHelperImpl
    }

}