package com.example.searchtutor.view.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.searchtutor.R
import com.example.searchtutor.controler.CustomDialog
import com.example.searchtutor.controler.PreferencesData
import com.example.searchtutor.data.response.CategoryNameResponse
import com.example.searchtutor.data.response.CategoryResponse
import com.example.searchtutor.data.response.CommentResponse
import com.example.searchtutor.data.response.GroupCourseResponse
import com.example.searchtutor.view.adapter.CategoryAdapter
import com.example.searchtutor.view.adapter.CourseTutorAdapter
import com.example.searchtutor.view.adapter.GridAdapter
import com.example.searchtutor.view.main.MainActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.*


@Suppress("DEPRECATION", "NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class HomeFragment : Fragment(), View.OnClickListener {

    private var supportFragmentManager: FragmentManager? = null
    private var user: PreferencesData.Users? = null
    private lateinit var mHomePresenter: HomePresenter
    private lateinit var mCourseTutorAdapter: CourseTutorAdapter
    private lateinit var mGridAdapter: GridAdapter
    private lateinit var mCategoryAdapter: CategoryAdapter

    private var layoutNotNull: LinearLayout? = null
    private var layoutNull: LinearLayout? = null

    private var tvCategory: TextView? = null
    private var btnEditCategory: ImageView? = null

    private var btnAddCategory: FloatingActionButton? = null
    private var btnAddCourse: Button? = null
    private var rvCourse: RecyclerView? = null
    private var tvCountCourse: TextView? = null

    private var mDialog = CustomDialog()

    private var cardView4: CardView? = null
    private var cardView3: CardView? = null
    private var cardView6: CardView? = null
    private var gvCategory: RecyclerView? = null

    //edit text
    private lateinit var edtSearch: EditText
    //image view
    private lateinit var clearText: ImageView



    var ca_name = ""
    var g_id = ""

    override fun onResume() {
        super.onResume()
        manageToolbar()

        when (user?.type) {
            "tutor" -> {
                tutorZone()
            }
            "user" -> {
                userZone()
            }
            else -> {
                userZone()
            }
        }

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        user = PreferencesData.user(requireActivity())
        initView(root)
        return root
    }

    private fun initView(root: View) {
        mHomePresenter = HomePresenter()
        supportFragmentManager = fragmentManager

        layoutNotNull = root.findViewById(R.id.layoutNotNull)
        layoutNull = root.findViewById(R.id.layoutNull)
        tvCategory = root.findViewById(R.id.tvCategory)
        btnAddCategory = root.findViewById(R.id.btnAddCategory)
        btnEditCategory = root.findViewById(R.id.btnEditCategory)
        btnAddCourse = root.findViewById(R.id.btnAddCourse)
        cardView4 = root.findViewById(R.id.cardView4)
        rvCourse = root.findViewById(R.id.rvCourse)
        tvCountCourse = root.findViewById(R.id.tvCountCourse)
        cardView3 = root.findViewById(R.id.cardView3)
        gvCategory = root.findViewById(R.id.gvCategory)
        edtSearch = root.findViewById(R.id.edtSearch)
        clearText = root.findViewById(R.id.clearText)
        cardView6 = root.findViewById(R.id.cardView6)

        btnAddCategory!!.setOnClickListener(this)
        btnEditCategory!!.setOnClickListener(this)
        btnAddCourse!!.setOnClickListener(this)

    }

    @SuppressLint("SetTextI18n")
    private fun manageToolbar() {

        val tvTitle = (activity as MainActivity).getTvTitle()
        val back = (activity as MainActivity).getBack()
        val nav = (activity as MainActivity).getNav()
        tvTitle.text = "หน้าแรก"
        back.visibility = View.GONE
        (activity as MainActivity?)!!.updateNavigationBarState(R.id.navigation_home)

    }

    private fun userZone() {
        // gone tutor zone
        cardView3!!.visibility = View.GONE
        cardView4!!.visibility = View.GONE
        btnAddCategory!!.visibility = View.GONE
        cardView6!!.visibility = View.VISIBLE

        mHomePresenter.getCategory(object : HomePresenter.Response.Category{
            override fun value(c: CategoryResponse) {

                clearText.setOnClickListener {
                    edtSearch.text.clear()
                }

                edtSearch.addTextChangedListener(object : TextWatcher {
                    override fun afterTextChanged(p0: Editable?) {
                        filters(p0.toString(), c.data as ArrayList<CategoryResponse.Category>)

                        if (p0.toString().isEmpty()) {
                            clearText.visibility = View.GONE
                        } else {
                            clearText.visibility = View.VISIBLE
                        }
                    }

                    override fun beforeTextChanged(
                        p0: CharSequence?,
                        p1: Int,
                        p2: Int,
                        p3: Int
                    ) {
                    }

                    override fun onTextChanged(
                        p0: CharSequence?,
                        p1: Int,
                        p2: Int,
                        p3: Int
                    ) {
                    }
                })
                val list = ArrayList<CategoryResponse.Category>()
                list.addAll(c.data!!)

                val gridLayoutManager = GridLayoutManager(activity, 2)

                mCategoryAdapter = CategoryAdapter(activity!!, list){ t,  s ->

                    if(s){
                        val bundle = Bundle()
                        bundle.putString("ca_id", t["ca_id"])
                        bundle.putString("ca_name", t["ca_name"])

                        val tutorListFragment: TutorListFragment? =
                            requireActivity().fragmentManager
                                .findFragmentById(R.id.fragment_tutor_list) as TutorListFragment?

                        if (tutorListFragment == null) {
                            val newFragment = TutorListFragment()
                            newFragment.arguments = bundle
                            requireFragmentManager().beginTransaction()
                                .replace(R.id.navigation_view, newFragment, "")
                                .addToBackStack(null)
                                .commit()
                        } else {
                            requireFragmentManager().beginTransaction()
                                .replace(R.id.navigation_view, tutorListFragment, "")
                                .addToBackStack(null)
                                .commit()
                        }

                    }

                }

                gvCategory!!.apply {
                    layoutManager = gridLayoutManager
                    adapter = mCategoryAdapter
                    mCategoryAdapter.notifyDataSetChanged()
                }
            }

            override fun error(c: String?) {

            }
        })



    }

    private fun filters(toString: String, mCourseFromUserList: ArrayList<CategoryResponse.Category>) {
        val list = ArrayList<CategoryResponse.Category>()

        for (t in mCourseFromUserList) {
            if (t.ca_name?.toLowerCase()!!.contains(toString.toLowerCase())) {
                list.add(t)
            }
        }
        mCategoryAdapter.filterList(list)

    }

    private fun tutorZone() {
        cardView3!!.visibility = View.VISIBLE
        btnAddCategory!!.visibility = View.VISIBLE
        cardView6!!.visibility = View.GONE

        mHomePresenter.getGroupCategory(
            user?.t_id.toString(),
            object : HomePresenter.Response.CategoryName {
                override fun value(c: CategoryNameResponse) {
                    if (!c.isSuccessful) {
                        layoutNotNull!!.visibility = View.GONE
                        layoutNull!!.visibility = View.VISIBLE
                        btnAddCategory!!.visibility = View.VISIBLE
                        cardView4!!.visibility = View.GONE
                    } else {
                        layoutNotNull!!.visibility = View.VISIBLE
                        layoutNull!!.visibility = View.GONE
                        btnAddCategory!!.visibility = View.GONE
                        cardView4!!.visibility = View.VISIBLE

                        tvCategory!!.text = "หมวดหมู่: " + c.data!![0].ca_name

                        ca_name = c.data[0].ca_name!!
                        g_id = c.data[0].g_id.toString()

                        mHomePresenter.getGroupCourse(c.data[0].g_id.toString(),
                            object : HomePresenter.Response.GroupCourse {
                                override fun value(c: GroupCourseResponse) {
                                    tvCountCourse!!.text =
                                        "จำนวนคอร์ส: " + c.data!!.size.toString() + " คอร์ส"
                                    mCourseTutorAdapter =
                                        CourseTutorAdapter(
                                            activity!!,
                                            c.data as ArrayList<GroupCourseResponse.GroupCourse>
                                        ) { hashMap, b ->

                                            // On click
                                            if (b) {

                                                /*val bundle = Bundle()
                                                bundle.putString(
                                                    "Cr_id",
                                                    hashMap["Cr_id"].toString()
                                                )
                                                bundle.putString(
                                                    "Cr_name",
                                                    hashMap["Cr_name"].toString()
                                                )
                                                bundle.putString(
                                                    "Cr_price",
                                                    hashMap["Cr_price"].toString()
                                                )
                                                bundle.putString(
                                                    "Cr_info",
                                                    hashMap["Cr_info"].toString()
                                                )
                                                bundle.putString(
                                                    "Cr_data_time",
                                                    hashMap["Cr_data_time"].toString()
                                                )

                                                val detailCourseFragment: DetailCourseFragment? =
                                                    activity!!.fragmentManager
                                                        .findFragmentById(R.id.fragment_add_course) as DetailCourseFragment?

                                                if (detailCourseFragment == null) {
                                                    val newFragment = DetailCourseFragment()
                                                    newFragment.arguments = bundle
                                                    fragmentManager!!.beginTransaction()
                                                        .replace(
                                                            R.id.navigation_view,
                                                            newFragment,
                                                            ""
                                                        )
                                                        .addToBackStack(null)
                                                        .commit()
                                                } else {
                                                    fragmentManager!!.beginTransaction()
                                                        .replace(
                                                            R.id.navigation_view,
                                                            detailCourseFragment,
                                                            ""
                                                        )
                                                        .addToBackStack(null)
                                                        .commit()
                                                }*/

                                                // On long click
                                            } else {


                                                mDialog.onDialog(
                                                    activity!!,
                                                    false,
                                                    "เลือกการทำงาน"
                                                ) { s ->
                                                    //เลือก แก้ไข
                                                    if (s == "update") {

                                                        val bundle = Bundle()
                                                        bundle.putString("gc_id", hashMap["gc_id"])
                                                        bundle.putString("g_id", hashMap["g_id"])
                                                        bundle.putString("t_id", hashMap["t_id"])
                                                        bundle.putString(
                                                            "gc_name",
                                                            hashMap["gc_name"]
                                                        )
                                                        bundle.putString(
                                                            "gc_detail",
                                                            hashMap["gc_detail"]
                                                        )
                                                        bundle.putString(
                                                            "gc_price",
                                                            hashMap["gc_price"]
                                                        )


                                                        val editCourseFragment: EditCourseFragment? =
                                                            requireActivity().fragmentManager
                                                                .findFragmentById(R.id.fragment_edit_course) as EditCourseFragment?

                                                        if (editCourseFragment == null) {
                                                            val newFragment = EditCourseFragment()
                                                            newFragment.arguments = bundle
                                                            requireFragmentManager().beginTransaction()
                                                                .replace(
                                                                    R.id.navigation_view,
                                                                    newFragment,
                                                                    ""
                                                                )
                                                                .addToBackStack(null)
                                                                .commit()
                                                        } else {
                                                            requireFragmentManager().beginTransaction()
                                                                .replace(
                                                                    R.id.navigation_view,
                                                                    editCourseFragment,
                                                                    ""
                                                                )
                                                                .addToBackStack(null)
                                                                .commit()
                                                        }

                                                        //เลือก ลบ
                                                    } else {
                                                        mDialog.dialogQuestion(
                                                            activity!!,
                                                            false,
                                                            "ลบข้อมูล",
                                                            "ต้องการลบข้อมูลคอร์สเรียนหรือไม่?"
                                                        ) { d, a ->
                                                            if (d) {
                                                                mHomePresenter.deleteCourse(
                                                                    hashMap["gc_id"].toString()
                                                                ) { boolean, string ->
                                                                    if (boolean) {
                                                                        tutorZone()
                                                                        Toast.makeText(
                                                                            activity,
                                                                            string,
                                                                            Toast.LENGTH_SHORT
                                                                        ).show()
                                                                    } else {
                                                                        tutorZone()
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
                                    rvCourse!!.apply {
                                        layoutManager = LinearLayoutManager(activity)
                                        adapter = mCourseTutorAdapter
                                        mCourseTutorAdapter.notifyDataSetChanged()
                                    }

                                }

                                override fun error(c: String?) {

                                }
                            })
                    }

                }

                override fun error(c: String?) {

                }
            })

    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btnAddCategory -> {
                val addCourseFragment: AddCategoryFragment? =
                    requireActivity().fragmentManager
                        .findFragmentById(R.id.fragment_add_category) as AddCategoryFragment?

                if (addCourseFragment == null) {
                    val newFragment = AddCategoryFragment()
                    requireFragmentManager().beginTransaction()
                        .replace(R.id.navigation_view, newFragment, "")
                        .addToBackStack(null)
                        .commit()
                } else {
                    requireFragmentManager().beginTransaction()
                        .replace(R.id.navigation_view, addCourseFragment, "")
                        .addToBackStack(null)
                        .commit()
                }
            }
            R.id.btnEditCategory -> {

                val bundle = Bundle()
                bundle.putString("ca_name", ca_name)
                bundle.putString("g_id", g_id)

                val addCourseFragment: EditCategoryFragment? =
                    requireActivity().fragmentManager
                        .findFragmentById(R.id.fragment_edit_category) as EditCategoryFragment?

                if (addCourseFragment == null) {
                    val newFragment = EditCategoryFragment()
                    newFragment.arguments = bundle
                    requireFragmentManager().beginTransaction()
                        .replace(R.id.navigation_view, newFragment, "")
                        .addToBackStack(null)
                        .commit()
                } else {
                    requireFragmentManager().beginTransaction()
                        .replace(R.id.navigation_view, addCourseFragment, "")
                        .addToBackStack(null)
                        .commit()
                }
            }
            R.id.btnAddCourse -> {
                val bundle = Bundle()
                bundle.putString("g_id", g_id)

                val addCourseFragment: AddCourseFragment? =
                    requireActivity().fragmentManager
                        .findFragmentById(R.id.fragment_add_course) as AddCourseFragment?

                if (addCourseFragment == null) {
                    val newFragment = AddCourseFragment()
                    newFragment.arguments = bundle
                    requireFragmentManager().beginTransaction()
                        .replace(R.id.navigation_view, newFragment, "")
                        .addToBackStack(null)
                        .commit()
                } else {
                    requireFragmentManager().beginTransaction()
                        .replace(R.id.navigation_view, addCourseFragment, "")
                        .addToBackStack(null)
                        .commit()
                }
            }
        }
    }


}