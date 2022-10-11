package com.test.testtaskmyna.ui.views

import android.app.ProgressDialog
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.test.testtaskmyna.R
import com.test.testtaskmyna.adapters.GalleryAdapter
import com.test.testtaskmyna.callbacks.OnItemClickListener
import com.test.testtaskmyna.data.model.MediaModel
import com.test.testtaskmyna.databinding.FragmentGalleryDetailsBinding
import com.test.testtaskmyna.ui.viewmodels.GalleryDetailsViewModel
import com.test.testtaskmyna.utilities.showToast
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import java.io.File
import java.util.*

class GalleryDetailsFragment : Fragment(), OnItemClickListener {

    private lateinit var viewModel: GalleryDetailsViewModel

    private var _binding: FragmentGalleryDetailsBinding? = null
    private val binding get() = _binding!!

    private lateinit var mediaList: Flow<List<MediaModel>>
    private lateinit var mediaListModel: List<MediaModel>
    private lateinit var galleryAdapter: GalleryAdapter

    private var progressDialog: ProgressDialog? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGalleryDetailsBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this)[GalleryDetailsViewModel::class.java]
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initData()
    }

    private fun initView() {
        val directoryName = arguments?.getString("directoryName").toString()
        (activity as AppCompatActivity).supportActionBar?.title = directoryName

        lifecycleScope.launchWhenCreated {
            mediaList = viewModel.getMediaFromDirectory(directoryName)
        }
    }

    private fun initData() {
        lifecycleScope.launchWhenCreated {
            mediaList.collectLatest { list ->
                mediaListModel = list
                mediaListModel.size
                binding.galleryRecyclerView.layoutManager = GridLayoutManager(activity, 4)

                val reversedList = mediaListModel.reversed()

                galleryAdapter =
                    GalleryAdapter(requireActivity(), reversedList, this@GalleryDetailsFragment)
                binding.galleryRecyclerView.adapter = galleryAdapter

            }
        }
    }

    override fun onItemClick(mediaModel: Any) {
        val model = mediaModel as MediaModel
        uploadImage(model.thumbUri)
    }

    private fun uploadImage(imageUri: String) {
        progressDialog = ProgressDialog(requireContext())
        progressDialog?.setTitle(getString(R.string.uploading_file))
        progressDialog?.show()

        val file = Uri.fromFile(File(imageUri))
        val storageReference: StorageReference =
            FirebaseStorage.getInstance().reference.child("images/${UUID.randomUUID()}")
        val uploadFile = storageReference.putFile(file)
        uploadFile.addOnSuccessListener {
            showToast(requireActivity(), getString(R.string.successfully_uploaded))
            uploadFile.continueWithTask { task ->
                if (!task.isSuccessful) {
                    task.exception?.let {
                        throw it
                    }
                }
                storageReference.downloadUrl
            }.addOnCompleteListener { aSuccessTask ->
                if (aSuccessTask.isSuccessful) {
                    progressDialog?.dismiss()
                    val downloadUri = aSuccessTask.result
                    val bundle = bundleOf(LINK_NAME_BUNDLE_KEY to downloadUri.toString())
                    findNavController().navigate(
                        R.id.action_GalleryDetailsFragment_to_MediaInfoFragment, bundle
                    )
                }
            }
        }.addOnFailureListener {
            progressDialog?.dismiss()
            showToast(requireActivity(), getString(R.string.failed_upload))
        }
    }

    companion object {
        const val LINK_NAME_BUNDLE_KEY = "linkNameBundleKey"
    }
}