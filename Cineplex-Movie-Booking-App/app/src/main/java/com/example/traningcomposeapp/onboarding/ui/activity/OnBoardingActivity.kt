package com.example.traningcomposeapp.onboarding.ui.activity

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.traningcomposeapp.data.entities.UserEntity
import com.example.traningcomposeapp.home.ui.activity.HomeActivity
import com.example.traningcomposeapp.onboarding.ui.screens.LauncherScreen
import com.example.traningcomposeapp.onboarding.ui.screens.SignInScreen
import com.example.traningcomposeapp.onboarding.ui.screens.SignUpScreen
import com.example.traningcomposeapp.ui.theme.Black
import com.example.traningcomposeapp.ui.theme.CinePlexAppTheme
import com.example.traningcomposeapp.utils.Constants.EMPTY
import com.example.traningcomposeapp.utils.RegexUtils.isValidEmail
import com.example.traningcomposeapp.utils.Router
import com.example.traningcomposeapp.viewmodel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OnBoardingActivity : ComponentActivity() {
    private val userViewModel: UserViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CinePlexAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Black
                ) {
                    OnBoardingNavigationHandler(userViewModel)
                }
            }
        }
    }
}

@Composable
fun OnBoardingNavigationHandler(userViewModel: UserViewModel) {

    val navController = rememberNavController()
    val context = LocalContext.current
    val buttonClick = remember { mutableStateOf(EMPTY) }

    NavHost(navController = navController, startDestination = "Launcher") {
        composable(route = "Launcher") {
            LauncherScreen { signInOrSignUp ->
                buttonClick.value = signInOrSignUp
                if (signInOrSignUp == "Sign in") {
                    navController.navigate("SignIn")
                } else {
                    navController.navigate("SignUp")
                }
            }
        }

        composable(route = "SignIn") {
            SignInScreen(
                onBackPressed = { navController.popBackStack() },
                onDoneClick = { email, password ->

                    userViewModel.getUserByEmail(email) { user ->
                        if (user != null && user.password == password) {
                            Toast.makeText(context, "Sign in successful!", Toast.LENGTH_SHORT)
                                .show()
                            val bundle = Bundle().apply {
                                putInt("USER_ID", user.id)
                            }
                            Router.launchActivity(
                                context,
                                HomeActivity::class.java,
                                Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK,
                                bundle
                            )
                        } else {
                            Toast.makeText(context, "Invalid credentials", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
                }
            )
        }

        composable(route = "SignUp") {
            SignUpScreen(
                onBackPressed = { navController.popBackStack() },
                onDoneClick = { username, email, password ->

                    if (!isValidEmail(email)) {
                        Toast.makeText(context, "Invalid email format", Toast.LENGTH_SHORT).show()
                    } else {
                        userViewModel.getUserByEmail(email) { existingUser ->
                            if (existingUser != null) {
                                Toast.makeText(context, "Email already exists", Toast.LENGTH_SHORT).show()
                            } else {
                                val newUser = UserEntity(username = username, email = email, password = password)
                                userViewModel.insertUser(newUser)
                            }
                        }
                    }
                }
            )

            val userId by userViewModel.userInserted.observeAsState(initial = null)
            userId?.let { id ->
                if (id != 0) {
                    Toast.makeText(context, "Sign up successful!", Toast.LENGTH_SHORT).show()
                    val bundle = Bundle().apply {
                        putInt("USER_ID", id)
                    }
                    Router.launchActivity(
                        context,
                        HomeActivity::class.java,
                        Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK,
                        bundle
                    )
                }
            }
        }
    }
}

