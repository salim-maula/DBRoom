package com.salim.android.roomdatabaseud.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.salim.android.roomdatabaseud.R
import com.salim.android.roomdatabaseud.databinding.ItemListBinding
import com.salim.android.roomdatabaseud.db.Subscriber

class MyRecyclerViewAdapter(private val subscribersList: List<Subscriber>) : RecyclerView.Adapter<MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
       val layoutInflater = LayoutInflater.from(parent.context)
        val binding : ItemListBinding = DataBindingUtil.inflate(layoutInflater, R.layout.item_list, parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
       holder.bind(subscribersList[position])
    }

    override fun getItemCount(): Int  = subscribersList.size


}

class MyViewHolder(val binding: ItemListBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(subscriber: Subscriber){
        binding.nameTextView.text = subscriber.name
        binding.emailTextView.text = subscriber.email
    }
}