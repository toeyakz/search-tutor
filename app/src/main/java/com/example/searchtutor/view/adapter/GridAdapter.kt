package com.example.searchtutor.view.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.searchtutor.R
import com.example.searchtutor.data.response.CategoryResponse

class GridAdapter(
    val context: Context
    , private val category: ArrayList<CategoryResponse.Category>
) :
    BaseAdapter() {


    override fun getCount(): Int {
        return category.size
    }

    override fun getItem(position: Int): Any {
        return category[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val category = this.category[position]

        var inflator = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        var foodView = inflator.inflate(R.layout.layout_item_grid, null)

        val tvName = foodView.findViewById(R.id.tvName) as TextView
        val imgFood = foodView.findViewById(R.id.imgFood) as ImageView


        //imgFood.setImageResource(category.!!)
        tvName.text = category.ca_name!!

        return foodView
    }

}

