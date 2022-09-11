package com.salim.android.roomdatabaseud.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.salim.android.roomdatabaseud.db.Subscriber
import com.salim.android.roomdatabaseud.db.SubscriberRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SubscriberViewmModel(private val repository: SubscriberRepository): ViewModel() {

    val inputName = MutableLiveData<String?>()
    val inputEmail = MutableLiveData<String?>()

    val saveOrUpdateButtonText = MutableLiveData<String>()
    val clearAllOrDeleteButtonText = MutableLiveData<String>()

    val subscribers = repository.subscribers
    private lateinit var subscriberToUpdateOrDelete : Subscriber

    private var isUpdateOrDelete = false

    init {
        saveOrUpdateButtonText.value = "Save"
        clearAllOrDeleteButtonText.value = "Clear All"
    }

    fun saveOrUpdate(){
        if (isUpdateOrDelete){
            subscriberToUpdateOrDelete.name = inputName.value!!
            subscriberToUpdateOrDelete.email = inputEmail.value!!
            update(subscriberToUpdateOrDelete)
        }else{
            val name = inputName.value!!
            val email = inputEmail.value!!
            insert(Subscriber(0, name, email))
            inputName.value = ""
            inputEmail.value = ""
        }
    }

    fun clearAllOrDelete(){
        if (isUpdateOrDelete){
            delete(subscriberToUpdateOrDelete)
        } else{
            clearAll()
        }
    }

    fun initUpdateAndDelete(subscriber: Subscriber){
        inputName.value = subscriber.name
        inputEmail.value = subscriber.email
        isUpdateOrDelete = true
        subscriberToUpdateOrDelete = subscriber
        saveOrUpdateButtonText.value = "update"
        clearAllOrDeleteButtonText.value = "Delete"
    }

    fun insert(subscriber: Subscriber){
        viewModelScope.launch(Dispatchers.IO) {
            repository.insert(subscriber)
        }
    }

    fun update(subscriber: Subscriber){
        viewModelScope.launch(Dispatchers.IO) {
            repository.update(subscriber)

            //add this
            //add this
            inputName.value = null
            inputEmail.value = null
            isUpdateOrDelete = false
            saveOrUpdateButtonText.value = "Save"
            clearAllOrDeleteButtonText.value = "Clear All"
        }
    }

    fun delete(subscriber: Subscriber){
        viewModelScope.launch(Dispatchers.IO) {
            repository.delete(subscriber)

            //add this
            inputName.value = null
            inputEmail.value = null
            isUpdateOrDelete = false
            saveOrUpdateButtonText.value = "Save"
            clearAllOrDeleteButtonText.value = "Clear All"
        }
    }
    fun clearAll(){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAll()
        }
    }
}


















