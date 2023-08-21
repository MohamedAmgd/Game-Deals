package com.mohamed_amgd.gamedeals.presentation.activities.main_activity

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mohamed_amgd.gamedeals.domain.repositories.DealRepository
import com.mohamed_amgd.gamedeals.presentation.utils.exceptionHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val dealRepository: DealRepository
) : ViewModel() {

    var freeDealsListState by mutableStateOf<MainActivityViewState>(MainActivityViewState.Empty)
    var hotDealsListState by mutableStateOf<MainActivityViewState>(MainActivityViewState.Empty)
    var searchDealsListState by mutableStateOf<MainActivityViewState>(MainActivityViewState.Empty)

    val eventChannel: Channel<MainActivityEvent> = Channel()

    init {
        viewModelScope.launch {
            eventChannel.consumeEach {
                when (it) {
                    is MainActivityEvent.LoadDealsLists -> initDealsLists()
                    is MainActivityEvent.Search -> search(it.query)
                }
            }
        }
    }

    private fun initDealsLists() {
        viewModelScope.launch(exceptionHandler {
            freeDealsListState = MainActivityViewState.Error(it)
        }) {
            freeDealsListState = MainActivityViewState.Loading
            freeDealsListState = MainActivityViewState.Data(dealRepository.getFreeDeals())
        }

        viewModelScope.launch(exceptionHandler {
            hotDealsListState = MainActivityViewState.Error(it)
        }) {
            hotDealsListState = MainActivityViewState.Loading
            hotDealsListState = MainActivityViewState.Data(dealRepository.getHotDeals())
        }
    }

    private fun search(query: String) {
        viewModelScope.launch(exceptionHandler {
            searchDealsListState = MainActivityViewState.Error(it)
        }) {
            searchDealsListState = MainActivityViewState.Loading
            searchDealsListState = MainActivityViewState.Data(dealRepository.getDealsByTitle(query))
        }
    }
}