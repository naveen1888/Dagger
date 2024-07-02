package com.rawat.dagger2.mvvm.dagger.di

import android.content.Context
import androidx.room.Room
import com.rawat.dagger2.mvvm.dagger.ApplicationContext
import com.rawat.dagger2.mvvm.dagger.db.FakerDB
import com.rawat.dagger2.mvvm.dagger.retrofit.FakerAPI
import com.rawat.dagger2.mvvm.dagger.util.Constants
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class ApplicationModule {

    @Singleton
    @Provides
    fun providesRetrofit(): Retrofit {
        return Retrofit.Builder().baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun providesFakerAPI(retrofit: Retrofit): FakerAPI {
        return retrofit.create(FakerAPI::class.java)
    }

    @Singleton
    @Provides
    fun provideFakerDB(@ApplicationContext context : Context) : FakerDB {
        return Room.databaseBuilder(context, FakerDB::class.java, "FakerDB").build()
    }

}