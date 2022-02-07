package com.chernyshev.newsletterapp.domain.repository

import com.chernyshev.newsletterapp.domain.entity.Language

interface UserRepository {
    var isFirstTimeUser: Boolean
    var preferredNewsLanguage: Language
}