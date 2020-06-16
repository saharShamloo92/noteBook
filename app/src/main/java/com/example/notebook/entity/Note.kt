package com.example.notebook.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "note_tbl")
data class Note (var title:String="",
                    var note:String="")   {
    @PrimaryKey(autoGenerate = true)
    var noteID:Int=0
}