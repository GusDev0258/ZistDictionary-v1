package udesc.eso.ddm.ui.components

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation

@Composable
fun TextInputField(fieldLabel: String, inputText: String, onInputTextChange: (String) -> Unit) {
    OutlinedTextField(
        value = inputText,
        onValueChange = onInputTextChange,
        label = { Text(fieldLabel) })
}

@Composable
fun PasswordInputField(passwordText: String, onPasswordTextChange: (String) -> Unit) {

    OutlinedTextField(
        value = passwordText,
        onValueChange = onPasswordTextChange,
        label = { Text("senha") },
        visualTransformation = PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        trailingIcon = {
            Icon(imageVector = Icons.Outlined.Lock, contentDescription ="Lock password" )
        }
    )
}