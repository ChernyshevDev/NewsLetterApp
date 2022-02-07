package com.chernyshev.newsletterapp.di

import android.content.Context
import android.content.SharedPreferences
import com.chernyshev.newsletterapp.data.repository.UserRepositoryImpl
import com.chernyshev.newsletterapp.data.repository.usecase.NewsUseCaseImpl
import com.chernyshev.newsletterapp.domain.repository.UserRepository
import com.chernyshev.newsletterapp.domain.usecase.NewsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped

private const val COMMON_PREF_NAME = "common-preferences"

@Module
@InstallIn(ViewModelComponent::class)
class ProvidersModule {

    @Provides
    @ViewModelScoped
    fun provideNewsUseCase(userRepository: UserRepository): NewsUseCase =
        NewsUseCaseImpl(userRepository)
}

@Module
@InstallIn(SingletonComponent::class)
object PreferencesModule {

    @Provides
    @Singleton
    fun provideSharedPrefs(@ApplicationContext applicationContext: Context): SharedPreferences =
        applicationContext.getSharedPreferences(
            COMMON_PREF_NAME, Context.MODE_PRIVATE
        )
}

@Module
@InstallIn(SingletonComponent::class)
object UserModule {

    @Provides
    @Singleton
    fun provideUserRepository(sharedPreferences: SharedPreferences): UserRepository =
        UserRepositoryImpl(sharedPreferences)
}