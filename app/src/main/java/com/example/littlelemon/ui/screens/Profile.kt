package com.example.littlelemon.ui.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
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
fun Profile(navController: NavController, viewModel: UserViewModel) {
    val context = LocalContext.current

    val firstName = viewModel.firstName ?: "Null"
    val lastName = viewModel.lastName ?: "Null"
    val email = viewModel.email ?: "Null"

    fun signOut() {
        viewModel.deleteUserInfo()
        Toast.makeText(context, "Sign Out Successful", Toast.LENGTH_SHORT).show()
        navController.navigate(Onboarding.route) {
            popUpTo(0) { inclusive = true }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
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
                    text = "Profile Information",
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
                        text = "First name",
                        style = MaterialTheme.typography.labelLarge,
                        modifier = Modifier.padding(bottom = 10.dp)
                    )
                    OutlinedTextField(
                        value = firstName,
                        onValueChange = {  },
                        singleLine = true,
                        textStyle = MaterialTheme.typography.titleSmall,
                        shape = MaterialTheme.shapes.large,
                        modifier = Modifier.fillMaxWidth(),
                        enabled = false
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
                        onValueChange = {  },
                        singleLine = true,
                        textStyle = MaterialTheme.typography.titleSmall,
                        shape = MaterialTheme.shapes.large,
                        modifier = Modifier.fillMaxWidth(),
                        enabled = false
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
                        onValueChange = {  },
                        singleLine = true,
                        textStyle = MaterialTheme.typography.titleSmall,
                        shape = MaterialTheme.shapes.large,
                        modifier = Modifier.fillMaxWidth(),
                        enabled = false
                    )
                }
            }
        }

        // Sign Out Button
        Button(
            onClick = { signOut() },
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondaryContainer),
            shape = MaterialTheme.shapes.large,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .fillMaxWidth()
                .padding(20.dp)
                .height(50.dp)
        ) {
            Text(
                text = "Sign Out",
                style = MaterialTheme.typography.bodyLarge,
                color = Color.Black
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProfilePreview() {
    val navController = rememberNavController()
    val viewModel: UserViewModel = viewModel()

    Profile(navController = navController, viewModel = viewModel)
}
