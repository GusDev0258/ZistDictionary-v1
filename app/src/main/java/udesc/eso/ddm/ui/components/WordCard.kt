package udesc.eso.ddm.ui.components

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import coil.compose.rememberImagePainter
import udesc.eso.ddm.model.Word
import udesc.eso.ddm.ui.theme.Black
import udesc.eso.ddm.ui.theme.DeepOrange50
import udesc.eso.ddm.ui.theme.Green50
import udesc.eso.ddm.ui.theme.Green60
import udesc.eso.ddm.ui.theme.Grey10
import udesc.eso.ddm.ui.theme.Grey70
import udesc.eso.ddm.ui.theme.Orange50
import udesc.eso.ddm.ui.theme.Orange60
import java.io.File
import java.io.FileOutputStream

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WordCard(word: Word?) {
    val showWordDialog = remember {
        mutableStateOf(false)
    }
    ElevatedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        onClick = { showWordDialog.value = !showWordDialog.value }, colors = CardDefaults.cardColors(
            containerColor = DeepOrange50,
        ),
        modifier = Modifier
            .fillMaxWidth(1f)
            .height(164.dp)
            .padding(12.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(6.dp)
                .fillMaxWidth(1f)
                .height(30.dp),
            horizontalArrangement =
            Arrangement.Center, verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = word?.word ?: "Default Word", fontSize = 20.sp, fontWeight = FontWeight
                    .Bold, color = Green60, modifier = Modifier.padding(horizontal = 4.dp)
            )
            Text(
                text = word?.translation ?: "Default Translation", fontSize = 20.sp, fontWeight
                = FontWeight.Black, color = Orange60, modifier = Modifier.padding(horizontal = 4.dp)
            )
        }
        Column(modifier = Modifier.padding(6.dp)) {
            Text(
                text = word?.meaning ?: "Default MeaningDefault MeaningDefault Meaning", color =
                Black, fontWeight = FontWeight.SemiBold, overflow = TextOverflow.Ellipsis,
                maxLines = 2, textAlign = TextAlign.Left
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth(1f)
                .height(36.dp), horizontalArrangement = Arrangement
                .Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextButton(
                onClick = { showWordDialog.value = !showWordDialog.value },
                content = { Text(text = "Ver mais informações") })
        }
        if (showWordDialog.value) {
            WordDialog(word = word, active = showWordDialog)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WordDialog(word: Word?, active: MutableState<Boolean>) {
    val context = LocalContext.current
    val imageUri = remember {
        mutableStateOf<Uri?>(null)
    }
    val launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.TakePicturePreview()) { bitmap ->
            bitmap?.let {
                val uri = saveBitmapToUri(context, it)
                imageUri.value = uri
            }

        }
    if (active.value) {
        Dialog(
            onDismissRequest = { active.value = false },
            properties = DialogProperties(dismissOnClickOutside = true)
        ) {
            OutlinedCard(
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 6.dp
                ),
                onClick = { /*TODO*/ },
                colors = CardDefaults.cardColors(
                    containerColor = Grey10,
                ),
                modifier = Modifier
                    .fillMaxWidth(1f)
                    .height(600.dp)
                    .padding(8.dp),
                border = BorderStroke(2.dp, Green50),
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(32.dp),
                    horizontalAlignment =
                    Alignment.Start
                ) {
                    Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
                        Text(
                            text = word?.word ?: "Default Title", fontWeight = FontWeight.Bold, color =
                            Orange50,
                            fontStyle = FontStyle.Italic, textDecoration = TextDecoration.Underline,
                            fontSize = 24.sp,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )

                    }
                    Column {
                        Text(text = "Tradução", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                        Text(
                            text = word?.translation ?: "Default Translation",
                            fontWeight = FontWeight.Bold,
                            color = Green50,
                            fontSize = 24.sp,
                            textDecoration = TextDecoration.Underline,
                        )
                    }

                    Column {
                        Text(
                            text = "Significado",
                            color = Black,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(
                                bottom = 8
                                    .dp
                            ),
                            fontSize = 18.sp
                        )
                        Text(
                            text = word?.meaning ?: "Default Meaning",
                            color = Grey70,
                            fontWeight = FontWeight.Medium,
                            fontSize = 16.sp
                        )
                    }
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Text(
                            text = "Frases de exemplo",
                            color = Black,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(
                                bottom = 8
                                    .dp
                            ),
                            fontSize = 18.sp
                        )
                        Column(
                            modifier = Modifier
                                .padding(bottom = 8.dp)
                                .fillMaxWidth(),
                        ) {
                            Text(
                                text = "${word?.fromLanguage ?: "Default from Language"}:",
                                color = Black,
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp
                            )
                            Text(
                                text = word?.fromLanguageExampleSentence ?: "Default Example sentence",
                                color = Grey70, fontWeight = FontWeight.Medium, fontSize = 16.sp
                            )
                        }
                        Column(
                            modifier = Modifier
                                .padding(bottom = 8.dp)
                                .fillMaxWidth()
                        ) {
                            Text(
                                text = "${word?.toLanguage ?: "Default to Language"}:",
                                color = Black,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = word?.toLanguageExampleSentence ?: "Default Example sentence",
                                color = Grey70, fontWeight = FontWeight.Medium, fontSize = 16.sp
                            )
                        }
                    }
                    Button(onClick = {
                        requestCameraPermission(context) {
                            launcher.launch(null)
                        }
                    }) {
                        Text(text = "Adicionar Foto")
                    }
                    imageUri.value?.let { uri ->
                        Dialog(onDismissRequest = {  }) {
                            Image(
                                painter = rememberImagePainter(data = uri), contentDescription = "Imagem da " +
                                        "palavra", modifier = Modifier
                                    .fillMaxSize(1f)
                            )
                        }

                    }
                }
            }
        }
    }
}

fun saveBitmapToUri(context: Context, bitmap: Bitmap): Uri? {
    val filesDir = context.filesDir
    val imageFile = File(filesDir, "imagem_palavra.png")
    FileOutputStream(imageFile).use { outputStream ->
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
    }
    return FileProvider.getUriForFile(context, "${context.packageName}.provider", imageFile)
}

fun requestCameraPermission(context: Context, onPermissionGranted: () -> Unit) {
    if (ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
        onPermissionGranted()
    } else {
        ActivityCompat.requestPermissions(
            context as Activity,
            arrayOf(Manifest.permission.CAMERA),
            1001
        )
    }
}

@Preview()
@Composable()
fun CardPreview() {
    WordCard(
        word = Word(
            word = "Baleia",
            translation = "Whale",
            meaning = "UUUm mamífero gigantesco que fica no mar e caga.Um mamífero gigantesco que " +
                    "fica no marUm mamífero gigantesco que fica no mar e caga.Um mamífero gigantesco" +
                    " que fica no mar e caga.m mamífero gigantesco que fica no mar e caga.",
            fromLanguage = "Portuguese",
            toLanguage = "English"
        )
    )
}