package com.sateeshjh.recipedirectory.data.repository

import com.sateeshjh.recipedirectory.data.model.MealsDTO
import com.sateeshjh.recipedirectory.data.remote.MealSearchAPI
import com.sateeshjh.recipedirectory.domain.repository.MealDetailsRepository

class MealDetailsRepositoryImpl(private val mealSearchAPI: MealSearchAPI): MealDetailsRepository {

    override suspend fun getMealDetails(i: String): MealsDTO {
        return mealSearchAPI.getMealDetails(i);
    }
}