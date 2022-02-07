package com.chernyshev.newsletterapp.presentation.language_selector

import androidx.fragment.app.viewModels
import com.chernyshev.newsletterapp.R
import com.chernyshev.newsletterapp.databinding.FragmentLanguageSelectorBinding
import com.chernyshev.newsletterapp.domain.entity.Language
import com.chernyshev.newsletterapp.presentation.base.BaseFragment
import com.chernyshev.newsletterapp.presentation.base.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LanguageSelectorFragment : BaseFragment(R.layout.fragment_language_selector) {
    private val viewModel by viewModels<LanguageSelectorViewModel>()
    private val binding by viewBinding<FragmentLanguageSelectorBinding>()

    override fun initClickListeners() = with(binding) {
        english.setOnClickListener {
            viewModel.sendEvent(Event.SelectedLanguage(Language.English))
        }
        russian.setOnClickListener {
            viewModel.sendEvent(Event.SelectedLanguage(Language.Russian))
        }
        deutsch.setOnClickListener {
            viewModel.sendEvent(Event.SelectedLanguage(Language.Deutsch))
        }
    }

    override fun initObservers() {
        viewModel.subscribeToCommand(viewLifecycleOwner) { command ->
            when (command) {
                is Command.NavigateHome -> navController
                    .navigate(LanguageSelectorFragmentDirections.actionLanguageSelectorFragmentToHomeFragment())
            }
        }
    }
}