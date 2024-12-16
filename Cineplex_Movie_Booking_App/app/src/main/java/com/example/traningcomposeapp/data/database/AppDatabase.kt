package com.example.traningcomposeapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.traningcomposeapp.data.dao.CinemaDao
import com.example.traningcomposeapp.data.dao.MovieDao
import com.example.traningcomposeapp.data.dao.TicketDao
import com.example.traningcomposeapp.data.dao.UserDao
import com.example.traningcomposeapp.data.entities.CinemaEntity
import com.example.traningcomposeapp.data.entities.MovieEntity
import com.example.traningcomposeapp.data.entities.TicketEntity
import com.example.traningcomposeapp.data.entities.UserEntity

@Database(entities = [MovieEntity::class, CinemaEntity::class, TicketEntity::class, UserEntity::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
    abstract fun cinemaDao(): CinemaDao
    abstract fun ticketDao(): TicketDao
    abstract fun userDao(): UserDao
}