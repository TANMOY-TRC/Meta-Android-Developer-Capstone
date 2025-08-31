package com.example.littlelemon.ui.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.littlelemon.ui.theme.Cloud
import com.example.littlelemon.navigation.*
import com.example.littlelemon.viewmodel.UserViewModel

@Composable
fun Onboarding(navController: NavController, viewModel: UserViewModel) {
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current

    var firstName by rememberSaveable { mutableStateOf("") }
    var lastName by rememberSaveable { mutableStateOf("") }
    var email by rememberSaveable { mutableStateOf("") }

    var errorMessage by remember { mutableStateOf<String?>(null) }

    val focusRequesterFirstName = remember { FocusRequester() }
    val focusRequesterLastName = remember { FocusRequester() }
    val focusRequesterEmail = remember { FocusRequester() }

    fun validateInputs(): Boolean {
        if (firstName.isBlank()) {
            errorMessage = "Enter First Name"
            return false
        }
        if (lastName.isBlank()) {
            errorMessage = "Enter Last Name"
            return false
        }
        if (email.isBlank() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            errorMessage = "Enter Valid Email"
            return false
        }
        errorMessage = null
        return true
    }

    fun onboard() {
        if (validateInputs()) {
            viewModel.saveUserInfo(
                firstName = firstName,
                lastName = lastName,
                email = email
            )
            Toast.makeText(context, "Registration Successful", Toast.LENGTH_SHORT).show()
            navController.navigate(Home.route) {
                popUpTo(0) { inclusive = true }
            }
        } else {
            Toast.makeText(context, errorMessage ?: "Invalid input", Toast.LENGTH_SHORT).show()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .clickable(
                // No visual ripple or indication on click
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) {
                focusManager.clearFocus()
            },
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            // Header Box
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.primary)
                    .height(150.dp)
                    .padding(20.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Let's get to know you",
                    style = MaterialTheme.typography.headlineMedium,
                    color = Cloud,
                    textAlign = TextAlign.Center
                )
            }

            // Content
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            ) {
                Text(
                    text = "Personal Information",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(top = 15.dp, bottom = 30.dp)
                )

                // Inputs
                Column {
                    Text(
                        text = "First Name",
                        style = MaterialTheme.typography.labelLarge,
                        modifier = Modifier.padding(bottom = 10.dp)
                    )
                    OutlinedTextField(
                        value = firstName,
                        onValueChange = { firstName = it },
                        singleLine = true,
                        textStyle = MaterialTheme.typography.titleSmall,
                        shape = MaterialTheme.shapes.large,
                        keyboardOptions = KeyboardOptions.Default.copy(
                            capitalization = KeyboardCapitalization.Words,
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Next
                        ),
                        keyboardActions = KeyboardActions(
                            onNext = { focusRequesterLastName.requestFocus() }
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .focusRequester(focusRequesterFirstName)
                    )
                }

                Spacer(modifier = Modifier.height(30.dp))

                Column {
                    Text(
                        text = "Last Name",
                        style = MaterialTheme.typography.labelLarge,
                        modifier = Modifier.padding(bottom = 10.dp)
                    )
                    OutlinedTextField(
                        value = lastName,
                        onValueChange = { lastName = it },
                        singleLine = true,
                        textStyle = MaterialTheme.typography.titleSmall,
                        shape = MaterialTheme.shapes.large,
                        keyboardOptions = KeyboardOptions.Default.copy(
                            capitalization = KeyboardCapitalization.Words,
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Next
                        ),
                        keyboardActions = KeyboardActions(
                            onNext = { focusRequesterEmail.requestFocus() }
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .focusRequester(focusRequesterLastName)
                    )
                }

                Spacer(modifier = Modifier.height(30.dp))

                Column {
                    Text(
                        text = "Email",
                        style = MaterialTheme.typography.labelLarge,
                        modifier = Modifier.padding(bottom = 10.dp)
                    )
                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it },
                        singleLine = true,
                        textStyle = MaterialTheme.typography.titleSmall,
                        shape = MaterialTheme.shapes.large,
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Email,
                            imeAction = ImeAction.Done
                        ),
                        keyboardActions = KeyboardActions(
                            onDone = { focusManager.clearFocus() }
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .focusRequester(focusRequesterEmail)
                    )
                }
            }
        }

        // Register Button
        Button(
            onClick = { onboard() },
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
            shape = MaterialTheme.shapes.large,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .fillMaxWidth()
                .padding(20.dp)
                .height(50.dp)
        ) {
            Text(
                text = "Register",
                style = MaterialTheme.typography.bodyLarge,
                color = Color.Black
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun OnboardingPreview() {
    val navController = rememberNavController()
    val viewModel: UserViewModel = viewModel()

    Onboarding(navController = navController, viewModel = viewModel)
}
