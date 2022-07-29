package dev.haziqkamel.countriesmvvmdemo.model

import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class CountriesService {

    private val BASE_URL = "https://raw.githubusercontent.com"
    private val api: CountriesAPI = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create()).build()
        .create(CountriesAPI::class.java)

    fun getCountries(): Single<List<Country>> {
        return api.getCountries()
    }
}