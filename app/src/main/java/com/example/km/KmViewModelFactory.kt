package com.example.km

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class KmViewModelFactory(private val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val dao = KmDatabase.getDatabase(application).kmRegistroDao()
        val repository = KmRepository(dao)
        return KmViewModel(repository) as T
    }
}
