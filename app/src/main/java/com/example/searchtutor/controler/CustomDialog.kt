package com.example.searchtutor.controler

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.InsetDrawable
import android.media.Image
import android.os.Build
import android.text.method.DigitsKeyListener
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.example.searchtutor.R
import kotlinx.android.synthetic.main.custom_alert_dialog.view.*
import kotlinx.android.synthetic.main.custom_alert_dialog.view.tvTitle
import kotlinx.android.synthetic.main.custom_alert_dialog02.view.*
import kotlinx.android.synthetic.main.custom_alert_dialog02.view.btnConfirm
import kotlinx.android.synthetic.main.dialog_edit_tutoring.view.*

import java.util.HashMap

class CustomDialog {

    fun dialogEditExpense(
        context: Context,
        title: String,
        price: String,
        callBack: (HashMap<String, String>, Boolean) -> Unit
    ) {
        val mDialogView = LayoutInflater.from(context).inflate(R.layout.dialog_edit_tutoring, null)
        val mBuilder = AlertDialog.Builder(context)
            .setView(mDialogView)

        val alertDialog: AlertDialog = mBuilder.create()
        alertDialog.setCancelable(true)

        // set title
        mDialogView.tvTitle.text = title

        //set data

        mDialogView.edtTutoring.setText(price)

        val back = ColorDrawable(Color.TRANSPARENT)
        val inset = InsetDrawable(back, 50)
        alertDialog.window?.setBackgroundDrawable(inset)

        alertDialog.show()


        //กดปุ่่มยืนยัน
        mDialogView.btnConfirm.setOnClickListener {
            val myMap = HashMap<String, String>()
            myMap["tutoring"] = mDialogView.edtTutoring.text.toString()
            callBack.invoke(myMap, true)
            alertDialog.dismiss()


        }
    }


    fun onDialog(
        context: Context,
        oneBtn: Boolean,
        title: String,
        callBack: (String) -> Unit
    ) {

        val mDialogView = LayoutInflater.from(context).inflate(R.layout.custom_alert_dialog, null)
        val mBuilder = AlertDialog.Builder(context)
            .setView(mDialogView)

        val alertDialog: AlertDialog = mBuilder.create()
        // alertDialog.window!!.attributes.windowAnimations = R.style.DialogSlide

        // set title
        mDialogView.tvTitle.text = title

        // set messess
        //   mDialogView.tvMessess.text = messess

        val back = ColorDrawable(Color.TRANSPARENT)
        val inset = InsetDrawable(back, 50)
        alertDialog.window?.setBackgroundDrawable(inset)

        alertDialog.show()

        if(oneBtn){
            mDialogView.btnEdit.visibility = View.GONE
        }

        mDialogView.btnDeleteCourse.setOnClickListener {
            callBack.invoke("delete")
            alertDialog.dismiss()
        }

        mDialogView.btnEditCourse.setOnClickListener {
            callBack.invoke("update")
            alertDialog.dismiss()
        }

    }


    fun dialogQuestion(
        context: Context,
        oneBtn: Boolean,
        title: String,
        messess: String,
        callBack: (Boolean, String) -> Unit
    ) {

        val mDialogView = LayoutInflater.from(context).inflate(R.layout.custom_alert_dialog02, null)
        val mBuilder = AlertDialog.Builder(context)
            .setView(mDialogView)

        val alertDialog: AlertDialog = mBuilder.create()

        // set title
        mDialogView.tvTitle02.text = title

        // set messess
        mDialogView.tvMessess.text = messess

        val back = ColorDrawable(Color.TRANSPARENT)
        val inset = InsetDrawable(back, 50)
        alertDialog.window?.setBackgroundDrawable(inset)

        alertDialog.show()
        if (!oneBtn) {
            mDialogView.btnCancel.visibility = View.VISIBLE
        } else {
            mDialogView.btnCancel.visibility = View.GONE
        }

        mDialogView.btnCancel.setOnClickListener {
            alertDialog.dismiss()
        }

        //กดปุ่่มยืนยัน
        mDialogView.btnConfirm.setOnClickListener {
            callBack.invoke(true, "www")
            alertDialog.dismiss()

        }


    }


}