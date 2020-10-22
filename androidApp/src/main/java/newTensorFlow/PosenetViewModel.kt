package newTensorFlow

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import domain.useCases.CameraHandler
import domain.useCases.EndSessionUseCase
import domain.useCases.RunModelUseCase
import domain.useCases.StartSessionUseCase
import originalTensorflow.AndroidCameraHandler

class PosenetViewModel: ViewModel() {

    private val cameraHandler : CameraHandler = AndroidCameraHandler()
    private val startSessionUseCase: StartSessionUseCase = StartSessionUseCase(cameraHandler)
    private val endSessionUseCase: EndSessionUseCase = EndSessionUseCase(cameraHandler)
    private val runModelUseCase: RunModelUseCase = RunModelUseCase()
    var drawingContent = MutableLiveData<DrawingContent>()

    fun setupSession(){

    }

    fun runModel(){

    }

    fun stopSession(){
        cameraHandler.stopSession()
    }
}