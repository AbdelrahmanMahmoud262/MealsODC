package com.androdevelopment.mealsapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.androdevelopment.mealsapp.ui.screens.main.MainScreen
import com.androdevelopment.mealsapp.ui.screens.main.MainScreenViewModel

@Composable
fun MealsNavHost(
    modifier: Modifier = Modifier,
) {

    val navController = rememberNavController()

    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = Screen.MainScreen.route
    ){
        composable(Screen.MainScreen.route){
            val viewModel = hiltViewModel<MainScreenViewModel>()
            MainScreen(
                modifier = Modifier,
                state = viewModel.state,
                onEvent = {
                    viewModel.onEvent(it)
                }
            )
        }
    }
}