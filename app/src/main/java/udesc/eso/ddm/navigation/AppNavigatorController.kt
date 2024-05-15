package udesc.eso.ddm.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import udesc.eso.ddm.MainActivity
import udesc.eso.ddm.ui.screens.AppHomeScreen
import udesc.eso.ddm.ui.screens.DictionaryScreen
import udesc.eso.ddm.ui.screens.HomeScreen
import udesc.eso.ddm.ui.screens.LoginScreen
import udesc.eso.ddm.ui.screens.RegisterScreen

@Composable
fun AppNavigatorController() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Routes.HOME ) {
        composable(Routes.HOME) { HomeScreen(navController = navController)}
        composable(Routes.REGISTER_SCREEN) { RegisterScreen(navController = navController)}
        composable(Routes.LOGIN_SCREEN) { LoginScreen(navController = navController) }
        composable(Routes.APP_HOME) { AppHomeScreen(navController = navController)}
        composable(
            route = "${Routes.DICTIONARY_SCREEN}/{dictionaryId}",
            arguments = listOf(navArgument("dictionaryId"){ type = NavType.StringType})
        ) {
            navBackStackEntry -> val dictionaryId = navBackStackEntry.arguments?.getString("dictionaryId")
            DictionaryScreen(dictionaryId = dictionaryId, navController = navController)
        }
    }
}