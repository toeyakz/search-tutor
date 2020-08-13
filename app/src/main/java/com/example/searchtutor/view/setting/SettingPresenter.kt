package com.example.searchtutor.view.setting

import android.R
import android.app.AlertDialog
import android.content.Context
import android.content.SharedPreferences
import com.example.searchtutor.controler.Constants

class SettingPresenter {

    fun logout(activity: Context, res: (Boolean) -> Unit) {
        val builder: AlertDialog.Builder = AlertDialog.Builder(activity)
        builder.setTitle("ออกจากระบบ")
            .setMessage("คุณณต้องการออกจากระบบหรือไม่?")
            .setPositiveButton(
                "ออกจากระบบ"
            ) { _, _ ->
                val preferences: SharedPreferences = activity.getSharedPreferences(
                    Constants.PREFERENCES_USER,
                    Context.MODE_PRIVATE
                )
                preferences.edit().putBoolean(Constants.SESSION, false).apply()
                preferences.edit().clear().apply()

                res.invoke(true)
            }
            .setNegativeButton(R.string.cancel) { _, _ ->
                res.invoke(false)
            }
            .show()
    }

}