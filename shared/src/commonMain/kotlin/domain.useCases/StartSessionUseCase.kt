package domain.useCases

import org.koin.core.KoinComponent
import org.koin.core.inject

class StartSessionUseCase(private val cameraHandler: CameraHandler) {

    fun invoke() {
        cameraHandler.checkCameraConfigurationAndStartSession()
    }
}