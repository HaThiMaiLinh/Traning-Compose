package com.example.traningcomposeapp.navigation

import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import com.example.traningcomposeapp.data.entities.MovieEntity
import com.example.traningcomposeapp.data.entities.TicketEntity
import com.example.traningcomposeapp.home.ui.screens.HomeScreen
import com.example.traningcomposeapp.home.ui.screens.MovieDetailsScreen
import com.example.traningcomposeapp.home.ui.screens.MoviesScreen
import com.example.traningcomposeapp.home.ui.screens.PaymentScreen
import com.example.traningcomposeapp.home.ui.screens.ProfileScreen
import com.example.traningcomposeapp.home.ui.screens.SeatSelectionScreen
import com.example.traningcomposeapp.home.ui.screens.TicketCollectionScreen
import com.example.traningcomposeapp.viewmodel.CinemaViewModel
import com.example.traningcomposeapp.viewmodel.MovieViewModel
import com.example.traningcomposeapp.viewmodel.TicketViewModel
import com.example.traningcomposeapp.viewmodel.UserViewModel
import kotlin.math.truncate

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppNavGraph(
    navController: NavHostController,
    innerPadding: PaddingValues,
    cinemaViewModel: CinemaViewModel,
    movieViewModel: MovieViewModel,
    ticketViewModel: TicketViewModel,
    userViewModel: UserViewModel,
    userId: Int
) {
    NavHost(
        navController,
        startDestination = BottomNavItem.Home_BottomNav.route,
        modifier = Modifier.padding(innerPadding)
    ) {
        addHomeRoute(
            navController,
            movieViewModel,
            userViewModel,
            cinemaViewModel,
            ticketViewModel,
            userId
        )
        addTicketRoute(ticketViewModel, movieViewModel, cinemaViewModel, userId)
        addMovieRoute(movieViewModel)
        addProfileRoute(navController, userViewModel, userId)
    }
}

@RequiresApi(Build.VERSION_CODES.O)
private fun NavGraphBuilder.addHomeRoute(
    navController: NavHostController,
    movieViewModel: MovieViewModel,
    userViewModel: UserViewModel,
    cinemaViewModel: CinemaViewModel,
    ticketViewModel: TicketViewModel,
    userId: Int
) {
    navigation(
        route = BottomNavItem.Home_BottomNav.route,
        startDestination = HomeScreen.Home.route
    ) {
        composable(route = HomeScreen.Home.route) {
            var movies by remember { mutableStateOf(emptyList<MovieEntity>()) }
            LaunchedEffect(Unit) {
                movies = movieViewModel.getAllMovies()
            }

            var username by remember { mutableStateOf("") }
            LaunchedEffect(Unit) {
                userViewModel.getUserById(userId) { user ->
                    username = user?.username ?: "Guest"
                }
            }

            HomeScreen(
                username = username,
                movies = movies,
                onClick = { movie ->
                navController.navigate("${HomeScreen.MovieDetails.route}?movieId=${movie.id}") {
                    popUpTo(navController.graph.findStartDestination().id) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            })
        }

        composable(route = "${HomeScreen.MovieDetails.route}?movieId={movieId}") { backStackEntry ->
            val movieId = backStackEntry.arguments?.getString("movieId")?.toIntOrNull() ?: 0

            val selectedMovie = remember { mutableStateOf<MovieEntity?>(null) }
            var isContinueButtonEnabled by remember { mutableStateOf(true) }

            LaunchedEffect(movieId) {
                selectedMovie.value = movieViewModel.getMovieById(movieId)
            }

            if (selectedMovie != null) {
                Log.d("AppNav", "Received userId: ${selectedMovie.value?.title}")
            } else {
                Log.d("AppNav", "movie null")
            }

            selectedMovie.value?.let { movie ->
                MovieDetailsScreen(
                    movieViewModel = movieViewModel,
                    cinemaViewModel = cinemaViewModel,
                    movieDetails = movie,
                    onBackPressed = {
                        navController.navigate(HomeScreen.Home.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = false
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    onContinuePressed = {
                        navController.navigate("${HomeScreen.SeatSelection.route}?movieId=${movie.id}") {
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
        }
    }

    composable(
        route = "${HomeScreen.SeatSelection.route}?movieId={movieId}",
        arguments = listOf(
            navArgument("movieId") { type = NavType.IntType }
        )
    ) { backStackEntry ->

        val movieId = backStackEntry.arguments?.getInt("movieId") ?: 0
        val selectedMovie = remember { mutableStateOf<MovieEntity?>(null) }
        LaunchedEffect(movieId) {
            selectedMovie.value = movieViewModel.getMovieById(movieId)
        }

        selectedMovie.value?.let { movie ->
            SeatSelectionScreen(
                cinemaViewModel = cinemaViewModel,
                movieViewModel = movieViewModel,
                ticketViewModel = ticketViewModel,
                selectedMovie = movie,
                userId = userId,
                onBuyClicked = { ticket ->
                    ticketViewModel.insertTickets(
                        TicketEntity(
                            userId = userId,
                            movieId = movie.id,
                            cinemaId = cinemaViewModel.selectedCinema.value?.id ?: 0,
                            seatList = ticket.seatList,
                            date = ticket.date,
                            time = ticket.time,
                            totalAmount = ticket.totalAmount
                        )
                    )
                    ticketViewModel.getTicketsForUser(userId)
                    navController.navigate(BottomNavItem.Ticket_BottomNav.route) {
                        popUpTo(HomeScreen.Home.route) {
                            inclusive = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}

private fun NavGraphBuilder.addTicketRoute(
    ticketViewModel: TicketViewModel,
    movieViewModel: MovieViewModel,
    cinemaViewModel: CinemaViewModel,
    userId: Int
) {
    composable(BottomNavItem.Ticket_BottomNav.route) {
        TicketCollectionScreen(ticketViewModel, movieViewModel, cinemaViewModel, userId)
    }
}

@RequiresApi(Build.VERSION_CODES.O)
private fun NavGraphBuilder.addMovieRoute(
    movieViewModel: MovieViewModel
) {
    navigation(
        route = BottomNavItem.Movie_BottomNav.route,
        startDestination = MovieScreen.Movie.route
    ) {

        composable(route = MovieScreen.Movie.route) {

            var movies by remember { mutableStateOf(emptyList<MovieEntity>()) }
            LaunchedEffect(Unit) {
                movies = movieViewModel.getAllMovies()
            }

            val nowPlayingMovies = movies
            val upcomingMovies = movies.shuffled()

            MoviesScreen(
                nowPlayingMovies = nowPlayingMovies,
                upcomingMovies = upcomingMovies,
            )
        }
    }
}

private fun NavGraphBuilder.addProfileRoute(
    navController: NavHostController,
    userViewModel: UserViewModel,
    userId: Int
) {
    composable(BottomNavItem.Profile_BottomNav.route) {
        ProfileScreen(navController, userViewModel, userId)
    }
}