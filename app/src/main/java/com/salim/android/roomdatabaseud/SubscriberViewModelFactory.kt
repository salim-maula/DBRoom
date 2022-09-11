package com.salim.android.roomdatabaseud

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.salim.android.roomdatabaseud.db.SubscriberRepository

class SubscriberViewModelFactory(private val repository: SubscriberRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SubscriberViewmModel::class.java)) {
            return SubscriberViewmModel(repository) as T
        } else {
            throw IllegalArgumentException("unknown ViewModel Class")
        }
    }
}