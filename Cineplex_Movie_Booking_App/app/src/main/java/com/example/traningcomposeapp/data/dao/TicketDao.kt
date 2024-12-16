package com.example.traningcomposeapp.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.traningcomposeapp.data.entities.TicketEntity

@Dao
interface TicketDao {
    @Insert
    suspend fun insertTickets(ticket: TicketEntity)

    @Query("SELECT * FROM tickets WHERE userId = :userId")
    suspend fun getTicketsForUser(userId: Int): List<TicketEntity>

    @Query("SELECT * FROM tickets")
    suspend fun getAllTickets() : List<TicketEntity>

    @Delete
    suspend fun deleteTicket(ticket: TicketEntity)

    @Query("DELETE FROM tickets WHERE id = :ticketId")
    suspend fun deleteTicketById(ticketId: Int)
}