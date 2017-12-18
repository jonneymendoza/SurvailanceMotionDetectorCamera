package com.jr.surveilancedropboxcam.interfaces.callback

/**
 * Created by Jonathan on 11/10/2017.
 */
interface VideoCameraCallback{
    fun onFinishedRecordingSuccess(videoFootagePath: String)
    fun onFinishedRecordingFailure()

}