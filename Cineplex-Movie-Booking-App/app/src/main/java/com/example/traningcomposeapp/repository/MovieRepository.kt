package com.example.traningcomposeapp.repository

import com.example.traningcomposeapp.data.dao.MovieDao
import com.example.traningcomposeapp.data.entities.MovieEntity
import javax.inject.Inject

class MovieRepository @Inject constructor(
    private val movieDao: MovieDao
) {
    suspend fun insertMovies(movies: List<MovieEntity>) = movieDao.insertMovies(movies)
    suspend fun getAllMovies(): List<MovieEntity> = movieDao.getAllMovies()
    suspend fun getMovieById(movieId: Int): MovieEntity? = movieDao.getMovieById(movieId)
    suspend fun countMovies(): Int = movieDao.countMovies()
}