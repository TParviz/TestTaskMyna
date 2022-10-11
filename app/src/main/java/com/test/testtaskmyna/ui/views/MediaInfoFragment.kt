package com.test.testtaskmyna.ui.views

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context.CLIPBOARD_SERVICE
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.test.testtaskmyna.R
import com.test.testtaskmyna.databinding.FragmentMediaInfoBinding
import com.test.testtaskmyna.ui.views.GalleryDetailsFragment.Companion.LINK_NAME_BUNDLE_KEY
import com.test.testtaskmyna.utilities.showToast

class MediaInfoFragment : Fragment() {

    private var _binding: FragmentMediaInfoBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMediaInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()

        val linkName = arguments?.getString(LINK_NAME_BUNDLE_KEY)
        binding.tvLink.text = linkName
    }

    private fun initView() = with(binding) {

        btnCopy.setOnClickListener {
            copyLinkText()
        }

        btnShare.setOnClickListener {
            showShareDialog()
        }
    }

    private fun copyLinkText() {
        val textToCopy = binding.tvLink.text
        val clipboardManager = context?.getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
        val clipData = ClipData.newPlainText("text", textToCopy)
        clipboardManager.setPrimaryClip(clipData)
        showToast(requireActivity(), getString(R.string.copy_text))
    }

    private fun showShareDialog() {
        val intent = Intent()
        intent.action = Intent.ACTION_SEND
        intent.putExtra(Intent.EXTRA_TEXT, binding.tvLink.text)
        intent.type = "text/plain"
        startActivity(Intent.createChooser(intent, "Share To:"))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}