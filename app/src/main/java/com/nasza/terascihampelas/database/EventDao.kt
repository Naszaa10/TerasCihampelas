package com.nasza.terascihampelas.database

import androidx.room.*
import com.nasza.terascihampelas.model.Event

@Dao
interface EventDao {
    @Query("SELECT * FROM events")
    fun getAllEvents(): List<Event>

    @Insert
    fun insertEvent(event: Event)

    @Update
    fun updateEvent(event: Event)

    @Delete
    fun deleteEvent(event: Event)
}