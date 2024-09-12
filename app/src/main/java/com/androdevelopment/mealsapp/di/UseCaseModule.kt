package com.androdevelopment.mealsapp.di

import com.androdevelopment.domain.usecase.UseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers

@Module
@InstallIn(SingletonComponent::class)
class UseCaseModule {

    @Provides
    fun provideUseCaseConfiguration():UseCase.Configuration = UseCase.Configuration(Dispatchers.IO)

}