package com.androdevelopment.data.local.di

import com.androdevelopment.data.local.source.LocalMealsDataSourceImpl
import com.androdevelopment.data.repository.source.local.LocalMealsDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class LocalDataSourceModule {

    @Binds
    abstract fun bindLocalMealsDataSource(
        localMealsDataSourceImpl: LocalMealsDataSourceImpl
    ): LocalMealsDataSource

}