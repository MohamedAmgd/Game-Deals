package com.mohamed_amgd.gamedeals.presentation.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mohamed_amgd.gamedeals.R
import com.mohamed_amgd.gamedeals.presentation.ui.theme.*

@Composable
fun AutoStartUi(
    modifier: Modifier = Modifier,
    isDontShowAgainChecked: Boolean = false,
    onDontShowAgainChecked: (Boolean) -> Unit = {},
    onSkipClicked: () -> Unit = {},
    onSettingClicked: () -> Unit = {}
) {
    Column(modifier = modifier) {
        TitleBar(Modifier.fillMaxWidth())

        Content(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp),
            isDontShowAgainChecked, onDontShowAgainChecked
        )

        Actions(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp),
            onSkipClicked,
            onSettingClicked
        )
    }
}

@Composable
private fun TitleBar(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.background(MaterialTheme.colors.primary),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Autostart",
            style = MaterialTheme.typography.subtitle1,
            modifier = Modifier.padding(top = 14.dp, bottom = 6.dp)
        )
    }
}


@Composable
private fun Content(
    modifier: Modifier = Modifier,
    isDontShowAgainChecked: Boolean = false,
    onDontShowAgainChecked: (Boolean) -> Unit = {}
) {
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = "Make sure you're always up to date with the newest free deals",
            style = MaterialTheme.typography.h1,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 18.dp),
            textAlign = TextAlign.Center
        )
        Text(
            text = "Turn on autostart for GameDeals. \n" +
                    "When your device restarts, the app will start automatically.",
            style = MaterialTheme.typography.body2,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp),
            textAlign = TextAlign.Center
        )
        Text(
            text = "To turn on autostart:",
            style = MaterialTheme.typography.subtitle1,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp)
        )

        Row(
            modifier = Modifier
                .padding(top = 10.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.mipmap.ic_launcher_foreground),
                contentDescription = "App Icon",
                modifier = Modifier
                    .clip(DealShape)
                    .background(MaterialTheme.colors.primary)
                    .width(78.dp)
                    .height(78.dp)
            )
            val stepOneString =
                "1. On the next screen, find\n" +
                        "   GameDeals and set the\n" +
                        "   \"Manage automatically\"           \n" +
                        "    switch to OFF."
            val appNameStartIndex = stepOneString.indexOf("GameDeals")
            val appNameEndIndex = appNameStartIndex + "GameDeals".length
            val stepOneAnnotatedString =
                AnnotatedString(
                    stepOneString,
                    spanStyles = listOf(
                        AnnotatedString.Range(
                            SpanStyle(color = AppleGreen),
                            appNameStartIndex,
                            appNameEndIndex
                        )
                    )
                )
            Text(
                text = stepOneAnnotatedString,
                style = MaterialTheme.typography.h1,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 14.dp)
            )
        }
        Box(
            modifier = Modifier
                .padding(top = 20.dp)
                .fillMaxWidth()
        ) {

            Text(
                text = "2. Make sure that all the\n" +
                        "    Manage manually \n" +
                        "    switches (Auto-launch,\n" +
                        "    Secondary launch, and \n" +
                        "    Run in background) \n" +
                        "    are set to ON\n" +
                        "    ",
                style = MaterialTheme.typography.h1,
                modifier = Modifier.align(Alignment.CenterStart)
            )

            Image(
                painter = painterResource(id = R.drawable.ic_step_two),
                contentDescription = "App Icon",
                modifier = Modifier
                    .padding(start = 14.dp)
                    .align(Alignment.CenterEnd)
            )
        }

        Box(
            modifier = Modifier
                .padding(top = 10.dp)
                .fillMaxWidth()
        ) {

            Image(
                painter = painterResource(id = R.drawable.ic_step_three),
                contentDescription = "App Icon",
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .padding(start = 10.dp)
            )
            Text(
                text = "3. Tap OK.",
                style = MaterialTheme.typography.subtitle1,
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(end = 4.dp)
            )
        }

        Row(
            modifier = Modifier.padding(top = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Checkbox(
                checked = isDontShowAgainChecked,
                onCheckedChange = { onDontShowAgainChecked(it) },
                colors = CheckboxDefaults.colors(
                    checkedColor = AppleGreen,
                    checkmarkColor = Color.White,
                    uncheckedColor = DarkSilver
                )
            )
            Text(
                text = "Don't show this again",
                style = MaterialTheme.typography.h1,
                modifier = Modifier.padding(start = 4.dp)
            )
        }
    }
}

@Composable
private fun Actions(
    modifier: Modifier = Modifier,
    onSkipClicked: () -> Unit = {},
    onSettingClicked: () -> Unit = {}
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Box(
            modifier = Modifier
                .clip(ButtonShape)
                .background(Color.White)
                .clickable { onSkipClicked() },
        ) {
            Text(
                text = "Skip",
                style = MaterialTheme.typography.h1,
                fontSize = 16.sp,
                color = AppleGreen,
                modifier = Modifier.padding(horizontal = 54.dp, vertical = 14.dp)
            )
        }

        Box(
            modifier = Modifier
                .clip(ButtonShape)
                .background(AppleGreen)
                .clickable { onSettingClicked() },
        ) {
            Text(
                text = "Go to Settings",
                style = MaterialTheme.typography.h3,
                fontSize = 18.sp,
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 14.dp)
            )
        }
    }
}

@Preview
@Composable
private fun DefaultPreview() {
    GameDealsTheme {
        /*
        TitleBar(Modifier.fillMaxWidth())
        Content(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .background(MaterialTheme.colors.background)
        )
        Actions(modifier = Modifier.fillMaxWidth())
         */
        var isChecked by remember {
            mutableStateOf(false)
        }
        AutoStartUi(
            modifier = Modifier
                .clip(AutoStartDialogShape)
                .background(MaterialTheme.colors.background),
            isDontShowAgainChecked = isChecked,
            onDontShowAgainChecked = { isChecked = it }
        )
    }
}