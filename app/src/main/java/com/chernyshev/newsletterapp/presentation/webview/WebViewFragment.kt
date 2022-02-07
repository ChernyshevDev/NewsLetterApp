package com.chernyshev.newsletterapp.presentation.webview

import android.webkit.WebChromeClient
import android.webkit.WebViewClient
import androidx.navigation.fragment.navArgs
import com.chernyshev.newsletterapp.R
import com.chernyshev.newsletterapp.databinding.FragmentWebviewBinding
import com.chernyshev.newsletterapp.presentation.base.BaseFragment
import com.chernyshev.newsletterapp.presentation.base.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WebViewFragment : BaseFragment(R.layout.fragment_webview) {
    private val args by navArgs<WebViewFragmentArgs>()
    private val binding by viewBinding<FragmentWebviewBinding>()

    override fun initClickListeners() = with(binding) {
        toolbar.onBackClicked { requireActivity().onBackPressed() }
    }

    override fun initViews() = with(binding) {
        webView.apply {
            settings.javaScriptEnabled = true
            isVerticalScrollBarEnabled = true
            webViewClient = object : WebViewClient() {}
            webChromeClient = object : WebChromeClient() {}

            loadUrl(args.url)
        }
        Unit
    }
}