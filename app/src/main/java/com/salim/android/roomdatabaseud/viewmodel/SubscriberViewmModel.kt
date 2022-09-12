package com.salim.android.roomdatabaseud.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.salim.android.roomdatabaseud.Event
import com.salim.android.roomdatabaseud.db.Subscriber
import com.salim.android.roomdatabaseud.db.SubscriberRepository
import kotlinx.coroutines.launch

class SubscriberViewmModel(private val repository: SubscriberRepository): ViewModel() {

    val inputName = MutableLiveData<String?>()
    val inputEmail = MutableLiveData<String?>()

    val saveOrUpdateButtonText = MutableLiveData<String>()
    val clearAllOrDeleteButtonText = MutableLiveData<String>()

    val subscribers = repository.subscribers
    private lateinit var subscriberToUpdateOrDelete : Subscriber

    private var isUpdateOrDelete = false

    private val statusMessage = MutableLiveData<Event<String>>()
    val message : LiveData<Event<String>>
    get() = statusMessage

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
            inputName.value = null
            inputEmail.value = null
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
        viewModelScope.launch {
            repository.insert(subscriber)
            statusMessage.value = Event("Subscriber Inserted Successfully")
        }
    }

    fun update(subscriber: Subscriber){
        viewModelScope.launch {
            repository.update(subscriber)

            //add this
            //add this
            inputName.value = null
            inputEmail.value = null
            isUpdateOrDelete = false
            saveOrUpdateButtonText.value = "Save"
            clearAllOrDeleteButtonText.value = "Clear All"
            statusMessage.value = Event("Subscriber Updated Successfully")
        }
    }

    fun delete(subscriber: Subscriber){
        viewModelScope.launch{
            repository.delete(subscriber)

            //add this
            inputName.value = null
            inputEmail.value = null
            isUpdateOrDelete = false
            saveOrUpdateButtonText.value = "Save"
            clearAllOrDeleteButtonText.value = "Clear All"
            statusMessage.value = Event("Subscriber Deleted Successfully")
        }
    }
    fun clearAll(){
        viewModelScope.launch {
            repository.deleteAll()
            statusMessage.value = Event("All Subscribers Deleted Successfully")
        }
    }
}


















