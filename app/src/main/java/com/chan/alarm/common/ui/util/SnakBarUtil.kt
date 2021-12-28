package com.chan.alarm.common.ui.util

import android.view.View
import com.google.android.material.snackbar.Snackbar

object SnackbarUtil {
    fun show(view: View, message: String) {
        Snackbar.make(
            view,
            message,
            Snackbar.LENGTH_SHORT
        ).show()
    }
}