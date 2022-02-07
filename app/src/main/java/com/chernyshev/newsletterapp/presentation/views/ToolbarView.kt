package com.chernyshev.newsletterapp.presentation.views

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import com.chernyshev.newsletterapp.R
import com.chernyshev.newsletterapp.databinding.ViewToolbarBinding
import com.chernyshev.newsletterapp.domain.entity.Language
import com.chernyshev.newsletterapp.domain.repository.UserRepository
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ToolbarView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : ConstraintLayout(context, attrs) {

    @Inject
    lateinit var userRepository: UserRepository

    private val binding by lazy { ViewToolbarBinding.bind(this) }
    private var onLanguageChange: ((Language) -> Unit)? = null

    init {
        inflate(context, R.layout.view_toolbar, this)
        elevation = resources.getDimension(R.dimen.default_elevation)
        context.obtainStyledAttributes(attrs, R.styleable.ToolbarView).run {
            val isBackVisible = getBoolean(R.styleable.ToolbarView_backVisible, true)
            val isLanguageSelectorVisible =
                getBoolean(R.styleable.ToolbarView_languageSelectorVisible, false)
            binding.apply {
                back.isVisible = isBackVisible
                languageSelector.isVisible = isLanguageSelectorVisible
            }
            recycle()
        }
        initLanguageSwitcher()
    }

    fun onBackClicked(onClicked: () -> Unit) = with(binding) {
        back.setOnClickListener {
            onClicked()
        }
    }

    fun onLanguageChanged(onChanged: (Language) -> Unit) {
        onLanguageChange = onChanged
    }

    private fun initLanguageSwitcher() = with(binding) {
        val languages = listOf(
            userRepository.preferredNewsLanguage
        ) + listOf(
            Language.English,
            Language.Deutsch,
            Language.Russian
        ).minus(userRepository.preferredNewsLanguage)
        val adapter = ArrayAdapter(
            context,
            R.layout.item_drop_down_small,
            languages
        )

        languageSelector.apply {
            this.adapter = adapter
            onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    (parent?.getItemAtPosition(position) as? Language)?.let { language ->
                        onLanguageChange?.let {
                            userRepository.preferredNewsLanguage = language
                            it.invoke(language)
                        }
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }
        }
    }
}