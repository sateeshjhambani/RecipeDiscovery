package com.sateeshjh.recipedirectory.presentation.meal_search

import com.sateeshjh.recipedirectory.domain.model.Meal

data class MealSearchState(
    val data: List<Meal>? = null,
    val error: String = "",
    val isLoading: Boolean = false
) {
}