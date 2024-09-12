package com.androdevelopment.mealsapp.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.androdevelopment.mealsapp.ui.navigation.MealsNavHost

@Composable
fun MealsApp(modifier: Modifier = Modifier) {

    Scaffold(
        modifier = modifier
            .fillMaxSize(),
        containerColor = Color(0xFF212121)
    ) { paddingValues ->
        MealsNavHost(
            modifier = Modifier
                .padding(paddingValues)
        )
    }
}