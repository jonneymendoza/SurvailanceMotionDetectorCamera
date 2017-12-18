package com.jr.surveilancedropboxcam

import android.content.Context
import android.hardware.camera2.CameraCaptureSession
import android.hardware.camera2.CameraDevice
import android.hardware.camera2.CameraDevice.TEMPLATE_RECORD
import android.hardware.camera2.CameraManager
import android.hardware.camera2.CaptureRequest
import android.media.MediaRecorder
import android.util.Log
import android.view.Surface
import com.jr.surveilancedropboxcam.interfaces.callback.VideoCameraCallback
import com.jr.surveilancedropboxcam.interfaces.VideoCameraRecorderINF
import java.util.ArrayList
import android.os.Handler
import android.os.HandlerThread


/**
 * Created by Jonathan on 11/10/2017.
 *
 * Start and stop a video recording
 *
 */
class VideoCameraRecorder : VideoCameraRecorderINF {

    var cameraManager: CameraManager

    var context: Context

    lateinit var captureRequest : CaptureRequest

    lateinit var mediaRecorder : MediaRecorder

    lateinit var videoFootagePath : String

    lateinit var videoCaptureCallback : VideoCameraCallback

    var cameraStateCalback = object : CameraDevice.StateCallback() {
        override fun onOpened(camera: CameraDevice?) {
            if (camera != null) {
                Log.d("JJJ", "onOpened and will now create handler and capture session")
                //create handler thread
                val thread = HandlerThread("MyHandlerThread")
                thread.start()
                val handler = Handler(thread.looper)

                //create capture session
                val outputCaptures = setMediaOutputSurface()
                captureRequest =  camera.createCaptureRequest(TEMPLATE_RECORD).build()
                camera.createCaptureSession(outputCaptures, cameraCaptureSessionCallBack,handler)
                Log.d("JJJ", "Created thread handler and capture session")
            }
        }

        override fun onDisconnected(camera: CameraDevice?) {
            Log.d("JJJ", "on disconnected")
        }

        override fun onError(camera: CameraDevice?, error: Int) {
            Log.d("JJJ", "on error")
        }
    }

    var  cameraCaptureSessionCallBack = object :  CameraCaptureSession.StateCallback(){

        override fun onConfigureFailed(session: CameraCaptureSession?) {
            Log.d("JJJ", "on configured failed")
        }

        override fun onConfigured(session: CameraCaptureSession?) {
//            session?.capture(captureRequest, captureCallback, null)
            Log.d("JJJ", "about to start capturing")
            mediaRecorder.start()
            Log.d("JJJ"," finished executing mediaRecorder.start()")

        }
    }

    var captureCallback = object : CameraCaptureSession.CaptureCallback(){
        override fun onCaptureStarted(session: CameraCaptureSession?, request: CaptureRequest?, timestamp: Long, frameNumber: Long) {
            super.onCaptureStarted(session, request, timestamp, frameNumber)
            Log.d("JJJ", "captured started")
        }
    }

    private fun setMediaOutputSurface(): ArrayList<Surface> {
        videoFootagePath = context.filesDir.absolutePath + "/"+System.currentTimeMillis() + ".mp4"
        Log.d("JJJ", "videoFootagePath = " + videoFootagePath)
        mediaRecorder = MediaRecorder()
        mediaRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA)
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
        mediaRecorder.setVideoFrameRate(25)
        mediaRecorder.setVideoSize(1920,1080)
        mediaRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.DEFAULT)
        mediaRecorder.setOutputFile(videoFootagePath)
        mediaRecorder.prepare()
//        mediaRecorder.start()

        var outputCaptures = ArrayList<Surface>()
        outputCaptures.add(mediaRecorder.surface)

        return outputCaptures
    }

    constructor(context: Context) {
        this.context = context
        cameraManager = context.getSystemService(Context.CAMERA_SERVICE) as CameraManager

    }

    override fun startRecording(videoCallback: VideoCameraCallback) {
        val cameraIdList = cameraManager.cameraIdList
        this.videoCaptureCallback = videoCallback
        cameraManager.openCamera(cameraIdList[0], cameraStateCalback, null)
        Log.d("JJJ", "start recording called")
    }

    override fun stopRecording() {
        mediaRecorder.stop()
        mediaRecorder.release()
        videoCaptureCallback.onFinishedRecordingSuccess(videoFootagePath)
        Log.d("JJJ", "stop recording called")
    }

}

