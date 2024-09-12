package com.androdevelopment.mealsapp.ui.screens.main

import com.androdevelopment.domain.entity.meals.MealCategoryEntity
import com.androdevelopment.domain.entity.meals.MealEntity

data class MainScreenState(
    val isLoading: Boolean = false,
    val categories:List<MealCategoryEntity> = listOf(),
    val meals: List<MealEntity> = listOf(),
    val randomMeals:List<MealEntity> = listOf(),
    val selectedCategory: String? = null,
    val errorMessage: String? = null,
)
