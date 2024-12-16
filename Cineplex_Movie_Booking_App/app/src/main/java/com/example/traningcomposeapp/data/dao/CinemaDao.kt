package com.example.traningcomposeapp.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.traningcomposeapp.data.entities.CinemaEntity

@Dao
interface CinemaDao {

    @Query("SELECT COUNT(*) FROM cinemas")
    suspend fun countCinemas(): Int

    @Insert
    suspend fun insertCinemas(cinemas: List<CinemaEntity>)

    @Query("SELECT * FROM cinemas")
    suspend fun getAllCinemas(): List<CinemaEntity>

    @Query("SELECT * FROM cinemas WHERE id = :cinemaId")
    suspend fun getCinemaById(cinemaId: Int): CinemaEntity?
}