package com.example.traningcomposeapp.onboarding.ui.screens

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.traningcomposeapp.R
import com.example.traningcomposeapp.common.compose.AppToolbar
import com.example.traningcomposeapp.common.compose.CenterAlignedButton
import com.example.traningcomposeapp.common.compose.TextFieldInput
import com.example.traningcomposeapp.data.entities.UserEntity
import com.example.traningcomposeapp.home.ui.activity.HomeActivity
import com.example.traningcomposeapp.ui.theme.TextStyleBold24
import com.example.traningcomposeapp.utils.RegexUtils
import com.example.traningcomposeapp.utils.Router
import com.example.traningcomposeapp.viewmodel.UserViewModel

@Composable
fun SignUpScreen(
    onBackPressed: () -> Unit,
    onDoneClick: (String, String, String) -> Unit
    ) {

    val context = LocalContext.current
    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    var usernameError by remember { mutableStateOf("") }
    var emailError by remember { mutableStateOf("") }
    var passwordError by remember { mutableStateOf("") }

    val validateInput = {
        var valid = true
        if (!RegexUtils.onlyAlphabetRegex.matches(username)) {
            usernameError = "Username should only contain letters"
            valid = false
        } else {
            usernameError = ""
        }

        if (!RegexUtils.isValidEmail(email)) {
            emailError = "Invalid email address"
            valid = false
        } else {
            emailError = ""
        }

        if (password.length < 8) {
            passwordError = "Password must be at least 8 characters"
            valid = false
        } else {
            passwordError = ""
        }

        valid
    }

    Scaffold(
        topBar = {
            AppToolbar(title = "Sign Up") {
                onBackPressed()
            }
        },
        bottomBar = {
            CenterAlignedButton(
                text = stringResource(id = R.string.done),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 24.dp, end = 24.dp, bottom = 24.dp)
            ) {
                if (validateInput()) {
                    onDoneClick(username, email, password)
                }
            }
        }
    ) { paddingValues ->
        paddingValues.calculateTopPadding()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black)
                .padding(paddingValues)
                .padding(top = 50.dp, start = 16.dp, end = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Enter your information",
                color = colorResource(id = R.color.widget_background_1),
                style = TextStyleBold24
            )

            TextFieldInput(
                value = username,
                onValueChange = { username = it },
                placeholder = context.getString(R.string.username),
                leadingIcon = {
                    Image(painter = painterResource(id = R.drawable.ic_person_svg), contentDescription = null)
                },
                isError = usernameError.isNotEmpty(),
                errorMessage = usernameError
            )

            TextFieldInput(
                value = email,
                onValueChange = { email = it },
                placeholder = context.getString(R.string.email),
                leadingIcon = {
                    Image(painter = painterResource(id = R.drawable.outline_email_24), contentDescription = null)
                },
                keyboardType = KeyboardType.Email,
                isError = emailError.isNotEmpty(),
                errorMessage = emailError
            )

            TextFieldInput(
                value = password,
                onValueChange = { password = it },
                placeholder = context.getString(R.string.password),
                leadingIcon = {
                    Image(painter = painterResource(id = R.drawable.baseline_currency_rupee_24), contentDescription = null)
                },
                keyboardType = KeyboardType.Password,
                isPassword = true,
                isError = passwordError.isNotEmpty(),
                errorMessage = passwordError
            )
        }
    }
}