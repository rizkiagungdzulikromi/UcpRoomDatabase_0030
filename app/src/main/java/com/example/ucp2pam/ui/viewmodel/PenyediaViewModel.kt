package com.example.ucp2pam.ui.viewmodel

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.ucp2pam.MyApp

object PenyediaViewModel {
    val Factory = viewModelFactory {
        initializer {
            DokterViewModel(
                repositoryDr = MyApp().containerApp.repositoryDr
            )
        }
        initializer {
            JadwalViewModel(
                repositoryJdw = MyApp().containerApp.repositoryJdw
            )
        }
        initializer {
            HomeViewModel(
                repositoryDr = MyApp().containerApp.repositoryDr,
                repositoryJdw = MyApp().containerApp.repositoryJdw
            )
        }
        initializer {
            val savedStateHandle = createSavedStateHandle()
            DetailJadwalViewModel(
                repositoryJdw = MyApp().containerApp.repositoryJdw,
                savedStateHandle = savedStateHandle
            )
        }
        initializer {
            val savedStateHandle = createSavedStateHandle()
            UpdateJadwalViewModel(
                savedStateHandle = savedStateHandle,
                repositoryJdw = MyApp().containerApp.repositoryJdw
            )
        }
    }
}

fun CreationExtras.MyApp(): MyApp =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as MyApp)