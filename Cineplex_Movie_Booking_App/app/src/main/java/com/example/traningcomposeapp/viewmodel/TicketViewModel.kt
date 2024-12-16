package com.example.traningcomposeapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.traningcomposeapp.data.entities.TicketEntity
import com.example.traningcomposeapp.repository.TicketRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TicketViewModel @Inject constructor(
    private val ticketRepository: TicketRepository
) : ViewModel() {

    private val _allTickets = MutableStateFlow<List<TicketEntity>>(emptyList())
    val allTickets: StateFlow<List<TicketEntity>> = _allTickets

    private val _ticketCollectionDetails = MutableStateFlow<List<TicketEntity>>(emptyList())
    val ticketCollectionDetails: StateFlow<List<TicketEntity>> = _ticketCollectionDetails

    // State for insertion success/failure
    private val _insertSuccess = MutableStateFlow(false)
    val insertSuccess: StateFlow<Boolean> = _insertSuccess

    // Fetch all tickets in the system
    init {
        viewModelScope.launch {
            _allTickets.value = ticketRepository.getAllTickets()
        }
    }

    fun getAllTickets() {
        viewModelScope.launch {
            _allTickets.value = ticketRepository.getAllTickets()
        }
    }

    // Fetch tickets for a specific user
    fun getTicketsForUser(userId: Int) {
        viewModelScope.launch {
            _ticketCollectionDetails.value = ticketRepository.getTicketsForUser(userId)
        }
    }

    fun insertTickets(ticket: TicketEntity): TicketEntity? {
        var insertedTicket: TicketEntity? = null
        viewModelScope.launch {
            try {
                // Insert ticket into repository
                ticketRepository.insertTickets(ticket)
                // Update the ticket collection
                getTicketsForUser(ticket.userId)
                // Update the list of all tickets
                getAllTickets()

                insertedTicket = _allTickets.value.lastOrNull()
                // Mark the insertion as successful
                _insertSuccess.value = true
            } catch (e: Exception) {
                _insertSuccess.value = false
            }
        }
        return insertedTicket
    }

    fun deleteTicket(ticket: TicketEntity) {
        viewModelScope.launch {
            ticketRepository.deleteTicket(ticket)
            getTicketsForUser(ticket.userId)
            getAllTickets()
        }
    }

    fun resetInsertStatus() {
        _insertSuccess.value = false
    }
}