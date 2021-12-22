package com.chan.ui.adapter

import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter

class BaseListAdapter<ITEM : Any>(
    @LayoutRes private val layoutResourceId: Int,
    private val viewHolderBindingId: Int,
    private val viewModel: Map<Int, ViewModel>,
    private val diffUtil: DiffUtil.ItemCallback<ITEM>
) : ListAdapter<ITEM, BaseViewHolder>(diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder =
        BaseViewHolder(parent, layoutResourceId, viewHolderBindingId, viewModel)

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.bind(item = getItem(position))
    }
}
