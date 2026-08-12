package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.ui.theme.DarkBackground
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    onTimeout: () -> Unit
) {
    // PHASE 1.1: Hold this standalone screen for exactly 3 seconds
    LaunchedEffect(Unit) {
        delay(3000)
        onTimeout()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBackground),
        contentAlignment = Alignment.Center
    ) {
        // ONLY the centered book logo URL: https://i.postimg.cc/rF2Fwv1J/860f51ee-337c-4760-b635-433b23570672.png
        AsyncImage(
            model = "https://i.postimg.cc/rF2Fwv1J/860f51ee-337c-4760-b635-433b23570672.png",
            contentDescription = "Book Logo",
            contentScale = ContentScale.Fit,
            modifier = Modifier.size(180.dp)
        )
    }
}
