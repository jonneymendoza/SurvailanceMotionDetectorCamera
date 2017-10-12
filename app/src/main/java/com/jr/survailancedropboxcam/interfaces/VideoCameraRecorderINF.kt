package com.jr.survailancedropboxcam.interfaces

/**
 * Created by Jonathan on 11/10/2017
 *
 * Start a video recording using the camera interface and attach a @see VideoCameraCallback
 *
 */
interface VideoCameraRecorderINF {
    fun startRecording(videoCameraCallback: VideoCameraCallback)
    fun stopRecording()
}