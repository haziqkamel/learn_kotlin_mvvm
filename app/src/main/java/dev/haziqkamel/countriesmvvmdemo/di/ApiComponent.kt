package dev.haziqkamel.countriesmvvmdemo.di

import dagger.Component
import dev.haziqkamel.countriesmvvmdemo.model.CountriesService

@Component(modules = [ApiModule::class])
interface ApiComponent {

    fun inject(service: CountriesService)

}