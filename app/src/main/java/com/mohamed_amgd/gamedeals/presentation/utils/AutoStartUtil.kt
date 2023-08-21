package com.mohamed_amgd.gamedeals.presentation.utils

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.preference.PreferenceManager

object AutoStartUtil {
    private const val TAG = "AutoStartUtil"
    private const val AUTO_START_DIALOG_ENABLED = "AUTO_START_DIALOG_ENABLED"

    private fun isAutoStartDialogEnabled(context: Context): Boolean {
        val preferences = PreferenceManager.getDefaultSharedPreferences(context)
        return preferences.getBoolean(AUTO_START_DIALOG_ENABLED, true)
    }

    fun disableAutoStartDialog(context: Context) {
        val preferences = PreferenceManager.getDefaultSharedPreferences(context)
        preferences.edit().putBoolean(AUTO_START_DIALOG_ENABLED, false).apply()
    }

    fun needAutoStartDialog(context: Context): Boolean {
        val manufacturers = listOf("xiaomi", "oppo", "vivo", "letv", "honor")
        return Build.MANUFACTURER.lowercase() in manufacturers && isAutoStartDialogEnabled(context)
    }

    fun addAutoStart(context: Context) {
        try {
            val intent = Intent()
            val manufacturer = Build.MANUFACTURER.lowercase()
            if ("xiaomi" == manufacturer) {
                intent.component = ComponentName(
                    "com.miui.securitycenter",
                    "com.miui.permcenter.autostart.AutoStartManagementActivity"
                )
            } else if ("oppo" == manufacturer) {
                intent.component = ComponentName(
                    "com.coloros.safecenter",
                    "com.coloros.safecenter.permission.startup.StartupAppListActivity"
                )
            } else if ("vivo" == manufacturer) {
                intent.component = ComponentName(
                    "com.vivo.permissionmanager",
                    "com.vivo.permissionmanager.activity.BgStartUpManagerActivity"
                )
            } else if ("letv" == manufacturer) {
                intent.component = ComponentName(
                    "com.letv.android.letvsafe",
                    "com.letv.android.letvsafe.AutobootManageActivity"
                )
            } else if ("honor" == manufacturer) {
                intent.component = ComponentName(
                    "com.huawei.systemmanager",
                    "com.huawei.systemmanager.optimize.process.ProtectActivity"
                )
            }
            val list =
                context.packageManager.queryIntentActivities(
                    intent,
                    PackageManager.MATCH_DEFAULT_ONLY
                )
            if (list.size > 0) {
                context.startActivity(intent)
            }
        } catch (e: Exception) {
            Log.e(TAG, e.toString())
        }
    }
}