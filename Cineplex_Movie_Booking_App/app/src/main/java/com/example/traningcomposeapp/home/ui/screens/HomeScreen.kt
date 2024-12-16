package com.example.traningcomposeapp.home.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.traningcomposeapp.R
import com.example.traningcomposeapp.data.entities.MovieEntity
import com.example.traningcomposeapp.home.ui.widgets.ComingSoonWidget
import com.example.traningcomposeapp.home.ui.widgets.NowPlayingWidget
import com.example.traningcomposeapp.ui.theme.TextStyleBold20
import com.example.traningcomposeapp.ui.theme.TextStyleNormal14
import com.example.traningcomposeapp.ui.theme.TextStyleNormal16
import com.example.traningcomposeapp.utils.Constants.EMPTY
import com.example.traningcomposeapp.viewmodel.CinemaViewModel
import com.example.traningcomposeapp.viewmodel.MovieViewModel

@Composable
fun HomeScreen(
    username: String,
    movies: List<MovieEntity>,
    onClick: (MovieEntity) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        WelcomeBackText(username)
        NowPlayingWidget(movies, onClick)
        Spacer(modifier = Modifier.height(20.dp))
        ComingSoonWidget(movies, onClick)
        Spacer(modifier = Modifier.height(4.dp))
    }
}

@Composable
fun WelcomeBackText(username: String) {
    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        Text(
            text = "Hi, $username \uD83D\uDC4F",
            style = TextStyleNormal16,
            color = colorResource(id = R.color.white),
        )
        Text(
            text = "Welcome !!!",
            style = TextStyleBold20,
            color = colorResource(id = R.color.white),
        )
    }
}
