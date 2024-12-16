package com.example.traningcomposeapp.home.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
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
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.traningcomposeapp.R
import com.example.traningcomposeapp.common.compose.AppToolbar
import com.example.traningcomposeapp.common.compose.CenterAlignedButton
import com.example.traningcomposeapp.common.compose.PosterGlideImage
import com.example.traningcomposeapp.data.entities.CinemaEntity
import com.example.traningcomposeapp.data.entities.MovieEntity
import com.example.traningcomposeapp.data.entities.TicketEntity
import com.example.traningcomposeapp.ui.theme.TextStyleBold14
import com.example.traningcomposeapp.ui.theme.TextStyleBold16
import com.example.traningcomposeapp.ui.theme.TextStyleBold18
import com.example.traningcomposeapp.ui.theme.TextStyleBold24
import com.example.traningcomposeapp.ui.theme.TextStyleNormal14
import com.example.traningcomposeapp.utils.Constants.EMPTY
import com.example.traningcomposeapp.viewmodel.CinemaViewModel
import com.example.traningcomposeapp.viewmodel.MovieViewModel
import com.example.traningcomposeapp.viewmodel.TicketViewModel

@Composable
fun PaymentScreen(
    movieViewModel: MovieViewModel,
    cinemaViewModel: CinemaViewModel,
    ticketViewModel: TicketViewModel,
    ticketId: Int,
    onContinueClicked: () -> Unit
) {

    var ticketDetails by remember { mutableStateOf<TicketEntity?>(null) }
    var selectedMovie by remember { mutableStateOf<MovieEntity?>(null) }
    var selectedCinema by remember { mutableStateOf<CinemaEntity?>(null) }

    // Collect ticket details from ticketViewModel
    val ticketCollection =
        ticketViewModel.ticketCollectionDetails.collectAsStateWithLifecycle().value
    ticketDetails = ticketCollection.find { it.id == ticketId }

    // Use LaunchedEffect to load the movie and cinema data asynchronously
    LaunchedEffect(ticketDetails) {
        if (ticketDetails != null) {
            selectedMovie = movieViewModel.getMovieById(ticketDetails!!.movieId)
            selectedCinema = cinemaViewModel.getCinemaById(ticketDetails!!.cinemaId)
        }
    }

    if (ticketDetails != null && selectedMovie != null && selectedCinema != null) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(colorResource(id = R.color.black))
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            AppToolbar(title = "Payment") {}
            MovieDetailsSection(selectedMovie, selectedCinema!!, ticketDetails)
            BookingSection(ticketDetails)
            Spacer(modifier = Modifier.weight(1f))
            CenterAlignedButton(
                text = "Continue",
                Modifier.fillMaxWidth(),
                textStyle = TextStyleBold16
            ) {
                onContinueClicked()
            }
        }
    } else {
        // Show loading state if data is still being fetched
        Text("Loading PaymentScreen...", color = Color.White)
    }
}

@Composable
fun MovieDetailsSection(selectedMovie: MovieEntity?, selectedCinema: CinemaEntity ,ticketDetails: TicketEntity?) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
            .background(
                color = colorResource(id = R.color.widget_background_7),
                shape = RoundedCornerShape(12.dp)
            ),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        PosterGlideImage(
            model = selectedMovie?.posterPath ?: EMPTY,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .fillMaxHeight()
                .width(100.dp)
                .clip(RoundedCornerShape(topStart = 12.dp, bottomStart = 12.dp))
        )

        Column(
            modifier = Modifier.padding(vertical = 20.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = selectedMovie?.title ?: EMPTY,
                style = TextStyleBold18,
                color = colorResource(id = R.color.widget_background_1)
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Image(
                    imageVector = Icons.Outlined.LocationOn,
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(
                        Color.White
                    ),
                    modifier = Modifier
                        .size(14.dp)
                )
                Text(
                    text = selectedCinema.name,
                    style = TextStyleNormal14,
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
                    colorFilter = ColorFilter.tint(
                        Color.White
                    ),
                    modifier = Modifier.size(14.dp)
                )
                Text(
                    text = "${ticketDetails?.date} • ${ticketDetails?.time}",
                    style = TextStyleNormal14,
                    color = Color.White
                )
            }
        }
    }
}

@Composable
fun BookingSection(ticketDetails: TicketEntity?) {
    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(
                text = "Order ID",
                style = TextStyleNormal14,
                color = colorResource(id = R.color.white)
            )
            Text(
                text = ticketDetails?.id.toString(),
                style = TextStyleBold14,
                color = colorResource(id = R.color.white)
            )
        }
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(
                text = "Seat",
                style = TextStyleNormal14,
                color = colorResource(id = R.color.white)
            )
            Text(
                text = ticketDetails?.seatList.toString(),
                style = TextStyleBold14,
                color = colorResource(id = R.color.white)
            )
        }
        HorizontalDivider()
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Total",
                style = TextStyleNormal14,
                color = colorResource(id = R.color.white)
            )
            Text(
                text = ticketDetails?.totalAmount.toString(),
                style = TextStyleBold24,
                color = colorResource(id = R.color.widget_background_1)
            )
        }
    }
}

