package com.androdevelopment.domain.repository

import com.androdevelopment.domain.entity.meals.MealCategoryEntity
import com.androdevelopment.domain.entity.meals.MealEntity
import kotlinx.coroutines.flow.Flow

interface MealsRepository {

    fun searchMeals(query: String): Flow<List<MealEntity>>

    fun getMealsCategories(): Flow<List<MealCategoryEntity>>

    fun getMealsByCategory(category: String): Flow<List<MealEntity>>

    fun getMealById(id: String): Flow<MealEntity>

    fun getRandomMeal(): Flow<MealEntity>

}