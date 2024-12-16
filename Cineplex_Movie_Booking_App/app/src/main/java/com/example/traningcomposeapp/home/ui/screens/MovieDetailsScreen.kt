package com.example.traningcomposeapp.home.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.traningcomposeapp.R
import com.example.traningcomposeapp.common.compose.CenterAlignedButton
import com.example.traningcomposeapp.common.compose.HeaderText
import com.example.traningcomposeapp.common.compose.PosterGlideImage
import com.example.traningcomposeapp.data.entities.CinemaEntity
import com.example.traningcomposeapp.data.entities.MovieEntity
import com.example.traningcomposeapp.ui.theme.TextStyleBold12
import com.example.traningcomposeapp.ui.theme.TextStyleBold16
import com.example.traningcomposeapp.ui.theme.TextStyleBold18
import com.example.traningcomposeapp.ui.theme.TextStyleNormal12
import com.example.traningcomposeapp.ui.theme.TextStyleNormal14
import com.example.traningcomposeapp.utils.Constants.EMPTY
import com.example.traningcomposeapp.viewmodel.CinemaViewModel
import com.example.traningcomposeapp.viewmodel.MovieViewModel

@Composable
fun MovieDetailsScreen(
    movieViewModel: MovieViewModel,
    cinemaViewModel: CinemaViewModel,
    movieDetails: MovieEntity,
    onBackPressed: () -> Unit,
    onContinuePressed: () -> Unit
) {

    var cinemas by remember { mutableStateOf(emptyList<CinemaEntity>()) }
    LaunchedEffect(Unit) {
        cinemas = cinemaViewModel.getAllCinemas()
    }

    val selectedCinema = remember { mutableIntStateOf(cinemas.firstOrNull()?.id ?: 0) }

    BackHandler {
        onBackPressed()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(Color.Black)
            .padding(bottom = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        MovieInfoSection(movieDetails)
        StorylineSection(movieDetails)
        CinemaSection(cinemas, selectedCinema)
        CenterAlignedButton(
            text = "Continue",
            Modifier.fillMaxWidth(),
            textStyle = TextStyleBold16,
        ) {
            val selectedCinemaDetail = cinemas.find {
                it.id == selectedCinema.intValue
            }
            cinemaViewModel.setSelectedCinemaDetails(selectedCinemaDetail)
            onContinuePressed()
        }
    }
}

@Composable
fun MovieInfoSection(movieDetails: MovieEntity?) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy((-50).dp)
    ) {
        PosterGlideImage(
            model = movieDetails?.backdropPath ?: EMPTY,
            contentScale = ContentScale.Crop,
            modifier = Modifier.height(180.dp)
        )
        Card(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
                .wrapContentHeight(),
            colors = CardDefaults.cardColors(containerColor = colorResource(id = R.color.widget_background_7))
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = movieDetails?.title ?: EMPTY,
                    style = TextStyleBold18,
                    color = colorResource(id = R.color.widget_background_4)
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "Duration",
                        style = TextStyleNormal14,
                        color = colorResource(id = R.color.widget_background_8)
                    )
                    Text(
                        text = "  •  ",
                        color = colorResource(id = R.color.widget_background_8)
                    )
                    Text(
                        text = movieDetails?.releaseDate ?: EMPTY,
                        style = TextStyleNormal14,
                        color = colorResource(id = R.color.widget_background_8)
                    )
                }
                Spacer(modifier = Modifier.height(20.dp))
                ReviewSection(movieDetails)
            }
        }
    }
    Column(
        Modifier.padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Row {
            Text(
                text = "Movie Genre:", style = TextStyleNormal12,
                color = colorResource(id = R.color.widget_background_9)
            )
            Text(
                text = "Action, adventure, sci-fi", style = TextStyleNormal12,
                color = colorResource(id = R.color.widget_background_4)
            )
        }
        Row {
            Text(
                text = "Censorship:", style = TextStyleNormal12,
                color = colorResource(id = R.color.widget_background_9)
            )
            Text(
                text = "13+", style = TextStyleNormal12,
                color = colorResource(id = R.color.widget_background_4)
            )
        }
        Row {
            Text(
                text = "Language:", style = TextStyleNormal12,
                color = colorResource(id = R.color.widget_background_9)
            )
            Text(
                text = "English", style = TextStyleNormal12,
                color = colorResource(id = R.color.widget_background_4)
            )
        }
    }
}

@Composable
fun ReviewSection(movieDetails: MovieEntity?) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        Text(
            text = "Review",
            style = TextStyleNormal14,
            color = colorResource(id = R.color.widget_background_4)
        )
        Icon(
            imageVector = Icons.Filled.Star,
            contentDescription = null,
            tint = colorResource(id = R.color.widget_background_1)
        )
        Text(
            text = String.format("%.1f", movieDetails?.voteAverage?.div(2) ?: 1),
            style = TextStyleNormal14,
            color = colorResource(id = R.color.widget_background_4)
        )
    }
}

@Composable
fun StorylineSection(movieDetails: MovieEntity?) {
    Column(
        Modifier.padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {

        HeaderText("Storyline")

        Text(
            text = movieDetails?.overview ?: EMPTY,
            style = TextStyleNormal12,
            color = colorResource(id = R.color.widget_background_4)
        )
    }
}

@Composable
fun CinemaSection(cinemaList: List<CinemaEntity>, selectedCinema: MutableIntState) {

    Column(
        Modifier.padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        HeaderText("Cinema")
        repeat(cinemaList.size) {
            val cinemaDetail = cinemaList[it]
            Cinema(cinemaDetail, selectedCinema.intValue) { cinemaDetails ->
                selectedCinema.intValue = cinemaDetails.id
            }
        }
    }
}

@Composable
fun Cinema(
    cinemaDetail: CinemaEntity,
    selectedCinema: Int,
    onCinemaSelect: (CinemaEntity) -> Unit
) {
    val isSelected = selectedCinema == cinemaDetail.id
    val backgroundColor = if (isSelected) {
        R.color.widget_background_5
    } else {
        R.color.widget_background_7
    }
    val border = if (isSelected) {
        BorderStroke(1.dp, color = colorResource(id = R.color.widget_background_1))
    } else {
        BorderStroke(0.dp, Color.Transparent)
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                colorResource(backgroundColor),
                shape = RoundedCornerShape(12.dp)
            )
            .border(border, shape = RoundedCornerShape(12.dp))
            .clickable {
                onCinemaSelect(cinemaDetail)
            }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(
                text = cinemaDetail.name,
                style = TextStyleBold12,
                color = Color.White
            )
            Text(
                text = cinemaDetail.distance + " | " + cinemaDetail.address,
                style = TextStyleNormal12,
                color = Color.White
            )
        }
        Image(
            painter = painterResource(cinemaDetail.logo),
            contentDescription = null,
            modifier = Modifier
                .width(32.dp)
                .height(16.dp)
        )
    }
}