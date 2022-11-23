package com.sateeshjh.recipedirectory.domain.repository

import com.sateeshjh.recipedirectory.data.model.MealsDTO

interface MealSearchRepository {

    suspend fun getMealList(s: String): MealsDTO
}