package com.example.notebook

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.notebook.databinding.ActivityMainBinding
import com.example.notebook.entity.Note
import com.example.notebook.model.MainActivityViewModel

class MainActivity : AppCompatActivity(),NoteViewEventHandler {

    val viewModel: MainActivityViewModel by lazy {
        ViewModelProvider(this).get(MainActivityViewModel::class.java)
    }
    private lateinit var  mainBinding: ActivityMainBinding

    val noteAdapter:NoteAdapter= NoteAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        val mainBinding: ActivityMainBinding =
            mainBinding=  ActivityMainBinding.inflate(layoutInflater)

        val view: View = mainBinding.root
        setContentView(view)

        //set Recycler
        viewModel.getNotes().observe(this, Observer {
            noteAdapter.addNotes(it as ArrayList<Note>)
//            for(i in it)
//                Log.i("indexCheck","main:   "+i.noteID.toString())
                })
        mainBinding.rvNote.apply {
            layoutManager = LinearLayoutManager(this@MainActivity,LinearLayoutManager.VERTICAL, false)
            adapter = noteAdapter
        }

        //set dialog
        val alertDialogBuilder = AlertDialog.Builder(this)
        val viewDialog = LayoutInflater.from(applicationContext).inflate(R.layout.edit_dialog, null)
        alertDialogBuilder.setView(viewDialog)
        val dialog=alertDialogBuilder.create()
        val btn_dialog_save: Button =viewDialog.findViewById(R.id.btn_dialog_save)
        val et_dialog_title: EditText =viewDialog.findViewById(R.id.et_dialog_title)
        val et_dialog_note: EditText =viewDialog.findViewById(R.id.et_dialog_note)
        et_dialog_title.setText("")
        et_dialog_note.setText("")
        btn_dialog_save.setOnClickListener {
            if (!(et_dialog_title.text.toString().trim().length>0))
                et_dialog_title.setError("fill the blank")
            else if (!(et_dialog_note.text.toString().trim().length>0))
                et_dialog_note.setError("fill the blank")
            else{
                viewModel.insetNote(Note(et_dialog_title.text.toString().trim(),et_dialog_note.text.toString().trim()))
                noteAdapter.addNote(Note(et_dialog_title.text.toString().trim(),et_dialog_note.text.toString().trim()))
                dialog.dismiss()
            }

        }
        val btn_dialog_cancel:Button=viewDialog.findViewById(R.id.btn_dialog_cancel)

        //set click
        btn_dialog_cancel.setOnClickListener { dialog.dismiss() }
        mainBinding.ivActionbtn.setOnClickListener {   dialog.show()}
        mainBinding.btnMainEmpty.setOnClickListener {   dialog.show()}



    }

    override fun onDeleteClicked(note: Note) {
        noteAdapter.deleteNote(note)
        viewModel.deleteNote(note)
    }

    override fun onEditClicked(note: Note) {
        Toast.makeText(this,note.title,Toast.LENGTH_LONG).show()
        val alertDialogBuilder = AlertDialog.Builder(this)
        val viewDialog = LayoutInflater.from(applicationContext).inflate(R.layout.edit_dialog, null)
        alertDialogBuilder.setView(viewDialog)
        val dialog=alertDialogBuilder.create()
        val btn_dialog_save: Button =viewDialog.findViewById(R.id.btn_dialog_save)
        val et_dialog_title: EditText =viewDialog.findViewById(R.id.et_dialog_title)
        val et_dialog_note: EditText =viewDialog.findViewById(R.id.et_dialog_note)
        et_dialog_title.setText(note.title)
        et_dialog_note.setText(note.note)
        dialog.show()
        btn_dialog_save.setOnClickListener {
            if (!(et_dialog_title.text.toString().trim().length>0))
                et_dialog_title.setError("fill the blank")
            else if (!(et_dialog_note.text.toString().trim().length>0))
                et_dialog_note.setError("fill the blank")
            else{

                note.note=et_dialog_note.text.toString().trim()
                note.title=et_dialog_title.text.toString().trim()
                viewModel.updateNote(note)
                noteAdapter.updateNote(note)
                dialog.dismiss()
            }

        }
        val btn_dialog_cancel:Button=viewDialog.findViewById(R.id.btn_dialog_cancel)
        btn_dialog_cancel.setOnClickListener { dialog.dismiss() }

    }

    override fun getItemCount(count: Int) {
        if (count > 0) {
            mainBinding.llEmtystate.setVisibility(View.GONE)
        } else {
            mainBinding.llEmtystate.setVisibility(View.VISIBLE)
        }
        if (mainBinding.llEmtystate.getVisibility() == View.VISIBLE) {
            mainBinding.ivActionbtn.setVisibility(View.GONE)
        } else {
            mainBinding.ivActionbtn.setVisibility(View.VISIBLE)
        }
    }
}
