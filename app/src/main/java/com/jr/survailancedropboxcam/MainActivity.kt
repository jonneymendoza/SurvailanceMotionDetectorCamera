package com.jr.survailancedropboxcam

import android.app.Activity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import com.jr.survailancedropboxcam.interfaces.callback.VideoCameraCallback

private val TAG = MainActivity::class.java.simpleName

class MainActivity : Activity() {

    lateinit var videoCameraRecorder :VideoCameraRecorder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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
