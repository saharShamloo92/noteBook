package com.example.notebook.databae

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.notebook.entity.Note

class NoteRepository(application:Application) {
    val noteDao:NoteDao
    init {
        val noteDatabase=NoteDatabase.getDatabase(application)
        noteDao=noteDatabase!!.noteDao()
    }
    fun insertNote(note: Note){
        noteDao.insertNote(note)
    }

    fun getNotes(): List<Note> {
        return noteDao.getNotes()
    }
    fun deleteNote(note: Note){
        noteDao.deleteNote(note)
    }
    fun updateNote(note: Note){
        noteDao.updateNote(note)
    }
}