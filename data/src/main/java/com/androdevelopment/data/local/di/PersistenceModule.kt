package com.androdevelopment.data.local.di

import android.content.Context
import androidx.room.Room
import com.androdevelopment.data.local.database.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class PersistenceModule {

    @Provides
    fun provideAppDatabase(
        @ApplicationContext context: Context
    ):AppDatabase = Room.databaseBuilder(
        context,
        AppDatabase::class.java,
        "meals-database"
    ).build()

    @Provides
    fun provideMealsDao(
        appDatabase: AppDatabase
    ) = appDatabase.mealsDao()


}