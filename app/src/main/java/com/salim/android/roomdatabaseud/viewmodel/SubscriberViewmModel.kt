package com.salim.android.roomdatabaseud.viewmodel

import android.util.Patterns
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
        if (inputName.value==null){
            statusMessage.value = Event("Please enter subscriber name")
        }else if (inputEmail.value==null){
            statusMessage.value = Event("Please enter subscriber email")
        }else if (!Patterns.EMAIL_ADDRESS.matcher(inputEmail.value!!).matches()){
            statusMessage.value = Event("Please enter a corect email address")
        }else{

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

    fun insert(subscriber: Subscriber) {
        viewModelScope.launch {
           val newRowId =   repository.insert(subscriber)
            if (newRowId> -1){
                statusMessage.value = Event("Subscriber Inserted Successfully $newRowId")
            }else{
                statusMessage.value = Event("Error Occured")
            }
        }
    }

    fun update(subscriber: Subscriber){
        viewModelScope.launch {
           val noOfRows = repository.update(subscriber)
            if (noOfRows>0){
                //add this
                //add this
                inputName.value = null
                inputEmail.value = null
                isUpdateOrDelete = false
                saveOrUpdateButtonText.value = "Save"
                clearAllOrDeleteButtonText.value = "Clear All"
                statusMessage.value = Event("$noOfRows Row Updated Successfully")
            }else{
                statusMessage.value = Event("Error Occured")
            }

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


















