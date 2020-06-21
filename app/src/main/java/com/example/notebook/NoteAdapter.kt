package com.example.notebook

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.notebook.entity.Note

class NoteAdapter(val noteViewEventHandler: NoteViewEventHandler)
    : RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {

    private var noteList= arrayListOf<Note>()
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
            titleTv?.text = note.title
            descriptionTv?.text = note.note
            deleteIv?.setOnClickListener { noteViewEventHandler.onDeleteClicked(note) }
            editIv?.setOnClickListener { noteViewEventHandler.onEditClicked(note) }
        }
    }
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