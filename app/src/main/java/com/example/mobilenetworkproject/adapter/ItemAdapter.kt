package com.example.mobilenetworkproject.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mobilenetworkproject.R
import com.example.mobilenetworkproject.model.ArticleModel

class ItemAdapter(
    private val context: Context,
    private val dataset: List<ArticleModel>
) : RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder.
    // Each data item is just an Affirmation object.
    class ItemViewHolder(private val view: View): RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.article_title)
        val subTitle: TextView = view.findViewById(R.id.article_sub_title)
        val star: TextView = view.findViewById(R.id.article_star)
        val image: ImageView = view.findViewById(R.id.article_image)
    }

    /**
     * Create new views (invoked by the layout manager)
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        // create a new view
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.article_card, parent, false)

        return ItemViewHolder(adapterLayout)
    }

    /**
     * Replace the contents of a view (invoked by the layout manager)
     */
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = dataset[position]
        holder.title.text = context.resources.getString(item.titleResourceId)
        holder.subTitle.text = context.resources.getString(item.subTitleResourceId)
        holder.star.text = context.resources.getString(item.starResourceId)
        holder.image.setImageResource(item.imageResourceId)
    }

    /**
     * Return the size of your dataset (invoked by the layout manager)
     */
    override fun getItemCount() = dataset.size
}