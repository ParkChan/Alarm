package com.chan.alarm.feature.alarmlist

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import com.chan.alarm.R
import com.chan.alarm.common.ui.viewmodel.AlarmViewModel
import com.chan.alarm.databinding.FragmentAlarmListBinding
import com.chan.alarm.feature.database.domain.data.Alarm
import com.chan.ui.BR
import com.chan.ui.BaseFragment
import com.chan.ui.adapter.BaseListAdapter
import com.chan.ui.livedata.observeEvent

class AlarmListFragment : BaseFragment<FragmentAlarmListBinding>(
    FragmentAlarmListBinding::inflate
) {

    private val alarmViewModel by activityViewModels<AlarmViewModel>()
    private val listAdapter: BaseListAdapter<Alarm> by lazy {
        BaseListAdapter(
            layoutResourceId = R.layout.rv_alarm_item,
            viewHolderBindingId = BR.alarm,
            viewModel = mapOf(BR.alarmViewModel to alarmViewModel),
            object : DiffUtil.ItemCallback<Alarm>() {
                override fun areItemsTheSame(
                    oldItem: Alarm,
                    newItem: Alarm
                ): Boolean =
                    oldItem.id == newItem.id

                override fun areContentsTheSame(
                    oldItem: Alarm, newItem: Alarm
                ): Boolean = oldItem == newItem
            }
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initTitle()
        initRecyclerView()
        initViewModel()
        initListener()
        initViewModelObserve()
        initViewData()
    }

    private fun initTitle() {
        binding.includeTitle.tvTitle.text = getString(R.string.fragment_label_alarm_list)
    }

    private fun initRecyclerView() {
        binding.rvAlarm.adapter = listAdapter
    }

    private fun initViewModel() {
        binding.alarmViewModel = alarmViewModel
    }

    private fun initListener() {
        binding.btnAddReminder.setOnClickListener {
            val action =
                AlarmListFragmentDirections.actionAlarmListFragmentToSettingsFragmentGraph()
            it.findNavController().navigate(action)
        }
    }

    private fun initViewModelObserve() {
        alarmViewModel.alarms.observeEvent(
            lifecycleOwner = viewLifecycleOwner, observer = {
                listAdapter.submitList(it)
            })
    }

    private fun initViewData() {
        alarmViewModel.selectAlarmList()
    }

}