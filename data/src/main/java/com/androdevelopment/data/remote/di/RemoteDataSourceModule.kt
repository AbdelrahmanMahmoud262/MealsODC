package com.androdevelopment.data.remote.di

import com.androdevelopment.data.remote.source.RemoteMealsDataSourceImpl
import com.androdevelopment.data.repository.source.remote.RemoteMealsDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RemoteDataSourceModule {

    @Binds
    abstract fun bindMealsDataSource(mealsDataSourceImpl: RemoteMealsDataSourceImpl): RemoteMealsDataSource

}