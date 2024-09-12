package com.androdevelopment.domain.usecase

import com.androdevelopment.domain.entity.meals.MealEntity
import com.androdevelopment.domain.repository.MealsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class GetMealsByCategoryUseCase @Inject constructor(
    configuration: Configuration,
    private val mealsRepository: MealsRepository,
) : UseCase<GetMealsByCategoryUseCase.Request, GetMealsByCategoryUseCase.Response>(configuration) {

    override fun process(request: Request): Flow<Response> =
        mealsRepository.getMealsByCategory(request.category)
            .map {
                Response(it)
            }

    data class Request(val category: String) : UseCase.Request
    data class Response(val meals: List<MealEntity>) : UseCase.Response
}