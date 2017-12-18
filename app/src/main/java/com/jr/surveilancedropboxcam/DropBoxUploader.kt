package com.jr.surveilancedropboxcam

import android.content.Context
import com.dropbox.core.DbxRequestConfig
import com.dropbox.core.v2.DbxClientV2
import com.jr.surveilancedropboxcam.interfaces.UploadVideoRecordingINF
import java.io.File

/**
 * Created by Jonathan on 24/10/2017.
 */
class DropBoxUploader : UploadVideoRecordingINF{

    val ACCESS_TOKEN = "IGvssLSl1fEAAAAAAADys3wHdQ7F9GZBBiCrR89XCW6x3N2ANNaGtkgSPPvrkECn"

    lateinit var dropBoxClient: DbxClientV2

    var appContext : Context

    val ROOT_FOLDER = "/surveilanceFootages/"

    constructor(context: Context){
        appContext = context
    }

    override fun initialiseUpload() {
        val config = DbxRequestConfig(appContext.packageName)
        dropBoxClient = DbxClientV2(config,ACCESS_TOKEN)
    }

    override fun uploadRecording(videoFile: File) {
        dropBoxClient.files().uploadBuilder(ROOT_FOLDER).uploadAndFinish(videoFile.inputStream())
    }

}
