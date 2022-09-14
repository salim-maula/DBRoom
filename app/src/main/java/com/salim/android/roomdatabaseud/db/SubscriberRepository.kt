package com.salim.android.roomdatabaseud.db


//purpose of the repository class is to provide a
//clean API for ViewModel to easily get and send data

//you can consider repositories to be mediator beetween different data source
//such as local databases, web service and caches

//why should we create an intermediate  repository calss?
//cant we directly communicate with the DAO from the view model, yes its. but
//we wont to create best practice for design patterns MVVM

//we add DAO from constructor to call function of DAO
class SubscriberRepository(private val dao : SubscriberDAO) {

    val subscribers = dao.getAllSubscribes()

    //DAO function should be called from a background thread
    // so we use coroutine
    suspend fun insert(subscriber: Subscriber){
        dao.insertSubscriber(subscriber)
    }

    suspend fun update(subscriber: Subscriber){
        dao.updateSubscriber(subscriber)
    }

    suspend fun delete(subscriber: Subscriber){
        dao.deleteSubscriber(subscriber)
    }

    suspend fun deleteAll(){
        dao.deleteAll()
    }
}