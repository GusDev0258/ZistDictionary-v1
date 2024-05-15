package udesc.eso.ddm.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import udesc.eso.ddm.R

@Composable
fun ImageLogo() {
    Image(
        painter = painterResource(id = R.drawable.logo_image),
        contentDescription = "Book Zist Image Logo",
        modifier = Modifier.padding(bottom = 12.dp)
    )
}

@Composable
fun ImageText() {
    Image(painter = painterResource(id = R.drawable.logo_name), contentDescription = "Zist logo")
}