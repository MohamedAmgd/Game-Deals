package com.mohamed_amgd.gamedeals.presentation.ui.components

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mohamed_amgd.gamedeals.R
import com.mohamed_amgd.gamedeals.presentation.ui.theme.AppBarShape
import com.mohamed_amgd.gamedeals.presentation.ui.theme.GameDealsTheme

@Composable
fun AppBar(
    modifier: Modifier = Modifier,
    onSearchClicked: () -> Unit = {}
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(64.dp)
            .clip(AppBarShape)
            .background(color = MaterialTheme.colors.primary)
    ) {
        Image(
            modifier = Modifier
                .align(Alignment.Center)
                .width(168.dp)
                .height(28.dp),
            painter = painterResource(id = R.drawable.ico_app_bar),
            contentDescription = "Game Deals"
        )
        Image(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(end = 24.dp)
                .clickable {
                    onSearchClicked()
                    Log.d("AppBar", "Search")
                },
            painter = painterResource(id = R.drawable.ico_search),
            contentDescription = "Search"
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun AppBarDefault() {
    GameDealsTheme() {
        AppBar()
    }
}