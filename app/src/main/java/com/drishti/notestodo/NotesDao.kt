package com.drishti.notestodo

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface NotesDao {
    @Query("SELECT * FROM notes")
    fun getNotes(): LiveData<List<NotesEntity>>

    @Query("SELECT * FROM notes")
    fun getAll(): List<NotesEntity>

    @Query("SELECT * FROM notes WHERE id = :id")
    fun findUserByID(id: Int?): NotesEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNotes(notes: NotesEntity)

    @Update
    fun updateNotes(notes: NotesEntity)

    @Delete
    fun deleteNote(notes: NotesEntity)
}
