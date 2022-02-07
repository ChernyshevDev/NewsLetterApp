package com.chernyshev.newsletterapp.presentation.home

import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.chernyshev.newsletterapp.R
import com.chernyshev.newsletterapp.databinding.FragmentHomeBinding
import com.chernyshev.newsletterapp.presentation.base.BaseFragment
import com.chernyshev.newsletterapp.presentation.base.viewBinding
import com.chernyshev.newsletterapp.presentation.utils.extensions.showToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment(R.layout.fragment_home) {
    private val binding by viewBinding<FragmentHomeBinding>()
    private val viewModel by viewModels<HomeViewModel>()
    private val newsAdapter = NewsAdapter()

    override fun onPause() {
        super.onPause()
        viewModel.sendEvent(Event.PausedFragment)
    }

    override fun onResume() {
        super.onResume()
        viewModel.sendEvent(Event.ResumedFragment)
    }

    override fun initClickListeners() = with(binding) {
        newsAdapter.setOnItemClick {
            viewModel.sendEvent(Event.ClickedNewsItem(it))
        }
        toolbar.onLanguageChanged {
            viewModel.sendEvent(Event.ChangedLanguage)
        }
    }

    override fun initViews() = with(binding) {
        newsRecycler.adapter = newsAdapter
    }

    override fun initObservers() {
        viewModel.subscribeToCommand(viewLifecycleOwner) { command ->
            when (command) {
                is Command.DisplayError -> showToast(
                    command.errorMessage ?: getString(R.string.unknown_error)
                )
                is Command.UpdateNews -> newsAdapter.updateItems(command.news)
                is Command.ShowDescriptionDialog -> navController.navigate(
                    HomeFragmentDirections.showNewsDialog(
                        command.newsItem
                    )
                )
            }
        }

        viewModel.subscribeToStateUpdates(viewLifecycleOwner) {
            displayShimmerLoading(it.shouldShowShimmer)
        }
    }

    private fun displayShimmerLoading(isLoadingVisible: Boolean) = with(binding) {
        shimmer.apply {
            if (isLoadingVisible) {
                showShimmer(true)
            } else {
                hideShimmer()
            }
        }
        fakeNewsLayout.root.isVisible = isLoadingVisible
        newsRecycler.isVisible = !isLoadingVisible
    }
}