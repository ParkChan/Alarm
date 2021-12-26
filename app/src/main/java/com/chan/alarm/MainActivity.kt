package com.chan.alarm

import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import androidx.activity.result.contract.ActivityResultContracts
import com.chan.alarm.common.ui.SnackbarUtil
import com.chan.alarm.databinding.ActivityMainBinding
import com.chan.ui.BaseActivity
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        val overlaysPermission = Settings.canDrawOverlays(this)
        if (!overlaysPermission) {
            SnackbarUtil.show(binding.root, getString(R.string.snack_bar_msg_runtime_permission))
            showPermissionCheckDialog()
        }
    }
    private val dialogBuilder: AlertDialog.Builder by lazy {
        AlertDialog.Builder(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestPermission()
    }

    private fun requestPermission() {
        if (!Settings.canDrawOverlays(this)) {
            showPermissionCheckDialog()
        }
    }

    private fun showPermissionCheckDialog() {
        dialogBuilder
            .setMessage(
                getString(
                    R.string.dialog_message_overlay_permission
                )
            )
            .setPositiveButton(
                getString(R.string.dialog_message_ok)
            ) { _, _ ->
                val intent =
                    Intent(
                        Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                        Uri.parse("package:$packageName")
                    )
                requestPermissionLauncher.launch(intent)
            }
            .setNegativeButton(
                getString(R.string.dialog_message_cancel)
            ) { _, _ ->
                finish()
            }.create().show()
    }
}