package com.example.notebook.model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.notebook.databae.NoteRepository
import com.example.notebook.entity.Note
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivityViewModel (application:Application):AndroidViewModel(application){
    private val noteRepository: NoteRepository
    init {
        noteRepository= NoteRepository(application)
    }
    private val coroutine = CoroutineScope(Dispatchers.IO)

    fun insetNote(note:Note){
        coroutine.launch { noteRepository.insertNote(note) }
    }
    fun getNotes():MutableLiveData<List<Note>>{
        val noteList=MutableLiveData<List<Note>>()

        coroutine.launch {
            val noteFromR =noteRepository.getNotes()
            noteList.postValue(noteFromR)

        }
        return noteList
    }

    fun updateNote(note: Note){
        coroutine.launch { noteRepository.updateNote(note) }
    }
    fun deleteNote(note: Note){
        coroutine.launch { noteRepository.deleteNote(note) }
    }

}