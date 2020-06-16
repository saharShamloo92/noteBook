package com.example.notebook

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import com.example.notebook.entity.Note

class CustomDialog(context: Context) : Dialog(context) {
    init {
        setCancelable(false)
    }

    var note: Note = TODO()
    override fun onCreate( savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        if (getArguments() != null) {
//            note = getArguments().getParcelable(note.EditDialog.EXTRA_KEY_NOTE)
//        }
    }
}