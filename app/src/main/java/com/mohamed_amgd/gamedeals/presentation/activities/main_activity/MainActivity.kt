package com.mohamed_amgd.gamedeals.presentation.activities.main_activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.mohamed_amgd.gamedeals.domain.models.Deal
import com.mohamed_amgd.gamedeals.presentation.ui.components.AutoStartUi
import com.mohamed_amgd.gamedeals.presentation.ui.components.MainUi
import com.mohamed_amgd.gamedeals.presentation.ui.components.SearchUi
import com.mohamed_amgd.gamedeals.presentation.ui.theme.AutoStartDialogShape
import com.mohamed_amgd.gamedeals.presentation.ui.theme.BottomSheetShape
import com.mohamed_amgd.gamedeals.presentation.ui.theme.GameDealsTheme
import com.mohamed_amgd.gamedeals.presentation.utils.AutoStartUtil
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel by viewModels<MainActivityViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        refreshDealsLists()
        setContent {
            GameDealsTheme {
                Screen(
                    viewModel,
                    dealClickedAction = { dealClicked(it) },
                    showErrorMessage = { showErrorMessage(it) },
                    onRefresh = { refreshDealsLists() }
                )
                var dialogState by remember {
                    mutableStateOf(true)
                }
                if (dialogState && AutoStartUtil.needAutoStartDialog(this))
                    Dialog(
                        onDismissRequest = { dialogState = false }
                    ) {
                        var isChecked by remember {
                            mutableStateOf(false)
                        }

                        AutoStartUi(
                            modifier = Modifier
                                .clip(AutoStartDialogShape)
                                .background(MaterialTheme.colors.background)
                                .width(336.dp)
                                .height(638.dp),
                            isDontShowAgainChecked = isChecked,
                            onDontShowAgainChecked = { isChecked = it },
                            onSkipClicked = {
                                willDisableAutoStartDialog(isChecked)
                                dialogState = false
                            },
                            onSettingClicked = {
                                goToSettingsClicked(isChecked)
                                dialogState = false
                            }
                        )
                    }
            }
        }
    }

    private fun refreshDealsLists() {
        viewModel.eventChannel.trySend(MainActivityEvent.LoadDealsLists)
    }

    private fun showErrorMessage(errorMessage: String) {
        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
    }

    private fun goToSettingsClicked(isDontShowAgainCheck: Boolean) {
        willDisableAutoStartDialog(isDontShowAgainCheck)
        AutoStartUtil.addAutoStart(this)
    }

    private fun willDisableAutoStartDialog(isChecked: Boolean) {
        if (isChecked)
            AutoStartUtil.disableAutoStartDialog(this)
    }

    private fun dealClicked(deal: Deal) {
        val openLinkIntent =
            Intent(Intent.ACTION_VIEW, Uri.parse(deal.discountURL))
        startActivity(openLinkIntent)
    }

}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun Screen(
    viewModel: MainActivityViewModel,
    dealClickedAction: (Deal) -> Unit,
    showErrorMessage: (String) -> Unit,
    onRefresh: () -> Unit
) {
    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = BottomSheetState(BottomSheetValue.Collapsed)
    )
    BottomSheetScaffold(
        sheetPeekHeight = 0.dp,
        scaffoldState = bottomSheetScaffoldState,
        sheetContent = {
            BottomSheetContent(viewModel, dealClickedAction, showErrorMessage)
        },
        sheetShape = BottomSheetShape,
    ) {
        MainContent(
            viewModel,
            bottomSheetScaffoldState,
            dealClickedAction,
            showErrorMessage,
            onRefresh
        )
    }
}


@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun MainContent(
    viewModel: MainActivityViewModel,
    bottomSheetScaffoldState: BottomSheetScaffoldState,
    dealClickedAction: (Deal) -> Unit,
    showErrorMessage: (String) -> Unit,
    onRefresh: () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    MainUi(
        viewModel.freeDealsListState,
        viewModel.hotDealsListState,
        onListItemClicked = dealClickedAction,
        onSearchClicked = {
            coroutineScope.launch {
                if (bottomSheetScaffoldState.bottomSheetState.isCollapsed) {
                    bottomSheetScaffoldState.bottomSheetState.expand()
                } else {
                    bottomSheetScaffoldState.bottomSheetState.collapse()
                }
            }
        },
        onError = showErrorMessage,
        onRefresh = onRefresh
    )
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun BottomSheetContent(
    viewModel: MainActivityViewModel,
    dealClickedAction: (Deal) -> Unit,
    showErrorMessage: (String) -> Unit
) {
    var query by remember {
        mutableStateOf("")
    }
    val keyboardController = LocalSoftwareKeyboardController.current
    val configuration = LocalConfiguration.current
    val bottomSheetHeight = configuration.screenHeightDp.dp - 64.dp
    SearchUi(
        modifier = Modifier
            .fillMaxWidth()
            .height(bottomSheetHeight),
        query = query,
        onQueryChange = { query = it },
        onQuerySubmit = {
            keyboardController?.hide()
            viewModel.eventChannel.trySend(MainActivityEvent.Search(query))
        },
        resultsState = viewModel.searchDealsListState,
        onResultClicked = dealClickedAction,
        onError = showErrorMessage
    )
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
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