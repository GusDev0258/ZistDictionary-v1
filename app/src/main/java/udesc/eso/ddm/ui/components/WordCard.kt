package udesc.eso.ddm.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import udesc.eso.ddm.model.Word
import udesc.eso.ddm.ui.theme.Black
import udesc.eso.ddm.ui.theme.BlueGray300
import udesc.eso.ddm.ui.theme.BlueGray50
import udesc.eso.ddm.ui.theme.DeepOrange50
import udesc.eso.ddm.ui.theme.Green60
import udesc.eso.ddm.ui.theme.Green70
import udesc.eso.ddm.ui.theme.Orange60
import udesc.eso.ddm.ui.theme.Orange70

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WordCard(word: Word?) {
    ElevatedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        onClick = { /*TODO*/ }, colors = CardDefaults.cardColors(
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
            TextButton(onClick = { /*TODO*/ }, content = { Text(text = "Ver mais informações") })
        }
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