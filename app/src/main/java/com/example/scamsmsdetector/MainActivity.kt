package com.example.scamsmsdetector

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {

    private val SMS_PERMISSION_CODE = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        checkAndRequestSMSPermission()
    }

    private fun checkAndRequestSMSPermission() {
        val receiveSmsPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS)
        val readSmsPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS)

        if (receiveSmsPermission != PackageManager.PERMISSION_GRANTED ||
            readSmsPermission != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.RECEIVE_SMS, Manifest.permission.READ_SMS),
                SMS_PERMISSION_CODE
            )
        } else {
            Toast.makeText(this, "SMS permissions already granted", Toast.LENGTH_SHORT).show()
        }
    }

    // Optional: Handle permission result
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == SMS_PERMISSION_CODE) {
            if (grantResults.isNotEmpty() && grantResults.all { it == PackageManager.PERMISSION_GRANTED }) {
                Toast.makeText(this, "SMS permissions granted", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "SMS permissions denied", Toast.LENGTH_LONG).show()
            }
        }
    }
}
