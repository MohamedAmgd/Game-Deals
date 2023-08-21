package com.mohamed_amgd.gamedeals.presentation.activities.main_activity

sealed class MainActivityEvent {
    object LoadDealsLists : MainActivityEvent()
    data class Search(val query: String) : MainActivityEvent()
}