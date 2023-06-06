package com.moon.weather.detail

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.moon.network.data.model.Forecastday
import com.moon.weather.databinding.ItemCityBinding

class DetailAdapter : RecyclerView.Adapter<DetailAdapter.MyViewHolder>() {

    class MyViewHolder(val binding: ItemCityBinding) :
        RecyclerView.ViewHolder(binding.root)

    private class DifferCallback : DiffUtil.ItemCallback<Forecastday>() {
        override fun areItemsTheSame(oldItem: Forecastday, newItem: Forecastday): Boolean {
            return oldItem.date == newItem.date
        }

        override fun areContentsTheSame(oldItem: Forecastday, newItem: Forecastday): Boolean {
            return oldItem == newItem
        }
    }

    var differ = AsyncListDiffer(this, DifferCallback())

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            ItemCityBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
        )
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val dataId = differ.currentList[position]
        holder.binding.apply {
            days.text = dataId.date
//            condition.text = dataId.condition.text
            grad.text = dataId.day.avgtemp_c.toString() + " \u2103"
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}