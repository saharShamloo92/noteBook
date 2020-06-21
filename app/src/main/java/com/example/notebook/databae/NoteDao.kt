package com.example.notebook.databae

import androidx.room.*
import com.example.notebook.entity.Note

@Dao
interface NoteDao {
    @Insert
    fun insertNote(note: Note)

    @Query("SELECT * from note_tbl")
    fun getNotes(): List<Note>

    @Delete
    fun deleteNote(note: Note)

    @Update
    fun updateNote(note:Note)
}