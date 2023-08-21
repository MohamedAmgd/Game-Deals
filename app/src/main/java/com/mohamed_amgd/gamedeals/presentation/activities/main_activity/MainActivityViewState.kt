package com.mohamed_amgd.gamedeals.presentation.activities.main_activity

import com.mohamed_amgd.gamedeals.domain.models.Deal

sealed class MainActivityViewState {
    object Loading : MainActivityViewState()
    object Empty : MainActivityViewState()
    data class Error(val errorMessage: String) : MainActivityViewState()
    data class Data(val deals: List<Deal>) : MainActivityViewState()
}