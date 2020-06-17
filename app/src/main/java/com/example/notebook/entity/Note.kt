package com.example.notebook.entity

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "note_tbl")
data class Note (var title:String="",
                    var note:String=""):Parcelable  {
    @PrimaryKey(autoGenerate = true)
    var noteID:Int=0

 constructor() : this("","") {

    }
    private constructor(p: Parcel) : this(
        title = p.readString()!!,
        note = p.readString()!!
    )
    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(title)
        dest.writeString(note)
    }

    override fun describeContents() = 0

    companion object {
        @JvmField val CREATOR = object : Parcelable.Creator<Note> {
            override fun createFromParcel(parcel: Parcel) = Note(parcel)

            override fun newArray(size: Int) = arrayOfNulls<Note>(size)
        }
    }
}