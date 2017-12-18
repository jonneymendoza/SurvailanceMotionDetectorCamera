package com.jr.surveilancedropboxcam.motionsensor

import com.google.android.things.pio.Gpio
import com.google.android.things.pio.PeripheralManagerService
import com.jr.surveilancedropboxcam.interfaces.MotionSensorINF

/**
 * Created by Jonathan on 19/10/2017.
 */
class MotionSensor : MotionSensorINF{

    var motionSensorGpio : Gpio

    constructor(){
        motionSensorGpio = PeripheralManagerService().openGpio("")
    }

    override fun initialiseComponent() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun activateSensor() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun deactivateSensor() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}