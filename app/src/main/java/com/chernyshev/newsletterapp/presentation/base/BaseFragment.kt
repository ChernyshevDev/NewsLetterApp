package com.chernyshev.newsletterapp.presentation.base

import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController

abstract class BaseFragment(@LayoutRes layoutId: Int) : Fragment(layoutId) {

    val navController: NavController
        get() {
            return findNavController()
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObservers()
        initViews()
        initClickListeners()
    }

    open fun initObservers() {}
    open fun initViews() {}
    open fun initClickListeners() {}
}