package com.example.database.di

import android.content.Context
import com.example.database.LocalDataSource
import com.example.database.PostsRoomDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideCitiesDatabaseDao(
        @ApplicationContext appContext: Context
    ): LocalDataSource {

      return PostsRoomDatabase
            .getInstance(appContext)
            .postsLocalDataSource
    }
}