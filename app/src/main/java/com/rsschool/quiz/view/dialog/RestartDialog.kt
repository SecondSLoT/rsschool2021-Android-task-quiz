package com.rsschool.quiz.view.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.rsschool.quiz.R

class RestartDialog : DialogFragment() {

    private var dialogListener: NoticeDialogListener? = null

    interface NoticeDialogListener {
        fun onPositiveButtonClicked(dialog: DialogFragment)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is NoticeDialogListener) {
            dialogListener = context
        } else {
            throw RuntimeException("$context must implement NoticeDialogListener")
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(getString(R.string.restart_dialog_title))
            .setMessage(getString(R.string.restart_dialog_message))
            .setPositiveButton(getString(R.string.yes)) { _, _ ->
                dialogListener?.onPositiveButtonClicked(this)
            }
            .setNegativeButton(getString(R.string.no)) { dialog, _ -> dialog.cancel() }
            .create()

    companion object {
        const val TAG = "RestartDialog"
    }
}