package com.sateeshjh.recipedirectory.presentation.meal_details

import com.sateeshjh.recipedirectory.domain.model.MealDetails

data class MealDetailsState(
    val data: MealDetails? = null,
    val error: String = "",
    val isLoading: Boolean = false
) {
}