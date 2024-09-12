package com.androdevelopment.domain.usecase

import com.androdevelopment.domain.entity.meals.MealEntity
import com.androdevelopment.domain.repository.MealsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SearchMealsUseCase @Inject constructor(
    configuration: Configuration,
    private val mealsRepository: MealsRepository
) :UseCase<SearchMealsUseCase.Request, SearchMealsUseCase.Response>(configuration) {

    override fun process(request: Request): Flow<Response> = mealsRepository.searchMeals(request.query)
        .map { meals->
            Response(meals)
        }

    data class Request(val query: String) : UseCase.Request
    data class Response(val meals: List<MealEntity>) : UseCase.Response
}