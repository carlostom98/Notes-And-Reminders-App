package com.plcoding.cleanarchitecturenoteapp.feature_note.presentation

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.PersistableBundle
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.plcoding.cleanarchitecturenoteapp.feature_note.presentation.util.toastMessageShort
import java.util.jar.Manifest

open class PermissionsManager : ComponentActivity() {

    private val resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            "Granted".toastMessageShort(this)
        }

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        if (checkForPermissions()) {
            "Granted successfully".toastMessageShort(this)
        } else {
            ActivityCompat.requestPermissions(
                this,
                listOfPermissions,
                REQUEST_PERMISSIONS_CODE
            )
        }
    }

    private fun checkForPermissions(): Boolean {
        return listOfPermissions.all {
            ContextCompat.checkSelfPermission(baseContext, it) == PackageManager.PERMISSION_GRANTED
        }
    }

    private fun notPermissionsGranted() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            checkAllFilesAccessPermission()
        }
    }

    @RequiresApi(Build.VERSION_CODES.R)
    private fun checkAllFilesAccessPermission() {
        val intent = Intent()
        intent.action = Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION
        val uri = Uri.fromParts("package", this.packageName, null)
        intent.data = uri
        resultLauncher.launch(intent)
    }

    companion object {
        private const val REQUEST_PERMISSIONS_CODE = 1
        @RequiresApi(Build.VERSION_CODES.S)
        private val listOfPermissions = arrayOf(
            android.Manifest.permission.RECORD_AUDIO,
            android.Manifest.permission.INTERNET
        )
    }
}