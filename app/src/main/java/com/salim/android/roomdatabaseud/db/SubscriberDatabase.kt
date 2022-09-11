package com.salim.android.roomdatabaseud.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

//to represent the actual database
@Database(entities = [Subscriber::class], version = 1)
abstract class SubscriberDatabase : RoomDatabase() {

    abstract val subscriberDAO: SubscriberDAO

    //we should only use one instance of room database for the entire app
    //to avoid unexpecterd errors

    //we create singletons as companion object
    companion object{
        //this the reference to the SubscriberDatabase
        @Volatile
        //make field immediately made visible to other threads
        private var INSTANCE : SubscriberDatabase? = null

        fun getInstance(context: Context) : SubscriberDatabase{
            //in this block we add syncronized block
            //lock is the SubscriberDatabase
            synchronized(this){
                var instance = INSTANCE
                if (instance == null){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        SubscriberDatabase::class.java,
                        "subscriber_data.db"
                    ).build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}