package udesc.eso.ddm.navigation

import android.util.Log
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
import udesc.eso.ddm.viewmodel.DictionaryViewModel
import udesc.eso.ddm.viewmodel.LoginViewModel
import udesc.eso.ddm.viewmodel.RegisterViewModel
import udesc.eso.ddm.viewmodel.UserViewModel
import udesc.eso.ddm.viewmodel.WordViewModel

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
        composable(Routes.LOGIN_SCREEN) {
            val viewModel = koinViewModel<LoginViewModel>()
            val uiState = viewModel.uiState.collectAsState()
            val scope = rememberCoroutineScope()
            val signInSuccessful = viewModel.signInSuccessful.collectAsState(initial = false)
            LaunchedEffect(signInSuccessful.value) {
                if (signInSuccessful.value) {
                    navController.navigate(Routes.APP_HOME)
                }
            }
            LoginScreen(uiState = uiState.value, onSignInClick = {
                scope.launch {
                    viewModel.signIn()
                }
            })
        }
        composable(Routes.APP_HOME) {
            val userViewModel = koinViewModel<UserViewModel>()
            val uiState = userViewModel.uiState.collectAsState()
            val dictionaryViewModel = koinViewModel<DictionaryViewModel>()
            AppHomeScreen(uiState.value, navController = navController, dictionaryViewModel)
        }
        composable(
            route = "${Routes.DICTIONARY_SCREEN}/{dictionaryId}",
            arguments = listOf(navArgument("dictionaryId") { type = NavType.StringType })
        ) { navBackStackEntry ->
            val dictionaryId = navBackStackEntry.arguments?.getString("dictionaryId")
            Log.d("Dicitionary id", "Dicitionary id: ${dictionaryId}")
            val userViewModel = koinViewModel<UserViewModel>()
            val uiState = userViewModel.uiState.collectAsState()
            val dictionaryViewModel = koinViewModel<DictionaryViewModel>()
            val wordViewModel = koinViewModel<WordViewModel>()
            DictionaryScreen(
                dictionaryId = dictionaryId,
                userUiState = uiState.value,
                dictionaryViewModel = dictionaryViewModel,
                wordViewModel = wordViewModel,
                navController = navController
            )
        }
    }
}