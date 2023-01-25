package com.bill.videosaver.home

import android.os.Bundle
import android.view.View
import com.bill.videosaver.R
import com.bill.videosaver.base.BaseFragment
import com.bill.videosaver.databinding.FragmentFirstBinding
import com.redmadrobot.extensions.viewbinding.viewBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : BaseFragment<HomeViewModel>(R.layout.fragment_first) {

    override val viewModel: HomeViewModel by viewModel()

    private val binding: FragmentFirstBinding by viewBinding()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnPaste.setOnClickListener {
            viewModel.loadDownloadableUrls(listOf("1", "2", "3"))
        }
        viewModel.viewState.observe {
            binding.textView.text = ""
            println(it.files.size)
            binding.textView.text = it.files.joinToString(separator = "\n") { it.toString() }
        }
    }
}