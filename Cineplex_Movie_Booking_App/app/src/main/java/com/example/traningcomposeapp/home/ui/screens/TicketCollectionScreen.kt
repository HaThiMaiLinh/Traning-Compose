package com.example.traningcomposeapp.home.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.traningcomposeapp.R
import com.example.traningcomposeapp.common.compose.AppToolbar
import com.example.traningcomposeapp.common.compose.PosterGlideImage
import com.example.traningcomposeapp.data.entities.CinemaEntity
import com.example.traningcomposeapp.data.entities.MovieEntity
import com.example.traningcomposeapp.data.entities.TicketEntity
import com.example.traningcomposeapp.ui.theme.TextStyleBold12
import com.example.traningcomposeapp.ui.theme.TextStyleNormal10
import com.example.traningcomposeapp.viewmodel.CinemaViewModel
import com.example.traningcomposeapp.viewmodel.MovieViewModel
import com.example.traningcomposeapp.viewmodel.TicketViewModel

@Composable
fun TicketCollectionScreen(
    ticketViewModel: TicketViewModel,
    movieViewModel: MovieViewModel,
    cinemaViewModel: CinemaViewModel,
    userId: Int
) {
    val currentUserId = userId
    // Fetch user tickets when screen is launched
    LaunchedEffect(currentUserId) {
        ticketViewModel.getTicketsForUser(currentUserId)
    }
    val ticketCollection by ticketViewModel.ticketCollectionDetails.collectAsStateWithLifecycle()

    val selectedMovies = remember { mutableStateMapOf<Int, MovieEntity?>() }
    val selectedCinemas = remember { mutableStateMapOf<Int, CinemaEntity?>() }
    val openDialog = remember { mutableStateOf(false) }
    val ticketToDelete = remember { mutableStateOf<TicketEntity?>(null) }

    // Fetch movie and cinema details asynchronously
    LaunchedEffect(ticketCollection) {
        ticketCollection.forEach { ticket ->
            // Fetch movie and cinema details if not already loaded
            if (selectedMovies[ticket.movieId] == null) {
                selectedMovies[ticket.movieId] = movieViewModel.getMovieById(ticket.movieId)
            }
            if (selectedCinemas[ticket.cinemaId] == null) {
                selectedCinemas[ticket.cinemaId] = cinemaViewModel.getCinemaById(ticket.cinemaId)
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.black))
            .padding(16.dp)
    ) {
        AppToolbar(title = "My Ticket", showNavigationIcon = false)

        LazyColumn(verticalArrangement = Arrangement.spacedBy(24.dp)) {
            itemsIndexed(ticketCollection) { _, ticket ->

                val movie = selectedMovies[ticket.movieId]
                val cinema = selectedCinemas[ticket.cinemaId]

                if (movie != null && cinema != null) {
                    TicketItem(
                        ticket = ticket,
                        movie = movie,
                        cinema = cinema,
                        onLongClick = {
                            ticketToDelete.value = it
                            openDialog.value = true
                        }
                    )
                } else {
                    // Display a loading state while fetching movie/cinema details
                    Text("Loading TicketCollectionScreen...", color = Color.White)
                }
            }
        }
    }

    if (openDialog.value) {
        AlertDialog(
            onDismissRequest = { openDialog.value = false },
            title = { Text(text = "Cancel Ticket") },
            text = { Text(text = "Are you sure you want to cancel this ticket?") },
            confirmButton = {
                Button(onClick = {
                    ticketViewModel.deleteTicket(ticketToDelete.value!!)
                    openDialog.value = false
                }) {
                    Text("Yes")
                }
            },
            dismissButton = {
                Button(onClick = {
                    openDialog.value = false
                }) {
                    Text("No")
                }
            }
        )
    }
}

@Composable
fun TicketItem(
    ticket: TicketEntity,
    movie: MovieEntity,
    cinema: CinemaEntity,
    onLongClick: (TicketEntity) -> Unit
) {

    val randomNumber = (100000000..999999999).random()

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(180.dp)
            .background(
                color = colorResource(id = R.color.widget_background_7),
                shape = RoundedCornerShape(12.dp)
            )
            .pointerInput(Unit) {
                detectTapGestures(
                    onLongPress = {
                        onLongClick(ticket)
                    }
                )
            },
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        PosterGlideImage(
            model = movie.posterPath,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .width(130.dp)
                .height(150.dp)
                .clip(RoundedCornerShape(topStart = 12.dp, bottomStart = 12.dp))
        )

        Column(
            modifier = Modifier
                .fillMaxHeight()
                .padding(vertical = 20.dp),
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Text(
                text = movie.title,
                style = TextStyleBold12,
                color = Color.White
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Image(
                    painterResource(id = R.drawable.outline_text_snippet_24),
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(Color.White),
                    modifier = Modifier.size(14.dp)
                )
                Text(
                    text = "Ticket ID: ${ticket.id}$randomNumber",
                    style = TextStyleNormal10,
                    color = Color.White
                )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Image(
                    painterResource(id = R.drawable.ic_clock),
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(Color.White),
                    modifier = Modifier.size(14.dp)
                )
                Text(
                    text = "${ticket.date} • ${ticket.time}",
                    style = TextStyleNormal10,
                    color = Color.White
                )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Image(
                    painterResource(id = R.drawable.baseline_currency_rupee_24),
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(Color.White),
                    modifier = Modifier.size(14.dp)
                )
                Text(
                    text = "${ticket.seatList}",
                    style = TextStyleNormal10,
                    color = Color.White
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Image(
                    imageVector = Icons.Outlined.LocationOn,
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(Color.White),
                    modifier = Modifier.size(14.dp)
                )
                Text(
                    text = cinema.name,
                    style = TextStyleNormal10,
                    color = Color.White
                )
            }
        }
    }
}