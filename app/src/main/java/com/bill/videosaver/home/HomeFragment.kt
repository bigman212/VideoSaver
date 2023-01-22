package com.bill.videosaver.home

import android.os.Bundle
import android.view.View
import com.bill.videosaver.base.BaseFragment
import com.bill.videosaver.databinding.FragmentFirstBinding
import com.redmadrobot.extensions.viewbinding.viewBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : BaseFragment<HomeViewModel>() {

    override val viewModel: HomeViewModel by viewModel()

    private val binding: FragmentFirstBinding by viewBinding()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.viewState.observe {
        }
    }
}