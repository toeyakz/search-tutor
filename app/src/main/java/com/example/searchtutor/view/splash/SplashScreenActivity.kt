package com.example.searchtutor.view.splash

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.searchtutor.R
import com.example.searchtutor.controler.PreferencesData
import com.example.searchtutor.view.login.LoginActivity
import com.example.searchtutor.view.main.MainActivity
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener

class SplashScreenActivity : AppCompatActivity() {

    private val timeOut: Long = 2000 // 2 sec
    private var user: PreferencesData.Users? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        user = PreferencesData.user(applicationContext)


        try {
            setContentView(R.layout.activity_splash_screen)
            permission()
        } catch (e: Exception) {
            e.printStackTrace()
        }


    }

    private fun permission() {
        val permissions = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            listOf(
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
        } else {
            listOf(
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
        }

        Dexter.withActivity(this)
            .withPermissions(permissions)
            .withListener(object : MultiplePermissionsListener {

                @SuppressLint("SetTextI18n")
                override fun onPermissionsChecked(report: MultiplePermissionsReport?) {

                    report?.let {
                        if (report.areAllPermissionsGranted()) {
                            if (user?.session!!) {
                                Handler().postDelayed({
                                    val myIntent =
                                        Intent(applicationContext, MainActivity::class.java)
                                    myIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(myIntent)
                                    finish()
                                }, timeOut)
                            } else {
                                Handler().postDelayed({
                                    val myIntent =
                                        Intent(applicationContext, LoginActivity::class.java)
                                    myIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(myIntent)
                                    finish()
                                }, timeOut)
                            }
                        }
                        /*else {
                            dialog()
                        }*/
                    }


                }

                @SuppressLint("SetTextI18n")
                override fun onPermissionRationaleShouldBeShown(
                    permissions: MutableList<PermissionRequest>?,
                    token: PermissionToken?
                ) {
                    //  tvSerial.text = ": unknowns"
                    token?.continuePermissionRequest()
                }

            })
            .check()
    }
}