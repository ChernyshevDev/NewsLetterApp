package com.chernyshev.newsletterapp.presentation.home.news_dialog

import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.chernyshev.newsletterapp.R
import com.chernyshev.newsletterapp.databinding.FragmentNewsDialogBinding
import com.chernyshev.newsletterapp.presentation.base.viewBinding
import com.chernyshev.newsletterapp.presentation.utils.extensions.loadImage
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewsDialogFragment : DialogFragment(R.layout.fragment_news_dialog) {
    private val binding by viewBinding<FragmentNewsDialogBinding>()
    private val args by navArgs<NewsDialogFragmentArgs>()

    override fun onStart() {
        super.onStart()

        dialog?.window?.apply {
            attributes?.windowAnimations = R.anim.fade_in
            setLayout(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT
            )
            setBackgroundDrawable(
                AppCompatResources.getDrawable(
                    requireContext(),
                    R.drawable.color_transparent
                )
            )
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val newsItem = args.newsItem
        with(binding) {
            image.loadImage(newsItem.image)
            title.text = newsItem.title
            description.text = newsItem.description

            readMore.setOnClickListener {
                navigateToWebView()
            }
        }
    }

    private fun navigateToWebView() {
        findNavController().navigate(
            NewsDialogFragmentDirections.navigateToWebView(args.newsItem.url)
        )
    }
}