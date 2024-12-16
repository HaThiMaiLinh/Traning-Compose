package com.example.traningcomposeapp.di

import android.content.Context
import androidx.room.Room
import com.example.traningcomposeapp.data.dao.CinemaDao
import com.example.traningcomposeapp.data.dao.MovieDao
import com.example.traningcomposeapp.data.dao.TicketDao
import com.example.traningcomposeapp.data.dao.UserDao
import com.example.traningcomposeapp.data.database.AppDatabase
import com.example.traningcomposeapp.repository.CinemaRepository
import com.example.traningcomposeapp.repository.MovieRepository
import com.example.traningcomposeapp.repository.TicketRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideContext(@ApplicationContext context: Context): Context {
        return context
    }

    @Provides
    @Singleton
    fun provideDatabase(context: Context): AppDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            "app_database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideMovieDao(database: AppDatabase): MovieDao {
        return database.movieDao()
    }

    @Provides
    @Singleton
    fun provideCinemaDao(database: AppDatabase): CinemaDao {
        return database.cinemaDao()
    }

    @Provides
    @Singleton
    fun provideTicketDao(database: AppDatabase): TicketDao {
        return database.ticketDao()
    }

    @Provides
    @Singleton
    fun provideUserDao(database: AppDatabase): UserDao {
        return database.userDao()
    }

    @Provides
    @Singleton
    fun provideMovieRepository(movieDao: MovieDao): MovieRepository {
        return MovieRepository(movieDao)
    }

    @Provides
    @Singleton
    fun provideCinemaRepository(cinemaDao: CinemaDao): CinemaRepository {
        return CinemaRepository(cinemaDao)
    }

    @Provides
    @Singleton
    fun provideTicketRepository(ticketDao: TicketDao): TicketRepository {
        return TicketRepository(ticketDao)
    }
}