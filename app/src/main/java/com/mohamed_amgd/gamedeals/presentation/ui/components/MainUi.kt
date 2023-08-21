package com.mohamed_amgd.gamedeals.presentation.ui.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshIndicator
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.mohamed_amgd.gamedeals.domain.models.Deal
import com.mohamed_amgd.gamedeals.presentation.activities.main_activity.MainActivityViewState
import com.mohamed_amgd.gamedeals.presentation.ui.theme.AppleGreen
import com.mohamed_amgd.gamedeals.presentation.ui.theme.GameDealsTheme


@Composable
fun MainUi(
    freeDealsListState: MainActivityViewState,
    hotDealsListState: MainActivityViewState,
    onListItemClicked: (Deal) -> Unit = {},
    onSearchClicked: () -> Unit = {},
    onError: (String) -> Unit = {},
    onRefresh: () -> Unit = {}
) {

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background
    ) {
        Column {
            var isFreeDealsLoading by remember { mutableStateOf(true) }
            var freeDealsList by remember {
                mutableStateOf(emptyList<Deal>())
            }
            when (freeDealsListState) {
                is MainActivityViewState.Empty -> {
                    isFreeDealsLoading = false
                    freeDealsList = emptyList()
                }
                is MainActivityViewState.Error -> {
                    isFreeDealsLoading = false
                    freeDealsList = emptyList()
                    onError(freeDealsListState.errorMessage)
                }
                is MainActivityViewState.Data -> {
                    isFreeDealsLoading = false
                    freeDealsList = freeDealsListState.deals
                }
                is MainActivityViewState.Loading -> {
                    isFreeDealsLoading = true
                    freeDealsList = emptyList()
                }
            }

            var isHotDealsLoading by remember { mutableStateOf(true) }
            var hotDealsList by remember {
                mutableStateOf(emptyList<Deal>())
            }

            when (hotDealsListState) {
                is MainActivityViewState.Empty -> {
                    isHotDealsLoading = false
                    hotDealsList = emptyList()
                }
                is MainActivityViewState.Error -> {
                    isHotDealsLoading = false
                    hotDealsList = emptyList()
                    onError(hotDealsListState.errorMessage)
                }
                is MainActivityViewState.Data -> {
                    isHotDealsLoading = false
                    hotDealsList = hotDealsListState.deals
                }
                is MainActivityViewState.Loading -> {
                    isHotDealsLoading = true
                    hotDealsList = emptyList()
                }
            }
            val swipeRefreshState =
                rememberSwipeRefreshState(isRefreshing = isFreeDealsLoading || isHotDealsLoading)
            AppBar(onSearchClicked = onSearchClicked)
            SwipeRefresh(
                state = swipeRefreshState,
                onRefresh = onRefresh,
                indicator = { state, trigger ->
                    SwipeRefreshIndicator(
                        state = state,
                        refreshTriggerDistance = trigger,
                        scale = true,
                        backgroundColor = MaterialTheme.colors.primary,
                        contentColor = AppleGreen,
                    )

                }
            ) {
                Column {
                    FreeDealsList(
                        modifier = Modifier.padding(top = 34.dp),
                        dealsList = freeDealsList,
                        isLoading = isFreeDealsLoading,
                        onItemClicked = onListItemClicked
                    )
                    HotDealsList(
                        modifier = Modifier
                            .padding(top = 14.dp)
                            .padding(horizontal = 36.dp),
                        dealsList = hotDealsList,
                        isLoading = isHotDealsLoading,
                        onItemClicked = onListItemClicked
                    )
                }

            }
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun DefaultPreview() {
    val deal = Deal(
        "Grand Theft Auto V",
        "59.99$",
        "Free",
        "-100%",
        "End in 5 Days",
        "https://upload.wikimedia.org/wikipedia/en/a/a5/Grand_Theft_Auto_V.png",
        "https://en.wikipedia.org/wiki/Grand_Theft_Auto_V",
        true,
    )
    val dealsList: List<Deal> = listOf(deal, deal, deal, deal)
    GameDealsTheme {
        MainUi(
            MainActivityViewState.Data(dealsList),
            MainActivityViewState.Data(dealsList),
            onListItemClicked = { Log.d("MainUI", it.name) },
            onError = { Log.e("MainUI", "error: $it") },
        )
    }
}