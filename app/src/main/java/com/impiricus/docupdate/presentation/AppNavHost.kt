package com.impiricus.docupdate.presentation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.impiricus.docupdate.presentation.message_detail.MessageDetailScreen
import com.impiricus.docupdate.presentation.message_list.MessageListScreen

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppNavHost() {
    val navController = rememberNavController()

    NavHost(navController, startDestination = "list") {
        composable("list") {
            MessageListScreen(navController)
        }
        composable("detail/{id}") { backStack ->
            MessageDetailScreen(
                id = backStack.arguments?.getString("id") ?: ""
            )
        }
    }
}
