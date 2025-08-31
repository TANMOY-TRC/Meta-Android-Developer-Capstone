package com.example.littlelemon.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.littlelemon.R
import com.example.littlelemon.navigation.Profile
import com.example.littlelemon.viewmodel.UserViewModel

@Composable
fun Header(navController: NavController, viewModel: UserViewModel) {

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val canNavigateBack = navController.previousBackStackEntry != null
    val isOnboarded = viewModel.isOnboarded
    val showProfile = isOnboarded && currentRoute != Profile.route

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp)
            .height(56.dp)
    ) {
        if (canNavigateBack) {
            IconButton(
                onClick = { navController.popBackStack() },
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .padding(10.dp)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    modifier = Modifier.size(28.dp),
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }

        Image(
            painter = painterResource(R.drawable.logo),
            contentDescription = "Little Lemon Logo",
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .align(Alignment.Center)
                .height(44.dp)
        )

        if (showProfile) {
            Image(
                painter = painterResource(id = R.drawable.profile),
                contentDescription = "Profile Image",
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(end = 20.dp)
                    .size(40.dp)
                    .clip(CircleShape)
                    .clickable {
                        navController.navigate(Profile.route)
                    }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HeaderPreview() {
    val navController = rememberNavController()
    val viewModel: UserViewModel = viewModel()

    Header(navController = navController, viewModel = viewModel)
}
