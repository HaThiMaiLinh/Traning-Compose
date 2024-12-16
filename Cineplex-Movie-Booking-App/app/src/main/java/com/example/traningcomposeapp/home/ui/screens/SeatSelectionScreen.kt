package com.example.traningcomposeapp.home.ui.screens

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.traningcomposeapp.R
import com.example.traningcomposeapp.common.compose.AppToolbar
import com.example.traningcomposeapp.common.compose.CenterAlignedButton
import com.example.traningcomposeapp.common.compose.HeaderText
import com.example.traningcomposeapp.data.entities.CinemaEntity
import com.example.traningcomposeapp.data.entities.MovieEntity
import com.example.traningcomposeapp.data.entities.TicketEntity
import com.example.traningcomposeapp.ui.theme.TextStyleBold20
import com.example.traningcomposeapp.ui.theme.TextStyleNormal10
import com.example.traningcomposeapp.ui.theme.TextStyleNormal14
import com.example.traningcomposeapp.ui.theme.TextStyleNormal16
import com.example.traningcomposeapp.viewmodel.CinemaViewModel
import com.example.traningcomposeapp.viewmodel.MovieViewModel
import com.example.traningcomposeapp.viewmodel.TicketViewModel
import kotlinx.coroutines.flow.StateFlow
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SeatSelectionScreen(
    movieViewModel: MovieViewModel,
    cinemaViewModel: CinemaViewModel,
    ticketViewModel: TicketViewModel,
    selectedMovie: MovieEntity,
    userId: Int,
    onBuyClicked: (TicketEntity) -> Unit
) {
    val today = LocalDate.now()
    val nextTenDates = (0 until 10).map { today.plusDays(it.toLong()) }

    val context = LocalContext.current
    val selectedDateIndex = remember { mutableIntStateOf(0) }
    val selectedTimeIndex = remember { mutableIntStateOf(0) }

    val selectedSeats = remember { mutableStateListOf<String>() }

    val soldSeats = remember { mutableStateListOf<String>() }

    val timeList = listOf(
        "10:00",
        "11:20",
        "12:40",
        "1:40",
        "3:00",
        "5:30",
        "6:05",
        "6:45",
        "8:30",
        "9:45"
    )

    LaunchedEffect(
        ticketViewModel.allTickets.collectAsState().value,
        selectedMovie,
        cinemaViewModel.selectedCinema,
        selectedDateIndex.value,
        selectedTimeIndex.value
    ) {
        val ticketsList = ticketViewModel.allTickets.value
             soldSeats.clear()

        val selectedCinemaId = cinemaViewModel.selectedCinema.value?.id
        val selectedDate = nextTenDates[selectedDateIndex.value]

        val formattedSelectedDate = selectedDate.format(DateTimeFormatter.ofPattern("MMM dd"))

        ticketsList.forEach { ticket ->
            if (ticket.movieId == selectedMovie.id && ticket.cinemaId == selectedCinemaId) {
                if (ticket.date == formattedSelectedDate && ticket.time == timeList[selectedTimeIndex.value]) {
                    soldSeats.addAll(ticket.seatList)
                }
            }
        }
    }

//    // Listen for successfully booked tickets and update the sold seats list
    LaunchedEffect(Unit) {
        val ticketsList = ticketViewModel.allTickets.value
              soldSeats.clear()

        val selectedCinemaId = cinemaViewModel.selectedCinema.value?.id
        val selectedDate = nextTenDates[selectedDateIndex.value]

        val formattedSelectedDate = selectedDate.format(DateTimeFormatter.ofPattern("MMM dd"))

        ticketsList.forEach { ticket ->
            if (ticket.movieId == selectedMovie.id && ticket.cinemaId == selectedCinemaId) {
                if (ticket.date == formattedSelectedDate && ticket.time == timeList[selectedTimeIndex.value]) {
                    soldSeats.addAll(ticket.seatList)
                }
            }
        }
    }

    // Observe the ticket insertion success state and show a toast
    LaunchedEffect(ticketViewModel.insertSuccess.collectAsState().value) {
        val success = ticketViewModel.insertSuccess.value
        if (success) {
            // Show toast after ticket is successfully inserted
            Toast.makeText(context, "Ticket booked successfully!", Toast.LENGTH_SHORT).show()

            // Refresh the sold seats list
            ticketViewModel.getAllTickets()

            // Reset the success flag
            ticketViewModel.resetInsertStatus()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.black))
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        // Show UI after sold seat data is loaded
        Image(
            painter = painterResource(id = R.drawable.ic_cinema_view),
            contentDescription = null,
            modifier = Modifier.padding(vertical = 12.dp)
        )
        SeatSection(selectedSeats, soldSeats)
        DateTimeSelectionWidget(timeList, selectedDateIndex, selectedTimeIndex)
        Spacer(modifier = Modifier.weight(1f))
        TotalAmountWidget(
            selectedDateIndex,
            timeList[selectedTimeIndex.intValue],
            selectedSeats,
            cinemaViewModel.selectedCinema,
            userId,
            onBuyClicked
        )
    }
}

@Composable
fun SeatSection(selectedSeats: SnapshotStateList<String>, soldSeats: List<String>) {

    for (char in 'A'..'J') {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 6.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            for (j in 1..10) {
                val seatNumber = "$char$j"
                val isSeatSold = soldSeats.contains(seatNumber)// Check if the chair has been sold
                SeatGrid(
                    isSold = isSeatSold,
                    isSelected = selectedSeats.contains(seatNumber),
                    seatNumber = seatNumber
                ) { selectedSeatName, isSeatSelected ->
                    // If the seat is sold, no selection is allowed
                    if (!isSeatSold) {
                        if (isSeatSelected) {
                            selectedSeats.remove(selectedSeatName)
                        } else {
                            selectedSeats.add(selectedSeatName)
                        }
                    }
                }
            }
        }
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        SeatLegend(text = "Available", color = R.color.widget_background_7)
        SeatLegend(text = "Selected", color = R.color.widget_background_1)
        SeatLegend(text = "Sold", color = R.color.widget_background_6)
    }
}

@Composable
fun SeatLegend(text: String, color: Int) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier
                .background(colorResource(id = color), shape = RoundedCornerShape(4.dp))
                .size(18.dp)
                .padding(3.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {}
        Text(text = text, style = TextStyleNormal14, color = Color.White)
    }
}

@Composable
fun SeatGrid(
    isSold: Boolean,
    isSelected: Boolean,
    seatNumber: String,
    onClick: (String, Boolean) -> Unit
) {

    val seatColor: Color = when {
        isSold -> colorResource(id = R.color.widget_background_6)
        isSelected -> colorResource(id = R.color.widget_background_1)
        else -> colorResource(id = R.color.widget_background_7)
    }

    val textColor = when {
        isSelected -> {
            Color.Black
        }

        else -> {
            Color.White
        }
    }

    Column(
        modifier = Modifier
            .clickable(enabled = !isSold) {// Do not click if the seat is sold
                onClick(seatNumber, isSelected)
            }
            .background(
                color = seatColor,
                shape = RoundedCornerShape(4.dp)
            )
            .size(25.dp)
            .padding(3.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = seatNumber,
            style = TextStyleNormal10,
            color = textColor
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DateTimeSelectionWidget(
    timeList: List<String>,
    selectedDateIndex: MutableIntState,
    selectedTimeIndex: MutableIntState
) {

    val deviceWidth = LocalConfiguration.current.screenWidthDp

    val today = LocalDate.now()
    val nextTenDates = (0 until 10).map { today.plusDays(it.toLong()) }

    Column(Modifier.fillMaxWidth()) {
        HeaderText(
            text = "Select Date and Time",
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 20.dp)
        )

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(20.dp),
            modifier = Modifier.padding(vertical = 20.dp),
            contentPadding = PaddingValues(horizontal = (deviceWidth / 2).dp)
        ) {
            items(nextTenDates.size) { index ->
                DatePill(
                    date = nextTenDates[index],
                    isSelected = (index == selectedDateIndex.intValue)
                ) {
                    selectedDateIndex.intValue = index
                }
            }
        }

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(20.dp),
            contentPadding = PaddingValues(horizontal = (deviceWidth / 2).dp)
        ) {
            itemsIndexed(timeList) { index, time ->
                TimePill(
                    time = time,
                    isSelected = (index == selectedTimeIndex.intValue)
                ) {
                    selectedTimeIndex.intValue = index
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DatePill(date: LocalDate, isSelected: Boolean, onDateClick: () -> Unit) {

    val today = LocalDate.now()
    val monthAbbreviation = date.format(DateTimeFormatter.ofPattern("MMM"))
    Column(
        modifier = Modifier
            .clickable {
                onDateClick()
            }
            .background(
                color = if (isSelected) {
                    colorResource(id = R.color.widget_background_1)
                } else colorResource(
                    id = R.color.widget_background_7
                ),
                shape = RoundedCornerShape(24.dp)
            )
            .clip(RoundedCornerShape(24.dp))
            .padding(top = 12.dp, start = 6.dp, end = 6.dp, bottom = 4.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = monthAbbreviation,
            style = TextStyleNormal14,
            color = if (isSelected) {
                colorResource(id = R.color.black)
            } else {
                colorResource(id = R.color.white)
            }
        )
        Box(
            modifier = Modifier
                .size(24.dp)
                .background(
                    color = if (isSelected) {
                        colorResource(id = R.color.widget_background_10)
                    } else colorResource(
                        id = R.color.widget_background_11
                    ),
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = date.dayOfMonth.toString(),
                style = TextStyleNormal14,
                color = colorResource(id = R.color.white)
            )
        }
    }
}

@Composable
fun TimePill(time: String, isSelected: Boolean, onTimeClick: () -> Unit) {
    Row(
        modifier = Modifier
            .clickable {
                onTimeClick()
            }
            .background(
                color = if (isSelected) {
                    colorResource(id = R.color.widget_background_5)
                } else colorResource(
                    id = R.color.widget_background_7
                ),
                shape = RoundedCornerShape(24.dp)
            )
            .border(
                width = if (isSelected) {
                    1.dp
                } else 0.dp,
                color = if (isSelected) {
                    colorResource(id = R.color.widget_background_1)
                } else colorResource(
                    id = R.color.transparent
                ),
                shape = RoundedCornerShape(24.dp)
            )
            .padding(horizontal = 20.dp, vertical = 6.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = time,
            style = TextStyleNormal14,
            color = colorResource(id = R.color.white)
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TotalAmountWidget(
    selectedDateIndex: MutableIntState,
    selectedTime: String,
    selectedSeats: SnapshotStateList<String>,
    selectedCinemaStateFlow: StateFlow<CinemaEntity?>,
    userId: Int,
    onBuyClicked: (TicketEntity) -> Unit
) {
    val noOfSeats = selectedSeats.size
    val selectedCinemaDetails = selectedCinemaStateFlow.collectAsStateWithLifecycle().value

    val today = LocalDate.now()
    val nextTenDates = (0 until 10).map { today.plusDays(it.toLong()) }

    val selectedDate = nextTenDates.getOrNull(selectedDateIndex.intValue)

    Column(verticalArrangement = Arrangement.spacedBy(20.dp)) {
        HorizontalDivider(
            modifier = Modifier.padding(top = 20.dp),
            color = colorResource(id = R.color.widget_background_3)
        )
        Row(Modifier.fillMaxWidth()) {
            Column(
                verticalArrangement = Arrangement.spacedBy(6.dp),
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = "Total",
                    style = TextStyleNormal16,
                    color = colorResource(id = R.color.white)
                )
                Text(
                    text = "${(noOfSeats * 210).toFloat()} VND",
                    style = TextStyleBold20,
                    color = colorResource(id = R.color.widget_background_1)
                )
            }

            CenterAlignedButton(
                text = "Buy Ticket",
                modifier = Modifier.weight(1f),
                enabled = noOfSeats != 0,
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (noOfSeats == 0) {
                        colorResource(id = R.color.widget_background_8)
                    } else {
                        colorResource(id = R.color.widget_background_1)
                    },
                    contentColor = colorResource(id = R.color.black)
                )
            ) {
                val ticket = TicketEntity(
                    userId = userId,
                    movieId = selectedCinemaDetails?.id ?: 0,
                    cinemaId = selectedCinemaDetails?.id ?: 0,
                    seatList = selectedSeats.toList(),
                    date = selectedDate?.format(DateTimeFormatter.ofPattern("MMM dd")) ?: "Unknown",
                    time = selectedTime,
                    totalAmount = (noOfSeats * 210).toFloat()
                )
                onBuyClicked(ticket)
            }
        }
    }
}