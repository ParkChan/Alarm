package com.chan.ui

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ViewDataBinding

abstract class BaseActivity<VDB : ViewDataBinding>(
    private val inflater: (LayoutInflater) -> VDB
) : AppCompatActivity() {

    protected lateinit var binding: VDB
        private set

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBinding()
    }

    private fun initBinding(){
        binding = inflater(layoutInflater).apply {
            lifecycleOwner = this@BaseActivity
        }
        setContentView(binding.root)
    }

}
