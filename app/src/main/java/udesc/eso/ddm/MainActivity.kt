package udesc.eso.ddm

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import udesc.eso.ddm.navigation.AppNavigatorController
import udesc.eso.ddm.ui.components.ImageLogo
import udesc.eso.ddm.ui.components.ImageText
import udesc.eso.ddm.ui.components.PrimaryButton
import udesc.eso.ddm.ui.components.SecondaryButton
import udesc.eso.ddm.ui.theme.Green40
import udesc.eso.ddm.ui.theme.Orange30
import udesc.eso.ddm.ui.theme.ZistDictionaryTheme
@ExperimentalMaterial3Api
class MainActivity() : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ZistDictionaryTheme {
                AppNavigatorController()
            }
        }
    }
}


@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ZistDictionaryTheme {
        Greeting("Android")
    }
}

fun testFun() {
}