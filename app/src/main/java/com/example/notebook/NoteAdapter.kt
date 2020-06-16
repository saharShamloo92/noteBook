package com.example.notebook

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.notebook.entity.Note

class NoteAdapter(noteViewEventHandler: NoteViewEventHandler)
    : RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {

    var noteList= arrayListOf<Note>()
    val noteViewEventHandler=noteViewEventHandler

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_row, parent, false)
        return NoteViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        noteViewEventHandler.getItemCount(noteList.size)
        return noteList.size
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note:Note= noteList[position]
        holder.bindNote(note)
    }

    inner class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var titleTv: TextView? = null
        private var descriptionTv: TextView? = null
        private var deleteIv: ImageView? = null
        private var editIv: ImageView? = null
        init {
            titleTv=itemView.findViewById(R.id.tv_item_title)
            descriptionTv=itemView.findViewById(R.id.tv_item_note)
            deleteIv=itemView.findViewById(R.id.iv_item_delete)
            editIv=itemView.findViewById(R.id.iv_item_edit)
        }
        fun bindNote(note:Note){
            titleTv?.setText(note.title+"")
            descriptionTv?.setText(note.note+"")
            deleteIv?.setOnClickListener { noteViewEventHandler.onDeleteClicked(note) }
            editIv?.setOnClickListener { noteViewEventHandler.onEditClicked(note) }
//            deleteIv.setOnClickListener(object : View.OnClickListener {
//                open fun onClick(v: View) {
//                    noteViewEventCallback.onDeleteClicked(note)
//                }
//            })
//            editIv.setOnClickListener(object : View.OnClickListener {
//                open fun onClick(v: View) {
//                    noteViewEventCallback.onEditClicked(note)
//                }
//            })
        }
    }
    fun addNotes(noteList:ArrayList<Note>){
        this.noteList=noteList
        notifyDataSetChanged()
    }
    fun addNote(note: Note){
        noteList.add(note)
        notifyItemInserted(noteList.indexOf(note))
    }
    fun updateNote(note: Note) {
        val index = noteList.indexOf(note)
        Log.i("indexCheck",index.toString())
        noteList[index] = note
        notifyItemChanged(index)
    }

    fun deleteNote(note: Note) {
        val index = noteList.indexOf(note)
        noteList.removeAt(index)
        notifyItemRemoved(index)
    }
}