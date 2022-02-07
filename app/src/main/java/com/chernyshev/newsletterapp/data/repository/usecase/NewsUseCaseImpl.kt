package com.chernyshev.newsletterapp.data.repository.usecase

import com.chernyshev.newsletterapp.domain.entity.NewsItem
import com.chernyshev.newsletterapp.domain.repository.UserRepository
import com.chernyshev.newsletterapp.domain.usecase.NewsUseCase
import com.chernyshev.newsletterapp.presentation.base.OperationResult
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val NEWS_BASE_URL = "https://gnews.io"
private const val API_KEY = "22e66bc9242207fc991b446b36fe01c8"

class NewsUseCaseImpl(private val userRepository: UserRepository) : NewsUseCase {

    private val retrofit by lazy {
        val gson: Gson = GsonBuilder()
            .setLenient()
            .create()
        val logger = HttpLoggingInterceptor().apply {
            setLevel(HttpLoggingInterceptor.Level.BODY)
        }
        val client = OkHttpClient.Builder().addInterceptor(logger).build()

        Retrofit.Builder()
            .baseUrl(NEWS_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(client)
            .build()
            .create(NewsApi::class.java)
    }

    override suspend fun getTopNews(): OperationResult<List<NewsItem>> {
        val response =
            retrofit.getTopNews(language = userRepository.preferredNewsLanguage.languageString)
        val body = response.body()
        return if (response.isSuccessful && body != null) {
            OperationResult.ResultSuccess(body.articles)
        } else {
            OperationResult.ResultError(response.errorBody()?.string() ?: "")
        }
    }
}

interface NewsApi {
    @GET("/api/v4/top-headlines?")
    suspend fun getTopNews(
        @Query("token") token: String = API_KEY,
        @Query("lang") language: String
    ): Response<NewsResponse>
}

data class NewsResponse(
    val articles: List<NewsItem>
)