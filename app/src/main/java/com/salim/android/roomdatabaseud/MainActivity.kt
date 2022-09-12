package com.salim.android.roomdatabaseud

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.salim.android.roomdatabaseud.adapter.MyRecyclerViewAdapter
import com.salim.android.roomdatabaseud.databinding.ActivityMainBinding
import com.salim.android.roomdatabaseud.db.Subscriber
import com.salim.android.roomdatabaseud.db.SubscriberDatabase
import com.salim.android.roomdatabaseud.db.SubscriberRepository
import com.salim.android.roomdatabaseud.viewmodel.SubscriberViewModelFactory
import com.salim.android.roomdatabaseud.viewmodel.SubscriberViewmModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var subscriberViewmModel: SubscriberViewmModel

    private lateinit var adapter: MyRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        val dao = SubscriberDatabase.getInstance(application).subscriberDAO
        val repository = SubscriberRepository(dao)
        val factory = SubscriberViewModelFactory(repository)

        subscriberViewmModel = ViewModelProvider(this, factory)[SubscriberViewmModel::class.java]

        binding.myViewModel = subscriberViewmModel
        binding.lifecycleOwner = this

//        displaySubscriberList()
        initRecycleView()

        //use message
        subscriberViewmModel.message.observe(this, Observer {
            it.getContentIfNotHandled()?.let {
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun initRecycleView() {
        binding.subscriberRecyclerView.layoutManager = LinearLayoutManager(this)
        displaySubscriberList()
        adapter =
            MyRecyclerViewAdapter({ selectedItem: Subscriber -> listItemClicked(selectedItem) })
    }

    private fun displaySubscriberList() {
        subscriberViewmModel.subscribers.observe(this, Observer {
            Log.i("TAG", "displaySubscriberList: $it")
            //when update, delete and insert we create a new MyRecyclerViewAdapter object
            adapter.setList(it)
            adapter.notifyDataSetChanged()
        })
    }

    private fun listItemClicked(subscriber: Subscriber) {
//        Toast.makeText(this, "Selected name is ${subscriber.name}", Toast.LENGTH_LONG).show()

        //setup delete
        subscriberViewmModel.initUpdateAndDelete(subscriber)
    }
}