package com.example.searchtutor.view.home

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.searchtutor.R
import com.example.searchtutor.controler.CustomDialog
import com.example.searchtutor.controler.PreferencesData
import com.example.searchtutor.data.response.AddCommentResponse
import com.example.searchtutor.data.response.CommentResponse
import com.example.searchtutor.view.adapter.CommentStudentAdapter
import com.example.searchtutor.view.main.MainActivity
import java.util.ArrayList


@Suppress("DEPRECATION")
class CourseDetailsAndCommentFragment : Fragment() {

    private var supportFragmentManager: FragmentManager? = null
    private var user: PreferencesData.Users? = null
    private lateinit var mHomePresenter: HomePresenter
    private lateinit var mCommentAdapter: CommentStudentAdapter

    private lateinit var tvDetail: TextView
    private var cardView9: CardView? = null
    private var imgSendComment: ImageView? = null
    private var rvComment: RecyclerView? = null
    private var edtComment: EditText? = null

    private var mDialog = CustomDialog()

    var c_id_key = ""
    override fun onResume() {
        super.onResume()
        manageToolbar()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_course_details_and_comment, container, false)
        initView(root)
        return root

    }

    private fun initView(root: View) {
        supportFragmentManager = fragmentManager
        mHomePresenter = HomePresenter()
        user = PreferencesData.user(requireActivity())

        tvDetail = root.findViewById(R.id.tvDetail)
        cardView9 = root.findViewById(R.id.cardView9)
        imgSendComment = root.findViewById(R.id.imgSendComment)
        rvComment = root.findViewById(R.id.rvComment)
        edtComment = root.findViewById(R.id.edtComment)

        val bundle = arguments
        if (bundle != null) {
            tvDetail.text = bundle.getString("gc_detail")
            c_id_key = bundle.getString("gc_id").toString()
        }
        commentViewList()


    }

    private fun commentViewList() {
        if (user?.type == "admin") {
            cardView9!!.visibility = View.GONE
        }
        mHomePresenter.getComment(c_id_key, object : HomePresenter.Response.Comment {
            override fun value(c: CommentResponse) {
                if (c.isSuccessful) {

                    mCommentAdapter =
                        CommentStudentAdapter(
                            activity!!,
                            user?.st_id!!,
                            c.data as ArrayList<CommentResponse.Comment>
                        ) { h, b ->
                             if (!b) {
                                 mDialog.onDialog(
                                     activity!!,
                                     true,
                                     "เลือกการทำงาน"
                                 ) { s ->
                                     //เลือก แก้ไข
                                     if (s != "update") {
                                         mDialog.dialogQuestion(
                                             activity!!,
                                             false,
                                             "ลบข้อมูล",
                                             "ต้องการลบความคิดเห็นนี้หรือไม่?"
                                         ) { d, a ->
                                             if (d) {
                                                 mHomePresenter.deleteComment(
                                                     h["cm_id"].toString()
                                                 ) { boolean, string ->
                                                     if (boolean) {
                                                         commentViewList()
                                                         Toast.makeText(
                                                             activity,
                                                             string,
                                                             Toast.LENGTH_SHORT
                                                         ).show()
                                                     } else {
                                                         commentViewList()
                                                         Toast.makeText(
                                                             activity,
                                                             string,
                                                             Toast.LENGTH_SHORT
                                                         ).show()
                                                     }
                                                 }
                                             }
                                         }

                                     }
                                 }
                             }
                        }

                    rvComment!!.apply {
                        layoutManager = LinearLayoutManager(activity)

                        adapter = mCommentAdapter
                        mCommentAdapter.notifyDataSetChanged()
                    }
                    //  rvComment!!.smoothScrollToPosition(c.data.size - 1)

                }

            }

            override fun error(c: String?) {

            }
        })

        imgSendComment!!.setOnClickListener {
            mHomePresenter.addComment(
                c_id_key,
                "",
                user?.st_id.toString(),
                edtComment!!.text.toString(),
                object : HomePresenter.Response.AddComment {
                    override fun value(c: AddCommentResponse) {
                        if (c.isSuccessful) {
                            commentViewList()
                            edtComment!!.text.clear()
                        }
                    }

                    override fun error(c: String?) {

                    }
                })
        }
    }

    @SuppressLint("SetTextI18n")
    private fun manageToolbar() {
        val tvTitle = (activity as MainActivity).getTvTitle()
        val back = (activity as MainActivity).getBack()
        val nav = (activity as MainActivity).getNav()
        //nav.visibility = View.GONE
        tvTitle.text = "รายละเอียดคอร์ส"
        back.visibility = View.VISIBLE





        back.setOnClickListener {
            requireFragmentManager().popBackStack()
        }
    }


}