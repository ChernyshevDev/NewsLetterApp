package com.chernyshev.newsletterapp.presentation.splash

import android.os.Bundle
import android.view.View
import com.chernyshev.newsletterapp.R
import com.chernyshev.newsletterapp.presentation.base.BaseFragment
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import androidx.fragment.app.viewModels
import com.chernyshev.newsletterapp.databinding.FragmentSplashBinding
import com.chernyshev.newsletterapp.presentation.base.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashFragment : BaseFragment(R.layout.fragment_splash) {
    private val binding by viewBinding<FragmentSplashBinding>()
    private val viewModel by viewModels<SplashViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        performFakeLoading()
    }

    override fun initObservers() {
        viewModel.subscribeToCommand(viewLifecycleOwner) { command ->
            when (command) {
                Command.NavigateToLanguageSelector -> navController.navigate(
                    SplashFragmentDirections.navigateToLanguageSelector()
                )
                Command.NavigateHome -> navController.navigate(SplashFragmentDirections.navigateHome())
            }
        }
    }

    private fun performFakeLoading() {
        MainScope().launch {
            delay(2000)
            viewModel.sendEvent(Event.FinishedFakeLoading)
        }
    }
}