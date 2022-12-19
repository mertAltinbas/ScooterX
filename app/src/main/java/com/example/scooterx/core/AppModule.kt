package com.example.scooterx.core
import com.example.scooterx.presentation.screens.map.view_model.MapViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel{ MapViewModel(get()) }
}

