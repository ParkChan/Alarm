package com.chan.alarm.feature.settings

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.net.Uri
import androidx.activity.result.contract.ActivityResultContract
import androidx.core.net.toUri
import androidx.core.os.bundleOf

class RingtoneActivityContract : ActivityResultContract<Uri?, Uri?>() {

    override fun createIntent(context: Context, uri: Uri?): Intent {

        val intent = Intent(RingtoneManager.ACTION_RINGTONE_PICKER)

        intent.apply {
            bundleOf(
                RingtoneManager.EXTRA_RINGTONE_SHOW_SILENT to false,
                RingtoneManager.EXTRA_RINGTONE_SHOW_DEFAULT to true,
                RingtoneManager.EXTRA_RINGTONE_TYPE to RingtoneManager.TYPE_ALL
            )
        }
        uri?.let {
            intent.putExtra(RingtoneManager.EXTRA_RINGTONE_EXISTING_URI, it)
        }
        return intent
    }

    override fun parseResult(resultCode: Int, intent: Intent?): Uri {
        return if (resultCode == Activity.RESULT_OK) {
            intent?.getParcelableExtra(RingtoneManager.EXTRA_RINGTONE_PICKED_URI) ?: "".toUri()
        } else
            "".toUri()
    }
}