package com.example.searchtutor.view.setting

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.searchtutor.R
import com.example.searchtutor.controler.CustomDialog
import com.example.searchtutor.controler.CustomProgressDialog
import com.example.searchtutor.controler.PreferencesData
import com.example.searchtutor.data.response.AddCommentResponse
import com.example.searchtutor.data.response.CommentResponse
import com.example.searchtutor.view.adapter.CommentAdapter
import com.example.searchtutor.view.home.HomePresenter
import com.example.searchtutor.view.main.MainActivity
import java.util.ArrayList


@Suppress("DEPRECATION")
class CommentListFragment : Fragment() {

    private lateinit var mSettingPresenter: SettingPresenter
    private var dialog: CustomProgressDialog? = null
    private var user: PreferencesData.Users? = null

    private var mDialog = CustomDialog()

    private var rvComment: RecyclerView? = null
    private var imgSendComment: ImageView? = null
    private var edtComment: EditText? = null
    private lateinit var mCommentAdapter: CommentAdapter

    override fun onResume() {
        super.onResume()
        manageToolbar()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_comment_list, container, false)
        user = PreferencesData.user(requireActivity())
        mSettingPresenter = SettingPresenter()

        rvComment = root.findViewById(R.id.rvComment)
        imgSendComment = root.findViewById(R.id.imgSendComment)
        edtComment = root.findViewById(R.id.edtComment)
        commentZone()
        return root
    }

    private fun commentZone() {
        mSettingPresenter.getComment(
            user?.t_id.toString(),
            object : HomePresenter.Response.Comment {
                override fun value(c: CommentResponse) {
                    if (c.isSuccessful) {
                        mCommentAdapter =
                            CommentAdapter(
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
                                                    mSettingPresenter.deleteComment(
                                                        h["cm_id"].toString()
                                                    ) { boolean, string ->
                                                        if (boolean) {
                                                            commentZone()
                                                            Toast.makeText(
                                                                activity,
                                                                string,
                                                                Toast.LENGTH_SHORT
                                                            ).show()
                                                        } else {
                                                            commentZone()
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
                            mSettingPresenter.addComment(
                                user?.t_id.toString(),
                                user?.t_id.toString(),
                                "",
                                edtComment!!.text.toString(),
                                object : HomePresenter.Response.AddComment {
                                    override fun value(c: AddCommentResponse) {
                                        if (c.isSuccessful) {
                                            commentZone()
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


    @SuppressLint("SetTextI18n")
    private fun manageToolbar() {
        val tvTitle = (activity as MainActivity).getTvTitle()
        val back = (activity as MainActivity).getBack()
        val nav = (activity as MainActivity).getNav()

        tvTitle.text = "รายการความคิดเห็น"


        back.visibility = View.VISIBLE

        back.setOnClickListener {
            requireFragmentManager().popBackStack()
        }
    }

}