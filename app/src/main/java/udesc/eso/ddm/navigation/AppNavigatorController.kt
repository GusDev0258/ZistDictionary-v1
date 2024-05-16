package udesc.eso.ddm.navigation

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import udesc.eso.ddm.ui.screens.AppHomeScreen
import udesc.eso.ddm.ui.screens.DictionaryScreen
import udesc.eso.ddm.ui.screens.HomeScreen
import udesc.eso.ddm.ui.screens.LoginScreen
import udesc.eso.ddm.ui.screens.RegisterScreen
import udesc.eso.ddm.viewmodel.RegisterViewModel

@ExperimentalMaterial3Api
@Composable
fun AppNavigatorController() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Routes.HOME) {
        composable(Routes.HOME) { HomeScreen(navController = navController) }
        composable(Routes.REGISTER_SCREEN) {
            val viewModel = koinViewModel<RegisterViewModel>()
            val uiState = viewModel.uiState.collectAsState()
            val scope = rememberCoroutineScope()
            val signUpSuccessful = viewModel.signUpSuccessful.collectAsState(initial = false)
            LaunchedEffect(signUpSuccessful.value) {
                if (signUpSuccessful.value) {
                    navController.navigate(Routes.APP_HOME)
                }
            }
            RegisterScreen(uiState = uiState.value, onSignUpClick = {
                scope.launch {
                    viewModel.signUp()
                }
            })
        }
        composable(Routes.LOGIN_SCREEN) { LoginScreen(navController = navController) }
        composable(Routes.APP_HOME) { AppHomeScreen(navController = navController) }
        composable(
            route = "${Routes.DICTIONARY_SCREEN}/{dictionaryId}",
            arguments = listOf(navArgument("dictionaryId") { type = NavType.StringType })
        ) { navBackStackEntry ->
            val dictionaryId = navBackStackEntry.arguments?.getString("dictionaryId")
            DictionaryScreen(dictionaryId = dictionaryId, navController = navController)
        }
    }
}