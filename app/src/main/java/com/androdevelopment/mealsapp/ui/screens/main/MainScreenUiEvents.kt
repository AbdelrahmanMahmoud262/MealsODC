package com.androdevelopment.mealsapp.ui.screens.main

sealed class MainScreenUiEvents {

    data class OnMealClick(val mealId: String) : MainScreenUiEvents()
    data class OnCategoryClick(val category: String) : MainScreenUiEvents()

}