package com.sateeshjh.recipedirectory.presentation.meal_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sateeshjh.recipedirectory.common.Resource
import com.sateeshjh.recipedirectory.domain.use_case.MealDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class MealDetailsViewModel @Inject constructor(private val getMealDetailsUseCase: MealDetailsUseCase) :
    ViewModel() {

    private val _mealDetailsState = MutableStateFlow<MealDetailsState>(MealDetailsState())
    val mealDetailsState: StateFlow<MealDetailsState> = _mealDetailsState

    fun getMealDetails(id: String) {
        getMealDetailsUseCase(id).onEach {
            when (it) {
                is Resource.Loading -> {
                    _mealDetailsState.value = MealDetailsState(
                        isLoading = true
                    )
                }
                is Resource.Success -> {
                    _mealDetailsState.value = MealDetailsState(
                        data = it.data
                    )
                }
                is Resource.Error -> {
                    _mealDetailsState.value = MealDetailsState(
                        error = it.message ?: ""
                    )
                }
            }
        }.launchIn(viewModelScope)
    }
}