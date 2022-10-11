package com.test.testtaskmyna.ui.views

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.test.testtaskmyna.R
import com.test.testtaskmyna.databinding.FragmentDirectoryBinding

class DirectoryFragment : Fragment() {

    private val PICK_FROM_GALLERY = 1
    private var _binding: FragmentDirectoryBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDirectoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        binding.buttonOpenGallery.setOnClickListener {
            if (activity?.let { fragmentActivity ->
                    ActivityCompat.checkSelfPermission(
                        fragmentActivity, Manifest.permission.READ_EXTERNAL_STORAGE
                    )
                } != PackageManager.PERMISSION_GRANTED
            ) {
                activity?.let { fragmentActivity ->
                    ActivityCompat.requestPermissions(
                        fragmentActivity,
                        arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                        PICK_FROM_GALLERY
                    )
                }
            } else {
                findNavController().navigate(R.id.action_DirectoryFragment_to_GalleryFragment)
            }
        }
    }
}