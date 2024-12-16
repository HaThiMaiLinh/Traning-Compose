package com.example.traningcomposeapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.traningcomposeapp.data.database.Data
import com.example.traningcomposeapp.data.entities.MovieEntity
import com.example.traningcomposeapp.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val movieRepository: MovieRepository
) : ViewModel() {

    init {
        checkAndInsertDefaultMovies()
    }

    private fun checkAndInsertDefaultMovies() {
        viewModelScope.launch {
            if (movieRepository.countMovies() == 0) {
                insertDefaultMovies()
            }
        }
    }

    fun insertMovies(movies: List<MovieEntity>) {
        viewModelScope.launch {
            movieRepository.insertMovies(movies)
        }
    }

    suspend fun getAllMovies() = movieRepository.getAllMovies()

    suspend fun getMovieById(movieId: Int): MovieEntity? {
        return movieRepository.getMovieById(movieId)
    }

    suspend fun getPosterPathForMovie(movieId: Int): String {
        val movie = getMovieById(movieId)
        return movie?.posterPath ?: "default_poster_path"
    }

    suspend fun getMovieTitle(movieId: Int): String {
        val movie = getMovieById(movieId)
        return movie?.title ?: "Unknown Movie"
    }

    fun insertDefaultMovies() {
        insertMovies(Data.movies)
    }
}