package com.chan.alarm

import android.os.Bundle
import android.provider.Settings.ACTION_MANAGE_OVERLAY_PERMISSION
import androidx.activity.result.contract.ActivityResultContracts
import com.chan.alarm.common.ui.SnackbarUtil
import com.chan.alarm.databinding.ActivityMainBinding
import com.chan.ui.BaseActivity
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            Timber.d("isGranted")
        } else {
            SnackbarUtil.show(binding.root, getString(R.string.snack_bar_msg_runtime_permission))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestPermission()
    }

    private fun requestPermission() {
        requestPermissionLauncher.launch(ACTION_MANAGE_OVERLAY_PERMISSION)
    }
}