package com.salim.android.roomdatabaseud.db

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface SubscriberDAO {

    //write function to insert subcriber object to the database
    @Insert
    //room dosnt support database access on the main thread
    //because it might lock the UI foe a long period
    //suspend can be paused and resumed at a later time
    suspend fun insertSubscriber(subscriber: Subscriber)

    @Update
    suspend fun updateSubscriber(subscriber: Subscriber)

    @Delete
    suspend fun deleteSubscriber(subscriber: Subscriber)

    @Query("DELETE FROM subscriber_data_table")
    suspend fun deleteAll()

    //because we dont need to execute this function in background thread using coroutines
    // since this function returns LiveData, room library do its work
    //from a background thread
    @Query("SELECT * FROM subscriber_data_table")
    fun getAllSubscribes(): LiveData<List<Subscriber>>

}

//to represent the actual database