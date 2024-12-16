package com.example.traningcomposeapp.repository

import com.example.traningcomposeapp.data.dao.CinemaDao
import com.example.traningcomposeapp.data.entities.CinemaEntity
import javax.inject.Inject

class CinemaRepository @Inject constructor(
    private val cinemaDao: CinemaDao
) {
    suspend fun insertCinemas(cinemas: List<CinemaEntity>) = cinemaDao.insertCinemas(cinemas)
    suspend fun getAllCinemas(): List<CinemaEntity> = cinemaDao.getAllCinemas()
    suspend fun getCinemaById(cinemaId: Int): CinemaEntity? = cinemaDao.getCinemaById(cinemaId)
    suspend fun countCinemas(): Int = cinemaDao.countCinemas()
}