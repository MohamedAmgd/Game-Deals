package com.mohamed_amgd.gamedeals.presentation.ui.components

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.mohamed_amgd.gamedeals.R
import com.mohamed_amgd.gamedeals.domain.models.Deal
import com.mohamed_amgd.gamedeals.presentation.activities.main_activity.MainActivityViewState
import com.mohamed_amgd.gamedeals.presentation.ui.theme.Black70Alpha
import com.mohamed_amgd.gamedeals.presentation.ui.theme.DarkSilver
import com.mohamed_amgd.gamedeals.presentation.ui.theme.DealShape
import com.mohamed_amgd.gamedeals.presentation.ui.theme.GameDealsTheme
import com.valentinilk.shimmer.shimmer

@Composable
fun SearchUi(
    modifier: Modifier = Modifier,
    query: String = "",
    onQueryChange: (String) -> Unit = {},
    onQuerySubmit: () -> Unit = {},
    resultsState: MainActivityViewState = MainActivityViewState.Empty,
    onResultClicked: (Deal) -> Unit = {},
    onError: (String) -> Unit
) {

    Column(
        modifier = modifier
            .background(color = Black70Alpha)
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(24.dp)
        )
        Box(
            modifier = Modifier
                .width(84.dp)
                .height(8.dp)
                .clip(RoundedCornerShape(36.dp))
                .background(DarkSilver),

            )
        SearchBar(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 44.dp)
                .padding(horizontal = 12.dp)
                .background(Color.Transparent),
            query = query,
            onQueryChange = onQueryChange,
            onQuerySubmit = onQuerySubmit
        )

        var dealsList by remember {
            mutableStateOf(emptyList<Deal>())
        }
        var isLoading by remember {
            mutableStateOf(false)
        }
        when (resultsState) {
            is MainActivityViewState.Empty -> {
                isLoading = false
                dealsList = emptyList()
            }
            is MainActivityViewState.Error -> {
                isLoading = false
                dealsList = emptyList()
                onError(resultsState.errorMessage)
            }
            is MainActivityViewState.Data -> {
                isLoading = false
                dealsList = resultsState.deals
            }
            is MainActivityViewState.Loading -> {
                isLoading = true
                dealsList = emptyList()
            }
        }
        ResultList(
            modifier = Modifier.padding(top = 18.dp),
            dealsList = dealsList,
            isLoading = isLoading,
            onItemClicked = onResultClicked
        )
    }

}

@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    query: String = "",
    onQueryChange: (String) -> Unit = {},
    onQuerySubmit: () -> Unit = {}
) {
    TextField(
        value = query,
        onValueChange = onQueryChange,
        modifier = modifier,
        singleLine = true,
        placeholder = {
            Text(
                text = "What are you looking for ?",
                style = MaterialTheme.typography.h3,
                color = DarkSilver
            )
        },
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = MaterialTheme.colors.background,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            cursorColor = Color.White,
        ),
        shape = DealShape,
        textStyle = MaterialTheme.typography.h1,
        keyboardOptions = KeyboardOptions(
            autoCorrect = true,
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Search
        ),
        keyboardActions = KeyboardActions {
            onQuerySubmit()
        }
    )
}

@Composable
private fun ListItem(
    modifier: Modifier = Modifier,
    deal: Deal,
    onClick: (Deal) -> Unit
) {
    Row(modifier = modifier
        .fillMaxWidth()
        .height(96.dp)
        .clip(DealShape)
        .clickable { onClick(deal) }
        .background(MaterialTheme.colors.background)
    ) {
        AsyncImage(
            modifier = Modifier
                .fillMaxHeight()
                .width(66.dp),
            model = deal.imageURL,
            contentScale = ContentScale.Crop,
            placeholder = painterResource(id = R.mipmap.ic_launcher_foreground),
            error = painterResource(id = R.mipmap.ic_launcher_foreground),
            onError = { Log.d("AsyncImage", "${it.result.throwable.message}") },
            contentDescription = ""
        )
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally

        ) {
            Text(
                modifier = Modifier.padding(top = 4.dp),
                text = deal.expiryDate,
                style = MaterialTheme.typography.body2
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 6.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(text = deal.oldPrice, style = MaterialTheme.typography.body1)
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Image(
                        modifier = Modifier
                            .width(44.dp)
                            .height(16.dp),
                        painter = painterResource(id = R.drawable.ico_discount),
                        contentDescription = "Discount"
                    )
                    Text(text = deal.discountPercentage, style = MaterialTheme.typography.caption)
                }
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = deal.newPrice,
                        style = MaterialTheme.typography.subtitle1,
                        fontSize = 20.sp
                    )
                    if (deal.isHistoricalLow)
                        Text(
                            text = "Historical Low",
                            style = MaterialTheme.typography.caption,
                            color = MaterialTheme.colors.secondaryVariant
                        )
                }
            }
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(all = 4.dp),
                contentAlignment = Alignment.Center
            ) {
                var textSize: TextUnit by remember { mutableStateOf(18.sp) }
                Text(
                    text = deal.name.trim(),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.h2,
                    fontSize = textSize,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    onTextLayout = { textLayoutResult ->
                        val maxCurrentLineIndex: Int = textLayoutResult.lineCount - 1
                        if (
                            textLayoutResult.isLineEllipsized(maxCurrentLineIndex) &&
                            textSize > 14.sp
                        ) {
                            textSize *= 0.9f
                        }
                    }
                )
            }
        }
    }
}


@Composable
private fun DealsList(
    modifier: Modifier = Modifier,
    dealsList: List<Deal>,
    onItemClicked: (Deal) -> Unit
) {
    LazyColumn(
        modifier = modifier
    ) {
        itemsIndexed(dealsList) { index, deal ->
            if (index == 0) {
                Spacer(modifier = Modifier.height(16.dp))
            }
            ListItem(deal = deal, onClick = { onItemClicked(deal) })
            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}

@Composable
private fun LoadingList() {
    val loadingItem = Deal("", "", "", "", "", "", "", false)
    val loadingList = listOf(loadingItem, loadingItem, loadingItem, loadingItem)

    LazyColumn(
        modifier = Modifier.shimmer(shimmerLoading())
    ) {
        itemsIndexed(loadingList) { index, _ ->
            if (index == 0) {
                Spacer(modifier = Modifier.height(16.dp))
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(96.dp)
                    .clip(DealShape)
                    .background(MaterialTheme.colors.background)
            ) {}
            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}

@Composable
private fun ResultList(
    modifier: Modifier = Modifier,
    dealsList: List<Deal>,
    isLoading: Boolean,
    onItemClicked: (Deal) -> Unit
) {
    Box(modifier = modifier) {
        if (isLoading) {
            LoadingList()
        } else if (dealsList.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Text(
                    modifier = Modifier.align(Alignment.Center),
                    text = "No Deals Found",
                    style = MaterialTheme.typography.h3
                )
            }
        } else {
            DealsList(
                dealsList = dealsList,
                onItemClicked = onItemClicked
            )
        }
        Box(
            Modifier
                .fillMaxWidth()
                .height(24.dp)
                .align(Alignment.TopCenter)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Black70Alpha,
                            Color.Transparent
                        )
                    )
                )
        )

    }

}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun DefaultPreview() {
    GameDealsTheme {
        SearchUi(modifier = Modifier.fillMaxSize(), onError = { })
    }
}

@Preview
@Composable
private fun SearchBarPreview() {
    GameDealsTheme {
        SearchBar()
    }
}

@Preview
@Composable
private fun ResultListItemPreview() {
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
    GameDealsTheme {
        ListItem(deal = deal, onClick = {})
    }
}

@Preview
@Composable
private fun ResultListPreview() {
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
    GameDealsTheme {
        ResultList(
            dealsList = listOf(deal, deal, deal, deal),
            isLoading = false
        ) { Log.d("HotDeal", deal.name) }

    }
}

