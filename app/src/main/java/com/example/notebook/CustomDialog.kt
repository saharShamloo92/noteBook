package com.example.notebook

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.example.notebook.entity.Note

class CustomDialog(context: Context) : DialogFragment() {
    val context2: Context = context
    private var resultCallback: ResultCallback? = null
    var note: Note = Note()

    init {
    }

    private val KEYNOTE = "key"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {

            arguments.let {
                note = it?.getParcelable(KEYNOTE)!!
            }

        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(context2)

        val viewDialog = LayoutInflater.from(context2).inflate(R.layout.edit_dialog, null)

        builder.setView(viewDialog)
        val btn_dialog_save: Button = viewDialog.findViewById(R.id.btn_dialog_save)
        val et_dialog_title: EditText = viewDialog.findViewById(R.id.et_dialog_title)
        val et_dialog_note: EditText = viewDialog.findViewById(R.id.et_dialog_note)
        val btn_dialog_cancel: Button = viewDialog.findViewById(R.id.btn_dialog_cancel)

        if (note != null) {
            et_dialog_title.setText(note.title)
            et_dialog_note.setText(note.note)
        }
        btn_dialog_save.setOnClickListener {

            if (!(et_dialog_title.text.toString().trim().length > 0))
                et_dialog_title.error = "fill title"
            else if (!(et_dialog_note.text.toString().trim().length > 0))
                et_dialog_note.error = "fill description"
            else {

                note.title = et_dialog_title.text.toString().trim()
                note.note = et_dialog_note.text.toString().trim()
                resultCallback?.onSave(note)
                dismiss()
            }

        }

        btn_dialog_cancel.setOnClickListener {
            dialog?.dismiss()
        }

        return builder.create()
    }

    fun setResultCallback(resultCallback: ResultCallback) {
        this.resultCallback = resultCallback
    }

    interface ResultCallback {
        fun onSave(note: Note)
    }

    fun newInstance(note: Note): CustomDialog {
        val bundle = Bundle()
        bundle.putParcelable(KEYNOTE, note as Parcelable)
        val fragment: CustomDialog = CustomDialog(context2)
        fragment.arguments = bundle
        return fragment
    }

}