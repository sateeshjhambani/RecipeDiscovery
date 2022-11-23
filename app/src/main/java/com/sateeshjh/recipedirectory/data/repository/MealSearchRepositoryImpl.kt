package com.sateeshjh.recipedirectory.data.repository

import com.sateeshjh.recipedirectory.data.model.MealsDTO
import com.sateeshjh.recipedirectory.data.remote.MealSearchAPI
import com.sateeshjh.recipedirectory.domain.repository.MealSearchRepository

class MealSearchRepositoryImpl(private val mealSearchAPI: MealSearchAPI): MealSearchRepository {

    override suspend fun getMealList(s: String): MealsDTO {
        return mealSearchAPI.getMealList(s)
    }
}