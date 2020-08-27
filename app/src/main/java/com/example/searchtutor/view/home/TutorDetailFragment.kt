package com.example.searchtutor.view.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
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
import com.example.searchtutor.controler.Utils
import com.example.searchtutor.data.response.*
import com.example.searchtutor.view.adapter.CommentAdapter
import com.example.searchtutor.view.adapter.CommentStudentAdapter
import com.example.searchtutor.view.adapter.CourseTutorAdapter
import com.example.searchtutor.view.main.MainActivity
import com.example.searchtutor.view.setting.SettingPresenter
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_tutor_detail.*
import java.util.ArrayList


@Suppress("DEPRECATION")
class TutorDetailFragment : Fragment() {

    private var supportFragmentManager: FragmentManager? = null
    private lateinit var mHomePresenter: HomePresenter
    private var user: PreferencesData.Users? = null
    private lateinit var mCourseTutorAdapter: CourseTutorAdapter
    private lateinit var mCommentAdapter: CommentStudentAdapter


    private var tvCategory: TextView? = null
    private var tvCountCourse: TextView? = null
    private var tvNameTutor: TextView? = null
    private var rvCourse: RecyclerView? = null
    private var rvComment: RecyclerView? = null
    private var edtComment: EditText? = null
    private var imgSendComment: ImageView? = null
    private var imgProfileTutor: ImageView? = null
    private var cardView9: CardView? = null


    private var mDialog = CustomDialog()

    var t_id = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_tutor_detail, container, false)
        initView(root)


        return root
    }

    @SuppressLint("SetTextI18n")
    private fun initView(root: View) {
        supportFragmentManager = fragmentManager
        mHomePresenter = HomePresenter()
        user = PreferencesData.user(requireActivity())

        tvCategory = root.findViewById(R.id.tvCategory)
        tvCountCourse = root.findViewById(R.id.tvCountCourse)
        tvNameTutor = root.findViewById(R.id.tvNameTutor)
        rvCourse = root.findViewById(R.id.rvCourse)
        rvComment = root.findViewById(R.id.rvComment)
        edtComment = root.findViewById(R.id.edtComment)
        imgSendComment = root.findViewById(R.id.imgSendComment)
        imgProfileTutor = root.findViewById(R.id.imgProfileTutor)
        cardView9 = root.findViewById(R.id.cardView9)

        val bundle = arguments
        if (bundle != null) {

            tvNameTutor!!.text =
                "ชื่อติวเตอร์ : " + bundle.getString("t_name") + " " + bundle.getString("t_lname")
            mHomePresenter.getGroupCategory(
                bundle.getString("t_id")!!,
                object : HomePresenter.Response.CategoryName {
                    @SuppressLint("SetTextI18n")
                    override fun value(c: CategoryNameResponse) {
                        tvCategory!!.text = "หมวดหมู่: " + c.data!![0].ca_name

                        mHomePresenter.getGroupCourse(c.data[0].g_id.toString(),
                            object : HomePresenter.Response.GroupCourse {
                                override fun value(c: GroupCourseResponse) {

                                    mHomePresenter.getTutor(c.data!![0].t_id.toString(), object : SettingPresenter.Response.Tutor{
                                        override fun value(c: TutorResponse) {
                                            if(c.data!![0].t_img != ""){
                                                Picasso.get().load(Utils.host+ "search_tutor/img_profile/"+c.data[0].t_img).into(imgProfileTutor)
                                            }else{
                                                Picasso.get().load(R.drawable.testimonial_man).into(imgProfileTutor)
                                            }

                                        }

                                        override fun error(c: String?) {

                                        }
                                    })



                                    tvCountCourse!!.text =
                                        "จำนวนคอร์ส: " + c.data!!.size.toString() + " คอร์ส"

                                    mCourseTutorAdapter =
                                        CourseTutorAdapter(
                                            activity!!,
                                            c.data as ArrayList<GroupCourseResponse.GroupCourse>
                                        ) { h, b ->
                                                if(b){
                                                    val bundle22 = Bundle()
                                                    bundle22.putString("gc_id", h["gc_id"])
                                                    bundle22.putString("g_id", h["g_id"])
                                                    bundle22.putString("t_id", h["t_id"])
                                                    bundle22.putString("gc_name", h["gc_name"])
                                                    bundle22.putString("gc_detail", h["gc_detail"])
                                                    bundle22.putString("gc_price", h["gc_price"])

                                                    val courseDetailsAndCommentFragment: CourseDetailsAndCommentFragment? =
                                                        requireActivity().fragmentManager
                                                            .findFragmentById(R.id.fragment_course_detail) as CourseDetailsAndCommentFragment?

                                                    if (courseDetailsAndCommentFragment == null) {
                                                        val newFragment = CourseDetailsAndCommentFragment()
                                                        newFragment.arguments = bundle22
                                                        requireFragmentManager().beginTransaction()
                                                            .replace(R.id.navigation_view, newFragment, "")
                                                            .addToBackStack(null)
                                                            .commit()
                                                    } else {
                                                        requireFragmentManager().beginTransaction()
                                                            .replace(R.id.navigation_view, courseDetailsAndCommentFragment, "")
                                                            .addToBackStack(null)
                                                            .commit()
                                                    }
                                                }
                                        }

                                    rvCourse!!.apply {
                                        layoutManager = LinearLayoutManager(activity)
                                        adapter = mCourseTutorAdapter
                                        mCourseTutorAdapter.notifyDataSetChanged()
                                    }

                                    t_id = c.data[0].t_id.toString()


                                    commentViewList()
                                }

                                override fun error(c: String?) {

                                }
                            })
                    }

                    override fun error(c: String?) {

                    }
                })
        }


    }

    private fun commentViewList() {
        if(user?.type== "admin"){
            cardView9!!.visibility = View.GONE
        }
        mHomePresenter.getComment(t_id, object : HomePresenter.Response.Comment {
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
                    rvComment!!.smoothScrollToPosition(c.data.size - 1)

                    imgSendComment!!.setOnClickListener {
                        mHomePresenter.addComment(
                            t_id,
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

            }

            override fun error(c: String?) {

            }
        })
    }

    override fun onResume() {
        super.onResume()
        manageToolbar()
    }

    @SuppressLint("SetTextI18n")
    private fun manageToolbar() {
        val tvTitle = (activity as MainActivity).getTvTitle()
        val back = (activity as MainActivity).getBack()
        val nav = (activity as MainActivity).getNav()
        //nav.visibility = View.GONE
        val bundle = arguments
        if (bundle != null) {
            tvTitle.text = "ข้อมูลติวเตอร์"
        }

        back.visibility = View.VISIBLE





        back.setOnClickListener {
            requireFragmentManager().popBackStack()
        }
    }

}