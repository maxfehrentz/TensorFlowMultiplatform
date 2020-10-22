package domain.useCases

import org.koin.core.KoinComponent
import org.koin.core.inject

open class StartSessionUseCase(private val cameraHandler: CameraHandler) {

    fun invoke() {
        cameraHandler.checkCameraConfigurationAndStartSession()
    }
}