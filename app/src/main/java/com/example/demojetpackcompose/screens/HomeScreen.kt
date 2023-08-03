package com.example.demojetpackcompose.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.demojetpackcompose.Screens
import com.example.demojetpackcompose.ui.theme.DemoJetpackComposeTheme

@Composable
fun HomeScreen(modifier: Modifier = Modifier, navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.LightGray)
    ) {
        Text(
            modifier = Modifier
                .align(Alignment.Center)
                .clickable {
                    navController.navigate(route = Screens.Details.route)
                },
            fontWeight = FontWeight.Bold,
            fontSize = 30.sp,
            text = "Home Screen",
            textAlign = TextAlign.Center,
        )
    }
}

@Composable
@Preview(showBackground = true)
fun HomeScreenPreview() {
    DemoJetpackComposeTheme {
        HomeScreen(navController = rememberNavController())
    }
}