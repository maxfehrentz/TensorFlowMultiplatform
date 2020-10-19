package domain.useCases

import org.koin.core.KoinComponent
import org.koin.core.inject

class EndSessionUseCase(private val cameraHandler: CameraHandler) {

    fun invoke() {
        cameraHandler.stopSession()
    }
}