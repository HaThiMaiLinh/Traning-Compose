package com.example.traningcomposeapp.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.traningcomposeapp.data.entities.MovieEntity

@Dao
interface MovieDao {

    @Query("SELECT COUNT(*) FROM movies")
    suspend fun countMovies(): Int

    @Insert
    suspend fun insertMovies(movies: List<MovieEntity>)

    @Query("SELECT * FROM movies")
    suspend fun getAllMovies(): List<MovieEntity>

    @Query("SELECT * FROM movies WHERE id = :movieId LIMIT 1")
    suspend fun getMovieById(movieId: Int): MovieEntity?
}