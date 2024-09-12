package com.androdevelopment.data.remote.networking.meal.models


data class MealCategoryResponse(
    val categories: List<MealCategoryApiModel> = listOf()
)
data class MealCategoryApiModel(
    val idCategory: String? = null,
    val strCategory: String? = null,
    val strCategoryDescription: String? = null,
    val strCategoryThumb: String? = null,
)
