package com.chernyshev.newsletterapp.data.repository

import android.content.SharedPreferences
import com.chernyshev.newsletterapp.domain.entity.Language
import com.chernyshev.newsletterapp.domain.repository.UserRepository
import javax.inject.Inject

private const val KEY_FIRST_TIME_USER = "first_time"
private const val KEY_PREFERRED_LANGUAGE = "preferred_language"

class UserRepositoryImpl @Inject constructor(
    private val commonPrefs: SharedPreferences
) : UserRepository {
    override var isFirstTimeUser: Boolean
        get() = commonPrefs.getBoolean(KEY_FIRST_TIME_USER, true)
        set(value) {
            commonPrefs.edit().putBoolean(KEY_FIRST_TIME_USER, value).apply()
        }

    override var preferredNewsLanguage: Language
        get() = commonPrefs
            .getString(KEY_PREFERRED_LANGUAGE, Language.English.languageString)
            .toLanguage()
        set(value) {
            commonPrefs
                .edit()
                .putString(KEY_PREFERRED_LANGUAGE, value.languageString)
                .apply()
        }
}

private fun String?.toLanguage(): Language {
    return when (this) {
        Language.Deutsch.languageString -> Language.Deutsch
        Language.Russian.languageString -> Language.Russian
        else -> Language.English
    }
}