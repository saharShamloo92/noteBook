package com.example.notebook

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.example.notebook.entity.Note

class CustomDialog(context: Context) : DialogFragment() {
   private val context2: Context = context
    private var resultCallback: ResultCallback? = null
    var note: Note = Note()
    private val KEY = "key"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {

            arguments.let {
                note = it?.getParcelable(KEY)!!
            }

        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(context2)

        val viewDialog = LayoutInflater.from(context2).inflate(R.layout.edit_dialog, null)

        builder.setView(viewDialog)
        val btnDialogSave: Button = viewDialog.findViewById(R.id.btn_dialog_save)
        val etDialogTitle: EditText = viewDialog.findViewById(R.id.et_dialog_title)
        val etDialogNote: EditText = viewDialog.findViewById(R.id.et_dialog_note)
        val btnDialogCancel: Button = viewDialog.findViewById(R.id.btn_dialog_cancel)

        if (note.title.isNotBlank() || note.title.isNotEmpty()) {
            etDialogTitle.setText(note.title)
            etDialogNote.setText(note.note)
        }
        btnDialogSave.setOnClickListener {

            if (etDialogTitle.text.toString().trim().isEmpty())
                etDialogTitle.error = "fill title"
            else if (etDialogNote.text.toString().trim().isEmpty())
                etDialogNote.error = "fill description"
            else {

                note.title = etDialogTitle.text.toString().trim()
                note.note = etDialogNote.text.toString().trim()
                resultCallback?.onSave(note)
                dismiss()
            }

        }

        btnDialogCancel.setOnClickListener {
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
        bundle.putParcelable(KEY, note as Parcelable)
        val fragment = CustomDialog(context2)
        fragment.arguments = bundle
        return fragment
    }

}