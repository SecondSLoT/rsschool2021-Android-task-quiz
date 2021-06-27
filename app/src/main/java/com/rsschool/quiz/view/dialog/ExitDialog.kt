package com.rsschool.quiz.view.dialog

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.rsschool.quiz.R
import kotlin.system.exitProcess

class ExitDialog : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(getString(R.string.exit_dialog_title))
            .setMessage(getString(R.string.exit_dialog_message))
            .setPositiveButton(getString(R.string.yes)) { _, _ -> exitProcess(0) }
            .setNegativeButton(getString(R.string.no)) { dialog, _ -> dialog.cancel() }
            .create()

    companion object {
        const val TAG = "ExitDialog"
    }
}