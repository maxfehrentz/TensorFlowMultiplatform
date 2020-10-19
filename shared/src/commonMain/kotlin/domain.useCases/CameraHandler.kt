package domain.useCases

interface CameraHandler {

    fun checkCameraConfigurationAndStartSession()
    fun stopSession()
}