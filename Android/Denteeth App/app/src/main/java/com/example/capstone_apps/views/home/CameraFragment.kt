package com.example.capstone_apps.views.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.capstone_apps.R
import com.example.capstone_apps.databinding.FragmentCameraBinding

class CameraFragment : Fragment() {

  private lateinit var binding: FragmentCameraBinding

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
    return inflater.inflate(R.layout.fragment_camera, container, false)
  }

}