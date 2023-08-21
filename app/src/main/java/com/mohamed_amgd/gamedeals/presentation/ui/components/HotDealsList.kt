package com.mohamed_amgd.gamedeals.presentation.ui.components

import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.mohamed_amgd.gamedeals.R
import com.mohamed_amgd.gamedeals.domain.models.Deal
import com.mohamed_amgd.gamedeals.presentation.ui.theme.DealShape
import com.mohamed_amgd.gamedeals.presentation.ui.theme.GameDealsTheme
import com.mohamed_amgd.gamedeals.presentation.ui.theme.GothamTypography
import com.valentinilk.shimmer.shimmer

@Composable
private fun Header(
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier) {
        Image(
            modifier = Modifier
                .height(20.dp)
                .align(Alignment.CenterStart),
            painter = painterResource(id = R.drawable.ico_hot_deals_header),
            contentDescription = ""
        )
        Text(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(start = 14.dp),
            text = "Hot Deals",
            style = MaterialTheme.typography.h1
        )

    }
}

@Composable
private fun ListItem(
    modifier: Modifier = Modifier,
    deal: Deal,
    onClick: (Deal) -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(168.dp)
            .clip(DealShape)
            .clickable { onClick(deal) }
            .background(MaterialTheme.colors.surface)
    ) {
        AsyncImage(
            modifier = Modifier
                .fillMaxSize(),
            model = deal.imageURL,
            contentScale = ContentScale.Crop,
            placeholder = painterResource(id = R.mipmap.ic_launcher_foreground),
            error = painterResource(id = R.mipmap.ic_launcher_foreground),
            onError = { Log.d("AsyncImage", "${it.result.throwable.message}") },
            contentDescription = ""
        )
        if (deal.expiryDate.isNotBlank()) {
            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .height(36.dp)
                    .clip(RoundedCornerShape(bottomStart = 36.dp))
                    .background(MaterialTheme.colors.surface),
            ) {
                Text(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(horizontal = 16.dp, vertical = 4.dp),
                    text = deal.expiryDate, style = MaterialTheme.typography.body2
                )

            }
        }

        if (deal.isHistoricalLow) {
            Box(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .height(36.dp)
                    .clip(RoundedCornerShape(bottomEnd = 36.dp))
                    .background(MaterialTheme.colors.surface),
            ) {
                Text(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                        .padding(end = 12.dp),
                    text = "Historical Low",
                    style = MaterialTheme.typography.body2,
                    color = MaterialTheme.colors.secondaryVariant
                )

            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center)
                .height(36.dp)
                .background(MaterialTheme.colors.surface),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
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
            Text(text = deal.newPrice, style = MaterialTheme.typography.subtitle1)
        }

        Box(
            modifier = Modifier
                .fillMaxHeight(0.67F)
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .drawWithContent {
                    val colors = listOf(Color.Black, Color.Transparent)
                    drawContent()
                    drawRect(
                        brush = Brush.verticalGradient(colors),
                        blendMode = BlendMode.DstIn
                    )
                }
        )
        var textSize: TextUnit by remember { mutableStateOf(GothamTypography.h2.fontSize) }
        Text(
            text = deal.name.trim(),
            modifier = Modifier
                .padding(all = 12.dp)
                .align(Alignment.BottomCenter),
            style = MaterialTheme.typography.h2,
            fontSize = textSize,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            onTextLayout = { textLayoutResult ->
                val maxCurrentLineIndex: Int = textLayoutResult.lineCount - 1
                if (
                    textLayoutResult.isLineEllipsized(maxCurrentLineIndex) &&
                    textSize > 16.sp
                ) {
                    textSize *= 0.9f
                }
            }
        )

    }
}


@Composable
fun HotDealsList(
    modifier: Modifier = Modifier,
    dealsList: List<Deal>,
    isLoading: Boolean,
    onItemClicked: (Deal) -> Unit
) {

    Column(modifier = modifier, horizontalAlignment = Alignment.Start) {
        Header()
        Box {
            if (isLoading) {
                val loadingItem = Deal("", "", "", "", "", "", "", false)
                val loadingList = listOf(loadingItem, loadingItem)
                LazyColumn(
                    modifier = Modifier
                        .padding(top = 4.dp)
                        .shimmer(shimmerLoading())
                ) {
                    itemsIndexed(loadingList) { index, _ ->
                        if (index == 0) {
                            Spacer(modifier = Modifier.height(16.dp))
                        }

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(168.dp)
                                .clip(DealShape)
                                .background(MaterialTheme.colors.surface)
                        )
                        Spacer(modifier = Modifier.height(18.dp))
                    }
                }
            } else if (dealsList.isEmpty()) {

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                ) {
                    Text(
                        modifier = Modifier
                            .fillMaxSize()
                            .align(Alignment.Center),
                        text = "No Deals Found",
                        style = MaterialTheme.typography.h3,
                        textAlign = TextAlign.Center
                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .padding(top = 4.dp)
                ) {
                    itemsIndexed(dealsList) { index, deal ->
                        if (index == 0) {
                            Spacer(modifier = Modifier.height(16.dp))
                        }
                        ListItem(deal = deal, onClick = { onItemClicked(deal) })
                        Spacer(modifier = Modifier.height(18.dp))
                    }
                }
            }
            Box(
                Modifier
                    .fillMaxWidth()
                    .height(24.dp)
                    .align(Alignment.TopCenter)
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                MaterialTheme.colors.background,
                                Color.Transparent
                            )
                        )
                    )
            )
        }
    }
}

@Preview
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
    GameDealsTheme() {
        //Header()
        //ListItem(deal = deal, onClick = { Log.d("HotDeal", deal.name) })
        HotDealsList(
            dealsList = listOf(deal),
            isLoading = false,
            onItemClicked = { Log.d("HotDeal", deal.name) })
    }
}