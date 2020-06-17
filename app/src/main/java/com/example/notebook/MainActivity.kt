package com.example.notebook

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.notebook.databinding.ActivityMainBinding
import com.example.notebook.entity.Note
import com.example.notebook.model.MainActivityViewModel
import java.lang.reflect.Array.newInstance
import javax.xml.datatype.DatatypeFactory.newInstance

class MainActivity : AppCompatActivity(), NoteViewEventHandler {

    val viewModel: MainActivityViewModel by lazy {
        ViewModelProvider(this).get(MainActivityViewModel::class.java)
    }
    private lateinit var mainBinding: ActivityMainBinding

    val noteAdapter: NoteAdapter = NoteAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        val mainBinding: ActivityMainBinding =
        mainBinding = ActivityMainBinding.inflate(layoutInflater)

        val view: View = mainBinding.root
        setContentView(view)

        //set Recycler
        viewModel.getNotes().observe(this, Observer {
            noteAdapter.addNotes(it as ArrayList<Note>)
//            for(i in it)
//                Log.i("indexCheck","main:   "+i.noteID.toString())
        })
        mainBinding.rvNote.apply {
            layoutManager =
                LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
            adapter = noteAdapter
        }

        //set dialog
        val editDialog = CustomDialog(this)
        editDialog.setResultCallback(object : CustomDialog.ResultCallback{
          override fun onSave(note: Note) {
              viewModel.insetNote(note)
              noteAdapter.addNote(note)
            }
        })

        mainBinding.ivActionbtn.setOnClickListener {
            editDialog.show(supportFragmentManager, null)
        }
        mainBinding.btnMainEmpty.setOnClickListener { editDialog.show(supportFragmentManager, null) }


    }

    override fun onDeleteClicked(note: Note) {
        noteAdapter.deleteNote(note)
        viewModel.deleteNote(note)
    }

    override fun onEditClicked(note: Note) {
        val editDialog: CustomDialog = CustomDialog(this).newInstance(note)
        editDialog.setCancelable(true)
        editDialog.setResultCallback(object : CustomDialog.ResultCallback{
            override fun onSave(note: Note) {
                viewModel.updateNote(note)
                noteAdapter.updateNote(note)
            }
        })
        editDialog.show(supportFragmentManager, null)
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
