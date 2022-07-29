package dev.haziqkamel.countriesmvvmdemo.di

import dagger.Component
import dev.haziqkamel.countriesmvvmdemo.model.CountriesService
import dev.haziqkamel.countriesmvvmdemo.viewmodel.ListViewModel

@Component(modules = [ApiModule::class])
interface ApiComponent {

    fun inject(service: CountriesService)

    fun inject(viewModel: ListViewModel)

}