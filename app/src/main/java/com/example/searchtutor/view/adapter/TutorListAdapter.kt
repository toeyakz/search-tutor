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
import com.example.searchtutor.data.response.CategoryResponse
import com.example.searchtutor.data.response.GroupCourseResponse
import com.example.searchtutor.data.response.TutorListResponse
import com.squareup.picasso.Picasso
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class TutorListAdapter(
    val context: Context,
    var item: ArrayList<TutorListResponse.TutorList>,
    private var mOnClickList: (HashMap<String, String>, Boolean) -> (Unit)
    // private val mCloseLoadPresenter: CloseLoadPresenter
) :
    RecyclerView.Adapter<TutorListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(
        viewGroup: ViewGroup,
        i: Int
    ): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(viewGroup.context).inflate(
                R.layout.layout_item_tutor_list,
                viewGroup,
                false
            )
        )
    }


    override fun getItemCount() = item.size


    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, i: Int) {

        // holder.tvNameCourse.text = item[i].gc_name
        holder.tvNameTutor.text = item[i].t_name + " " + item[i].t_lname
        holder.tvCountCourse.text = "จำนวนคอร์ส : " + item[i].count_course

        Picasso.get()
            .load(Utils.host + "search_tutor/img_profile/" + item[i].t_img)
            .into(holder.imgProfile)

        holder.itemView.setOnClickListener {
            val myMap = HashMap<String, String>()
            myMap["t_id"] = item[i].t_id.toString()
            myMap["t_name"] = item[i].t_name.toString()
            myMap["t_lname"] = item[i].t_lname.toString()
            myMap["t_username"] = item[i].t_username.toString()
            myMap["t_password"] = item[i].t_password.toString()
            myMap["t_email"] = item[i].t_email.toString()
            myMap["t_tel"] = item[i].t_tel.toString()
            myMap["t_address"] = item[i].t_address.toString()
            myMap["t_tutoring_center"] = item[i].t_tutoring_center.toString()
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

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvNameTutor: TextView = itemView.findViewById(R.id.tvNameTutor)
        var tvCountCourse: TextView = itemView.findViewById(R.id.tvCountCourse)
        var imgProfile: ImageView = itemView.findViewById(R.id.imgProfile)


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