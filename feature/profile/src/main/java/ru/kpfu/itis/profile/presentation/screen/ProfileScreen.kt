package ru.kpfu.itis.profile.presentation.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import org.orbitmvi.orbit.compose.collectAsState
import ru.kpfu.itis.core_ui.composable.TextFieldWithErrorState
import ru.kpfu.itis.core_ui.ui.theme.Persimmon
import ru.kpfu.itis.core_ui.ui.theme.SeaGreen
import ru.kpfu.itis.core.R as CoreR

@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = hiltViewModel()
) {

    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var isEditing by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Bottom
    ) {

        viewModel.collectAsState().apply {

            TextFieldWithErrorState(
                value = name,
                onValueChange = { name = it },
                labelValue = stringResource(id = CoreR.string.name),
                validationResult = this.value.emailValidationResult,
            )

            Spacer(modifier = Modifier.height(8.dp))

            TextFieldWithErrorState(
                value = email,
                onValueChange = { email = it },
                labelValue = stringResource(id = CoreR.string.email),
                validationResult = this.value.emailValidationResult,
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { isEditing = !isEditing },
                modifier = Modifier.fillMaxWidth(),
                colors = if (isEditing) ButtonDefaults.buttonColors(SeaGreen)
                else ButtonDefaults.buttonColors()
            ) {
                Text(
                    text = if (isEditing)
                        stringResource(id = CoreR.string.save_profile)
                    else stringResource(id = CoreR.string.edit_mode)
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            Button(
                onClick = { },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(Persimmon)
            ) {
                Text(text = stringResource(id = CoreR.string.exit_account))
            }
        }
    }
}
