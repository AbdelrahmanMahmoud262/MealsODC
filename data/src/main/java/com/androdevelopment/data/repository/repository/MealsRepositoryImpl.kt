package com.androdevelopment.data.repository.repository

import com.androdevelopment.data.repository.source.local.LocalMealsDataSource
import com.androdevelopment.data.repository.source.remote.RemoteMealsDataSource
import com.androdevelopment.domain.entity.meals.MealCategoryEntity
import com.androdevelopment.domain.entity.meals.MealEntity
import com.androdevelopment.domain.repository.MealsRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject


class MealsRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteMealsDataSource,
    private val localDataSource: LocalMealsDataSource
) : MealsRepository {

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun searchMeals(query: String): Flow<List<MealEntity>> =
        remoteDataSource.searchMeals(query)
            .onEach {
                it.forEach {meal->
                    localDataSource.insertMeal(meal)
                }
            }.flatMapConcat { localDataSource.searchMeals(query) }
            .catch { emitAll(localDataSource.searchMeals(query)) }


    @OptIn(ExperimentalCoroutinesApi::class)
    override fun getMealsCategories(): Flow<List<MealCategoryEntity>> =
        remoteDataSource.getMealsCategories()
            .onEach {
                it.forEach {category->
                    localDataSource.insertCategory(category)
                }
            }.flatMapConcat { localDataSource.getMealsCategories() }
            .catch { emitAll(localDataSource.getMealsCategories()) }

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun getMealsByCategory(category: String): Flow<List<MealEntity>> =
        remoteDataSource.getMealsByCategory(category)
            .onEach {
                it.forEach {meal->
                    localDataSource.insertMeal(meal)
                }
            }.flatMapLatest { localDataSource.getMealsByCategory(category) }
            .catch { emitAll(localDataSource.getMealsByCategory(category)) }

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun getMealById(id: String): Flow<MealEntity> = remoteDataSource.getMealById(id)
        .onEach {
            localDataSource.insertMeal(it)
        }.flatMapConcat { localDataSource.getMealById(id) }
        .catch { localDataSource.getMealById(id) }

    override fun getRandomMeal(): Flow<MealEntity> = remoteDataSource.getRandomMeal()
        .onEach {
            localDataSource.insertMeal(it)
        }
        .catch { emitAll(localDataSource.getRandomMeal()) }
}