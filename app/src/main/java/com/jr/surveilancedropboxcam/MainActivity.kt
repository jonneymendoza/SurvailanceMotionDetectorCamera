package com.jr.survailancedropboxcam

import android.app.Activity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import com.jr.survailancedropboxcam.interfaces.callback.VideoCameraCallback
import android.content.Intent
import android.content.ComponentName



private val TAG = MainActivity::class.java.simpleName

class MainActivity : Activity() {

    lateinit var videoCameraRecorder :VideoCameraRecorder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val intent = Intent(Intent.ACTION_MAIN, null)
        intent.addCategory(Intent.CATEGORY_LAUNCHER)
        val cn = ComponentName("com.android.settings", "com.android.settings.wifi.WifiSettings")
        intent.component = cn
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        intent.putExtra("ssid", "jonneyOnePlus")
        intent.putExtra("passphrase", "kerstin123")
        startActivity(intent)

        Handler().postDelayed({
            videoCameraRecorder = VideoCameraRecorder(applicationContext)
            videoCameraRecorder.startRecording( object : VideoCameraCallback {
                override fun onFinishedRecordingSuccess() {
                    Log.d(TAG, "onFinishedRecordingSuccess")
                }

                override fun onFinishedRecordingFailure() {
                    Log.d(TAG, "onFinishedRecordingFailure")
                }
            })
            Handler().postDelayed({
                videoCameraRecorder = VideoCameraRecorder(applicationContext)
                videoCameraRecorder.stopRecording()
            }, 10000)
        }, 5000)
    }

    override fun onDestroy() {
        super.onDestroy()
    }

}
