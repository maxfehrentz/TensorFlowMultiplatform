package originalTensorflow

import domain.useCases.CameraHandler

class AndroidCameraHandler: CameraHandler {

    override fun checkCameraConfigurationAndStartSession() {
        println("Starting camera")
    }

    override fun stopSession() {
        println("Ending camera")
    }
}