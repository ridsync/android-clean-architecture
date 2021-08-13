package kr.clean.devoks.core.util

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import androidx.activity.result.ActivityResultLauncher
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import kr.clean.devoks.R
import kr.clean.devoks.core.logging.OKLogger.Logger

/**
 * Created by devoks
 * Description : Android Permission Util Class
*/
object PermissionUtil {

    fun isDeniedPermission(context: Context, permission: String): Boolean {
        return !isGrantedPermissions(context, arrayOf(permission))
    }

    fun isDeniedPermissions(context: Context, permissions: Array<String>): Boolean {
        return !isGrantedPermissions(context, permissions)
    }

    fun isGrantedPermissions(context: Context, permissions: Array<String>): Boolean {
        for (permission in permissions) {
            if (ContextCompat.checkSelfPermission(context,permission) == PackageManager.PERMISSION_DENIED) {
                Logger.i("[PermissionUtil] Permissions Denied : $permission")
                return false
            }
        }
        Logger.i("[PermissionUtil] All Permissions Granted : ${permissions.toList()}")
        return true
    }

    fun checkAndRequestPermissions(
        fragment : Fragment,
        permissions: Array<String>,
        launcherPermission: ActivityResultLauncher<Array<String>>,
        launcherSetting: ActivityResultLauncher<Intent>,
        messageinDialog: Int?
    ): Boolean {
        val deniedPermissions = getDeniedPermissions(fragment.requireContext(),permissions)
        if (deniedPermissions.isNotEmpty()) {
            for (deniedPermission in deniedPermissions) {
                val activity = fragment.requireActivity()
                if (ActivityCompat.shouldShowRequestPermissionRationale(activity,deniedPermission)) {
                    if (messageinDialog != null) {
                        // TODO 다이얼로그
                    }
                    Logger.i("[PermissionUtil] Permissions Denied : ${deniedPermissions.toList()}")
                    return false
                }
            }
            launcherPermission.launch(deniedPermissions)
            return false
        }
        // All Permissions Granted
        Logger.i("[PermissionUtil] All Permissions Granted : ${permissions.toList()}")
        return true
    }

    private fun getDeniedPermissions(context: Context, permissions: Array<String>): Array<String> {
        val deniedPermissions = mutableListOf<String>()
        for (permission in permissions) {
            if (ContextCompat.checkSelfPermission(context,permission) == PackageManager.PERMISSION_DENIED) {
                deniedPermissions.add(permission)
            }
        }
        return deniedPermissions.toTypedArray()
    }
}