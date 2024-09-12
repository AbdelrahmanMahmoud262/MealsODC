package com.androdevelopment.mealsapp.di

import com.androdevelopment.data.repository.repository.MealsRepositoryImpl
import com.androdevelopment.data.repository.source.remote.RemoteMealsDataSource
import com.androdevelopment.domain.repository.MealsRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

//    @Provides
//    fun provideMealsRepository(
//        remoteDataSource: RemoteMealsDataSource
//    ): MealsRepository = MealsRepositoryImpl(remoteDataSource)


    @Binds
    abstract fun bindMealsRepository(
        mealsRepositoryImpl: MealsRepositoryImpl
    ): MealsRepository
}