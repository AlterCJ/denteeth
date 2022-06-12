package com.example.capstone_apps.views.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.capstone_apps.R
import com.example.capstone_apps.data.remote.response.DataHistory
import com.example.capstone_apps.databinding.FragmentHistoryBinding
import com.example.capstone_apps.viewmodels.HistoryViewModel
import com.example.capstone_apps.viewmodels.PreferenceViewModel
import com.example.capstone_apps.viewmodels.ViewModelFactory
import com.example.capstone_apps.views.adapter.ListHistoryAdapter


class HistoryFragment : Fragment() {
  private lateinit var binding: FragmentHistoryBinding
  private lateinit var historyViewModel: HistoryViewModel
  private lateinit var preferenceViewModel: PreferenceViewModel

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    // Inflate the layout for this fragment
    return inflater.inflate(R.layout.fragment_history, container, false)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    binding = FragmentHistoryBinding.bind(view)
    binding.rvHistory.layoutManager = LinearLayoutManager(activity)
    historyViewModel = obtainHistoryViewModel(requireActivity())
    preferenceViewModel = obtainPreferenceViewModel(requireActivity())

    historyViewModel.getALlHistory(preferenceViewModel.getToken().token.toString())
    historyViewModel.getResult().observe(requireActivity()) {
      setListHistory(it.data)
    }
    historyViewModel.getError().observe(requireActivity()) {}
  }

  private fun setListHistory(data: List<DataHistory>) {
    val adapter = ListHistoryAdapter(data)
    binding.rvHistory.adapter = adapter
  }

  private fun obtainHistoryViewModel(requireActivity: FragmentActivity): HistoryViewModel {
    val factory = ViewModelFactory.getInstance(requireActivity)
    return ViewModelProvider(requireActivity, factory).get(HistoryViewModel::class.java)
  }

  private fun obtainPreferenceViewModel(requireActivity: FragmentActivity): PreferenceViewModel {
    val factory = ViewModelFactory.getInstance(requireActivity)
    return ViewModelProvider(requireActivity, factory).get(PreferenceViewModel::class.java)
  }
}