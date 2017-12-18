package com.jr.surveilancedropboxcam

import android.app.Activity
import android.content.ComponentName
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import com.jr.surveilancedropboxcam.interfaces.UploadVideoRecordingINF
import com.jr.surveilancedropboxcam.interfaces.callback.VideoCameraCallback
import java.io.File
import android.R.attr.start
import android.os.HandlerThread




private val TAG = MainActivity::class.java.simpleName

class MainActivity : Activity() {

    lateinit var videoCameraRecorder :VideoCameraRecorder

    lateinit var dropBoxUpload : UploadVideoRecordingINF

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

        val handlerThread = HandlerThread("backgroundThread")
        if (!handlerThread.isAlive){
            handlerThread.start()
        }


        Handler(handlerThread.looper).postDelayed({
            videoCameraRecorder = VideoCameraRecorder(applicationContext)
            videoCameraRecorder.startRecording( object : VideoCameraCallback {
                override fun onFinishedRecordingSuccess(videoFootagePath: String) {
                    Log.d(TAG, "onFinishedRecordingSuccess")
                    dropBoxUpload = DropBoxUploader(this@MainActivity)
                    dropBoxUpload.initialiseUpload()
                    dropBoxUpload.uploadRecording(File(videoFootagePath))
//                    handlerThread.quit()
                }

                override fun onFinishedRecordingFailure() {
                    Log.d(TAG, "onFinishedRecordingFailure")
//                    handlerThread.quit()
                }
            })

        }, 5000)

        Handler(handlerThread.looper).postDelayed({
            videoCameraRecorder = VideoCameraRecorder(applicationContext)
            videoCameraRecorder.stopRecording()
        }, 20000)
    }

    override fun onDestroy() {
        super.onDestroy()
    }

}
