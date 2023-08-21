package com.mohamed_amgd.gamedeals.presentation.ui.components

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.mohamed_amgd.gamedeals.R
import com.mohamed_amgd.gamedeals.domain.models.Deal
import com.mohamed_amgd.gamedeals.presentation.ui.theme.DealShape
import com.mohamed_amgd.gamedeals.presentation.ui.theme.GameDealsTheme
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
            painter = painterResource(id = R.drawable.ico_free_deals_header),
            contentDescription = ""
        )
        Text(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(start = 14.dp),
            text = "Free Deals",
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
            .clip(DealShape)
            .width(126.dp)
            .height(168.dp)
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
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(36.dp)
                .background(MaterialTheme.colors.surface)
                .align(Alignment.TopCenter),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            if (deal.expiryDate.isNotBlank()) {
                Text(
                    modifier = Modifier
                        .padding(horizontal = 4.dp),
                    text = deal.expiryDate, style = MaterialTheme.typography.body2
                )
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = deal.oldPrice, style = MaterialTheme.typography.body2)
                Image(
                    modifier = Modifier
                        .width(20.dp)
                        .height(8.dp),
                    painter = painterResource(id = R.drawable.ico_discount),
                    contentDescription = "Discount"
                )
                Text(text = deal.newPrice, style = MaterialTheme.typography.subtitle2)
            }
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
        ) {
        }
        Text(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(all = 12.dp),
            text = deal.name.trim(),
            style = MaterialTheme.typography.h3,
            maxLines = 4,
            overflow = TextOverflow.Ellipsis
        )

    }
}

@Composable
private fun DealsList(
    modifier: Modifier = Modifier,
    dealsList: List<Deal>,
    onItemClicked: (Deal) -> Unit
) {
    LazyRow(modifier = modifier) {
        itemsIndexed(dealsList) { index, deal ->
            if (index == 0) {
                Spacer(modifier = Modifier.width(36.dp))
            }
            ListItem(deal = deal, onClick = { onItemClicked(deal) })
            Spacer(modifier = Modifier.width(14.dp))
        }
    }
}

@Composable
fun FreeDealsList(
    modifier: Modifier = Modifier,
    dealsList: List<Deal>,
    isLoading: Boolean,
    onItemClicked: (Deal) -> Unit
) {
    Column(modifier = modifier, horizontalAlignment = Alignment.Start) {
        Header(modifier = Modifier.padding(start = 36.dp))
        if (isLoading) {
            val loadingItem = Deal("", "", "", "", "", "", "", false)
            val loadingList = listOf(loadingItem, loadingItem)

            LazyRow(
                modifier = Modifier
                    .padding(top = 12.dp)
                    .shimmer(shimmerLoading())
            ) {
                itemsIndexed(loadingList) { index, _ ->
                    if (index == 0) {
                        Spacer(modifier = Modifier.width(36.dp))
                    }
                    Box(
                        modifier = Modifier
                            .clip(DealShape)
                            .width(126.dp)
                            .height(168.dp)
                            .background(MaterialTheme.colors.surface)
                    )
                    Spacer(modifier = Modifier.width(14.dp))
                }
            }
        } else if (dealsList.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(168.dp)
            ) {
                Text(
                    modifier = Modifier.align(Alignment.Center),
                    text = "No Deals Found",
                    style = MaterialTheme.typography.h3
                )
            }
        } else {
            LazyRow(modifier = Modifier.padding(top = 12.dp)) {
                itemsIndexed(dealsList) { index, deal ->
                    if (index == 0) {
                        Spacer(modifier = Modifier.width(36.dp))
                    }
                    ListItem(deal = deal, onClick = { onItemClicked(deal) })
                    Spacer(modifier = Modifier.width(14.dp))
                }
            }
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
        //ListItem(deal = deal, onClick = { Log.d("FreeDeal", deal.name) })
        FreeDealsList(
            dealsList = listOf(deal),
            isLoading = false,
            onItemClicked = { Log.d("FreeDeal", deal.name) })
    }
}