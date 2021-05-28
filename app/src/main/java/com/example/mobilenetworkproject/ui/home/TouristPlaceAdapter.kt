package com.example.mobilenetworkproject.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.mobilenetworkproject.R
import com.example.mobilenetworkproject.model.entity.TouristPlaceModel


class TouristPlaceAdapter(
    private val dataset: Array<TouristPlaceModel>,
//    private val mainView: View
) : RecyclerView.Adapter<TouristPlaceAdapter.ViewHolder>() {

    class ViewHolder(private val view: View) : RecyclerView.ViewHolder(view){
        val myView: View = view
        val name: TextView = view.findViewById(R.id.tourist_place_name)
        val country: TextView = view.findViewById(R.id.tourist_place_country)
        val star: TextView = view.findViewById(R.id.tourist_place_star)
        val image: ImageView = view.findViewById(R.id.tourist_place_image)
        val id: TextView = view.findViewById(R.id.tourist_place_id)

        fun onClick() {
            print("himsalfjl")
            val bundle = Bundle()
            bundle.putInt("id", this.id.text.toString().toInt())
            this.myView.findNavController().navigate(R.id.action_nav_home_to_nav_slide_show, bundle)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // create a new view
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.tourist_place_home_card, parent, false)

        return ViewHolder(adapterLayout)
    }

    override fun getItemCount() = dataset.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = dataset[position]
        holder.name.text = item.name
        holder.country.text = item.country
        holder.star.text = item.star.toString()
        holder.image.setImageResource(item.imageResourceId)
        holder.id.text = item.id.toString()
        holder.itemView.setOnClickListener { holder.onClick() }
    }
}