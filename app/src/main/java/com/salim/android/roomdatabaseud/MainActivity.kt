package com.salim.android.roomdatabaseud

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.salim.android.roomdatabaseud.databinding.ActivityMainBinding
import com.salim.android.roomdatabaseud.db.SubscriberDatabase
import com.salim.android.roomdatabaseud.db.SubscriberRepository

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var subscriberViewmModel: SubscriberViewmModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        val dao = SubscriberDatabase.getInstance(application).subscriberDAO
        val repository = SubscriberRepository(dao)
        val factory = SubscriberViewModelFactory(repository)

        subscriberViewmModel = ViewModelProvider(this, factory)[SubscriberViewmModel::class.java]

        binding.myViewModel = subscriberViewmModel
        binding.lifecycleOwner = this

        displaySubscriberList()
    }

    private fun displaySubscriberList(){
        subscriberViewmModel.subscribers.observe(this, Observer {
            Log.i("TAG", "displaySubscriberList: $it")
        })
    }
}