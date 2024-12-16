package com.example.traningcomposeapp.repository

import com.example.traningcomposeapp.data.dao.TicketDao
import com.example.traningcomposeapp.data.entities.TicketEntity
import javax.inject.Inject

class TicketRepository @Inject constructor(
    private val ticketDao: TicketDao
) {
    suspend fun insertTickets(ticket: TicketEntity) = ticketDao.insertTickets(ticket)
    suspend fun getTicketsForUser(userId: Int): List<TicketEntity> = ticketDao.getTicketsForUser(userId)
    suspend fun getAllTickets(): List<TicketEntity> = ticketDao.getAllTickets()
    suspend fun deleteTicket(ticket: TicketEntity) = ticketDao.deleteTicket(ticket)
}