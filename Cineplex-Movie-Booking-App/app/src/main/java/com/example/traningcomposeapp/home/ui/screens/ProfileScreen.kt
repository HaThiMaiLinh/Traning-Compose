package com.example.traningcomposeapp.home.ui.screens

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.example.traningcomposeapp.R
import com.example.traningcomposeapp.data.entities.UserEntity
import com.example.traningcomposeapp.navigation.BottomNavItem
import com.example.traningcomposeapp.onboarding.ui.activity.OnBoardingActivity
import com.example.traningcomposeapp.ui.theme.TextStyleBold14
import com.example.traningcomposeapp.ui.theme.TextStyleBold16
import com.example.traningcomposeapp.ui.theme.TextStyleNormal10
import com.example.traningcomposeapp.utils.Router
import com.example.traningcomposeapp.viewmodel.UserViewModel

@Composable
fun ProfileScreen(
    navController: NavHostController,
    userViewModel: UserViewModel,
    userId: Int
    ) {

    var user by remember { mutableStateOf<UserEntity?>(null) }

    LaunchedEffect(Unit) {
        userViewModel.getUserById(userId) { fetchedUser ->
            user = fetchedUser
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .verticalScroll(rememberScrollState())
            .padding(top = 30.dp)
            .padding(12.dp),
        verticalArrangement = Arrangement.spacedBy(32.dp)
    ) {
        if (user != null) {
            UserProfileWidget(user = user!!)
        }
        SettingsWidget(navController)
    }
}

@Composable
fun UserProfileWidget(user: UserEntity) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_person_svg),
            contentDescription = null,
            modifier = Modifier
                .size(90.dp)
                .clip(CircleShape)
        )

        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = user.username,
                style = TextStyleBold16,
                color = Color.White
            )

            Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                Image(
                    painter = painterResource(id = R.drawable.outline_email_24),
                    contentDescription = null,
                    modifier = Modifier.size(16.dp)
                )

                Text(
                    text = user.email,
                    style = TextStyleNormal10,
                    color = Color.White
                )
            }
        }
    }
}

@Composable
fun SettingsWidget(navController: NavHostController) {

    val context = LocalContext.current
    val showLogoutDialog = remember { mutableStateOf(false) }

    val settingsList = listOf("My Ticket", "Logout")

    settingsList.forEach { setting ->
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            when (setting) {
                "My Ticket" -> {
                    Image(
                        painter = painterResource(id = R.drawable.ic_outline_ticket),
                        contentDescription = null,
                        modifier = Modifier.padding(end = 12.dp)
                    )
                    Text(
                        text = setting,
                        style = TextStyleBold14,
                        color = Color.White
                    )

                    Spacer(modifier = Modifier.weight(1f))

                    Image(
                        painter = painterResource(id = R.drawable.baseline_arrow_forward_ios_24),
                        contentDescription = null,
                        modifier = Modifier.clickable {
                            navController.navigate(BottomNavItem.Ticket_BottomNav.route)
                        }
                    )
                }
                "Logout" -> {
                    Image(
                        painter = painterResource(id = R.drawable.icons_logout),
                        contentDescription = null,
                        modifier = Modifier.padding(end = 12.dp)
                    )
                    Text(
                        text = setting,
                        style = TextStyleBold14,
                        color = Color.White
                    )

                    Spacer(modifier = Modifier.weight(1f))

                    Image(
                        painter = painterResource(id = R.drawable.baseline_arrow_forward_ios_24),
                        contentDescription = null,
                        modifier = Modifier.clickable {
                            showLogoutDialog.value = true
                        }
                    )
                }
            }
        }
    }

    if (showLogoutDialog.value) {
        AlertDialog(
            onDismissRequest = { showLogoutDialog.value = false },
            title = {
                Text(text = "Logout")
            },
            text = {
                Text("Are you sure you want to logout?")
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        showLogoutDialog.value = false
                        Router.launchActivity(
                            context,
                            OnBoardingActivity::class.java,
                            Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK,
                        )
                    }
                ) {
                    Text("Yes")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { showLogoutDialog.value = false }
                ) {
                    Text("No")
                }
            }
        )
    }
}
