package com.example.notebook

import com.example.notebook.entity.Note

interface NoteViewEventHandler {
    fun onDeleteClicked(note: Note)
    fun onEditClicked(note: Note)
    fun getItemCount(count:Int)
}