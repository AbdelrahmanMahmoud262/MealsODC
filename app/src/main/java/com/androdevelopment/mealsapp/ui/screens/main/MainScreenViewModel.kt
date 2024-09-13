package com.androdevelopment.mealsapp.ui.screens.main

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.androdevelopment.domain.entity.Result
import com.androdevelopment.domain.usecase.GetMealsByCategoryUseCase
import com.androdevelopment.domain.usecase.GetMealsCategoriesUseCase
import com.androdevelopment.domain.usecase.GetRandomMeanUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    private val getMealsByCategoryUseCase: GetMealsByCategoryUseCase,
    private val getMealsCategoriesUseCase: GetMealsCategoriesUseCase,
    private val getRandomMeanUseCase: GetRandomMeanUseCase,
) : ViewModel() {

    var state by mutableStateOf(MainScreenState())
        private set

    init {
        getCategories()

        repeat(10) {
            getRandomMeal()
        }
    }

    private fun getCategories() = viewModelScope.launch {
        loading(true)
        getMealsCategoriesUseCase.invoke(GetMealsCategoriesUseCase.Request).collect { result ->
            state = when (result) {
                is Result.Error -> {
                    Log.e("MainScreenViewModel", "getCategories: ${result.exception.message}")
                    state.copy(
                        isLoading = false,
                        errorMessage = result.exception.message
                    )
                }

                is Result.Success -> {
                    state.copy(
                        isLoading = false,
                        categories = result.data.mealCategories
                    )
                }
            }

            if (state.categories.isNotEmpty()) {
                getMealsByCategory(state.categories.first().categoryName)
            }
        }
    }

    private fun getMealsByCategory(category: String) = viewModelScope.launch {
        loading(true)
        getMealsByCategoryUseCase.invoke(GetMealsByCategoryUseCase.Request(category))
            .collect { result ->
                state = when (result) {
                    is Result.Error -> {
                        Log.e(
                            "MainScreenViewModel",
                            "getMealsByCategory: ${result.exception.message}"
                        )
                        state.copy(
                            isLoading = false,
                            errorMessage = result.exception.message
                        )
                    }

                    is Result.Success -> {
                        Log.e(
                            "MainScreenViewModel",
                            "getMealsByCategory: ${result.data.meals.map { it.category }}"
                        )
                        state.copy(
                            isLoading = false,
                            meals = result.data.meals,
                        )
                    }
                }
                cancel()
            }
    }

    private fun getRandomMeal() = viewModelScope.launch {
        loading(true)
        getRandomMeanUseCase.invoke(GetRandomMeanUseCase.Request)
            .collect { result ->
                state = when (result) {
                    is Result.Error -> {
                        Log.e("MainScreenViewModel", "getRandomMeal: ${result.exception.message}")
                        state.copy(
                            isLoading = false,
                            errorMessage = result.exception.message
                        )
                    }

                    is Result.Success -> {
                        val randomMeals = state.randomMeals.toMutableList()
                        randomMeals.add(0, result.data.meal)
                        state.copy(
                            isLoading = false,
                            randomMeals = randomMeals
                        )
                    }
                }
            }
    }

    fun loading(loading: Boolean) = run { state = state.copy(isLoading = loading) }

    fun onEvent(event: MainScreenUiEvents) {
        when (event) {
            is MainScreenUiEvents.OnCategoryClick -> {
                state = state.copy(
                    selectedCategory = event.category
                )
                getMealsByCategory(event.category)
            }

            is MainScreenUiEvents.OnMealClick -> {
                // TODO: Navigate
            }
        }
    }
}