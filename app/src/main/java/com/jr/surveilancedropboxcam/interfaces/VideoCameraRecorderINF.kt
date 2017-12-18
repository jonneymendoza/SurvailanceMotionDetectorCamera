package com.jr.surveilancedropboxcam.interfaces

import com.jr.surveilancedropboxcam.interfaces.callback.VideoCameraCallback

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