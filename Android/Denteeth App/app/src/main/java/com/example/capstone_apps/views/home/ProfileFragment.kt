package com.example.capstone_apps.views.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.capstone_apps.R
import com.example.capstone_apps.databinding.FragmentProfileBinding


class ProfileFragment : Fragment() {

  private lateinit var binding: FragmentProfileBinding

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
    return inflater.inflate(R.layout.fragment_profile, container, false)
  }
}