package com.jr.survailancedropboxcam

import android.content.Context
import android.hardware.camera2.CameraCaptureSession
import android.hardware.camera2.CameraDevice
import android.hardware.camera2.CameraManager
import android.media.MediaRecorder
import android.view.Surface
import com.jr.survailancedropboxcam.interfaces.VideoCameraCallback
import com.jr.survailancedropboxcam.interfaces.VideoCameraRecorderINF
import java.util.ArrayList

/**
 * Created by Jonathan on 11/10/2017.
 *
 * Start and stop a video recording
 *
 */
class VideoCameraRecorder : VideoCameraRecorderINF {

    lateinit var cameraManager: CameraManager

    lateinit var context: Context

    var cameraStateCalback = object : CameraDevice.StateCallback() {
        override fun onOpened(camera: CameraDevice?) {
            //create capture session
            val outputCaptures = setMediaOutputSurface()

        }

        override fun onDisconnected(camera: CameraDevice?) {
        }

        override fun onError(camera: CameraDevice?, error: Int) {
        }
    }

    var  cameraCaptureSessionCallBack = object :  CameraCaptureSession.StateCallback(){
        override fun onConfigureFailed(session: CameraCaptureSession?) {

        }

        override fun onConfigured(session: CameraCaptureSession?) {

            session.capture()
        }

    }

    private fun setMediaOutputSurface(): ArrayList<Surface> {
        val mediaRecorder = MediaRecorder()
        mediaRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.DEFAULT)
        mediaRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA)
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
        mediaRecorder.setOutputFile(context.filesDir.absolutePath)

        var outputCaptures = ArrayList<Surface>()
        outputCaptures.add(mediaRecorder.surface)

        return outputCaptures
    }

    constructor(context: Context) {
        this.context = context
        cameraManager = context.getSystemService(Context.CAMERA_SERVICE) as CameraManager

    }

    override fun startRecording(videoCameraCallback: VideoCameraCallback) {
        val cameraIdList = cameraManager.cameraIdList

        cameraManager.openCamera(cameraIdList[0], cameraStateCalback, null)

    }

    override fun stopRecording() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}

