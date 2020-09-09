package com.example.searchtutor.view.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.searchtutor.R
import com.example.searchtutor.controler.Utils
import com.example.searchtutor.data.model.User
import com.example.searchtutor.data.response.CategoryResponse
import com.example.searchtutor.data.response.GroupCourseResponse
import com.example.searchtutor.data.response.TutorListResponse
import com.squareup.picasso.Picasso
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class TutorAllAdapter(
    val context: Context,
    var item: ArrayList<User>,
    private var mOnClickList: (HashMap<String, String>, Boolean) -> (Unit)
    // private val mCloseLoadPresenter: CloseLoadPresenter
) :
    RecyclerView.Adapter<TutorAllAdapter.ViewHolder>() {

    override fun onCreateViewHolder(
        viewGroup: ViewGroup,
        i: Int
    ): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(viewGroup.context).inflate(
                R.layout.layout_item_student_all,
                viewGroup,
                false
            )
        )
    }


    override fun getItemCount() = item.size


    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, i: Int) {

        holder.tvNameStudent.text = item[i].t_name + " " + item[i].t_lname
        holder.tvEmail.text = "Email: " + item[i].t_email

        Picasso.get()
            .load(Utils.host + "search_tutor/img_profile/" + item[i].t_img)
            .into(holder.imgProfile)

        holder.imgEdit.setOnClickListener {
            val myMap = HashMap<String, String>()
            myMap["t_id"] = item[i].t_id.toString()
            mOnClickList.invoke(myMap, true)
        }

        /* holder.itemView.setOnLongClickListener {
             val myMap = HashMap<String, String>()
             myMap["gc_id"] = item[i].gc_id.toString()
             myMap["g_id"] = item[i].g_id.toString()
             myMap["t_id"] = item[i].t_id.toString()
             myMap["gc_name"] = item[i].gc_name.toString()
             myMap["gc_detail"] = item[i].gc_detail.toString()
             myMap["gc_price"] = item[i].gc_price.toString()
             mOnClickList.invoke(myMap, false)
             true
         }*/

    }

    /*   fun filterList(list: ArrayList<CategoryResponse.Category>) {
           item = list
           notifyDataSetChanged()
       }*/

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvNameStudent: TextView = itemView.findViewById(R.id.tvNameStudent)
        var tvEmail: TextView = itemView.findViewById(R.id.tvEmail)
        var imgProfile: ImageView = itemView.findViewById(R.id.imgProfile)
        var imgEdit: ImageView = itemView.findViewById(R.id.imgEdit)


    }

    @SuppressLint("SimpleDateFormat")
    private fun dateNewFormat(pattern: String): String? {
        var pattern: String? = pattern
        val pattern2 = "dd/MM/yyyy kk:mm"
        var spf = SimpleDateFormat("yyyy-MM-dd kk:mm:ss")
        var newDate: Date? = null
        try {
            newDate = spf.parse(pattern)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        spf = SimpleDateFormat(pattern2, Locale("th", "th"))
        pattern = spf.format(newDate)
        return pattern
    }
}