package com.sateeshjh.recipedirectory.domain.repository

import com.sateeshjh.recipedirectory.data.model.MealsDTO

interface MealDetailsRepository {

    suspend fun getMealDetails(i: String): MealsDTO
}