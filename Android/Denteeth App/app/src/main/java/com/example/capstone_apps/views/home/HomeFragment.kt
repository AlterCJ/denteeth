package com.example.capstone_apps.views.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.capstone_apps.R
import com.example.capstone_apps.data.remote.response.DataItem
import com.example.capstone_apps.databinding.FragmentHomeBinding
import com.example.capstone_apps.helper.Key
import com.example.capstone_apps.helper.ResultResponse
import com.example.capstone_apps.viewmodels.ArticleViewModel
import com.example.capstone_apps.viewmodels.ViewModelFactory
import com.example.capstone_apps.views.adapter.ListArticleAdapter
import com.example.capstone_apps.views.detail.DetailArticleActivity

class HomeFragment : Fragment() {

  private lateinit var binding: FragmentHomeBinding
  private lateinit var articleViewModel: ArticleViewModel

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
    return inflater.inflate(R.layout.fragment_home, container, false)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    binding = FragmentHomeBinding.bind(view)

    binding.rvListArticle.layoutManager = LinearLayoutManager(activity)
    articleViewModel = obtainMedicViewModel(requireActivity())
    articleViewModel.getAllListArticle().observe(viewLifecycleOwner) { result ->
      if(result != null) {
        when(result) {
          is ResultResponse.Loading -> {}

          is ResultResponse.Success -> {
            Toast.makeText(activity, "success get data", Toast.LENGTH_SHORT).show()
            setListArticle(result.data.data)
          }

          is ResultResponse.Error -> {
            Toast.makeText(activity, "testing", Toast.LENGTH_SHORT).show()
          }
        }
      }
    }
  }


  private fun setListArticle(dataArticle: List<DataItem>) {
    val adapter = ListArticleAdapter(dataArticle)
    binding.rvListArticle.adapter = adapter
    adapter.setOnClickCallback(object : ListArticleAdapter.OnItemClickCallback {
      override fun onItemClicked(list: DataItem) {
        val intent = Intent(requireActivity(), DetailArticleActivity::class.java)
        intent.putExtra(Key.ID, list.id)
        startActivity(intent)
      }
    })
  }

  private fun obtainMedicViewModel(requireActivity: FragmentActivity): ArticleViewModel {
    val factory = ViewModelFactory.getInstance(requireActivity)
    return ViewModelProvider(requireActivity, factory).get(ArticleViewModel::class.java)
  }
}