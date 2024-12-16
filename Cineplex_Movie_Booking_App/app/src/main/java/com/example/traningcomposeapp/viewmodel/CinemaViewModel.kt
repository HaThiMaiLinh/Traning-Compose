package com.example.traningcomposeapp.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.traningcomposeapp.data.database.Data
import com.example.traningcomposeapp.data.entities.CinemaEntity
import com.example.traningcomposeapp.repository.CinemaRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CinemaViewModel @Inject constructor(
    private val cinemaRepository: CinemaRepository
) : ViewModel() {

    private val _selectedCinema = MutableStateFlow<CinemaEntity?>(null)
    val selectedCinema: StateFlow<CinemaEntity?> = _selectedCinema

    init {
        checkAndInsertDefaultCinemas()
    }

    private fun checkAndInsertDefaultCinemas() {
        viewModelScope.launch {
            if (cinemaRepository.countCinemas() == 0) {
                insertDefaultCinemas()
            }
        }
    }

    fun insertCinemas(cinemas: List<CinemaEntity>) {
        viewModelScope.launch {
            cinemaRepository.insertCinemas(cinemas)
        }
    }

    suspend fun getAllCinemas() = cinemaRepository.getAllCinemas()

    suspend fun getCinemaById(cinemaId: Int): CinemaEntity? {
        return cinemaRepository.getCinemaById(cinemaId)
    }

    fun setSelectedCinemaDetails(cinema: CinemaEntity?) {
        _selectedCinema.value = cinema
    }

    fun insertDefaultCinemas() {
        insertCinemas(Data.cinemas)
    }
}