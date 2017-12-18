package com.jr.surveilancedropboxcam.interfaces

import java.io.File

/**
 * Created by Jonathan on 11/10/2017.
 */
interface UploadVideoRecordingINF {
    fun initialiseUpload()
    fun uploadRecording(videoFile: File)
}