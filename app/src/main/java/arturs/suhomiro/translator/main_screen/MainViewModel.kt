package arturs.suhomiro.translator.main_screen

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import arturs.suhomiro.translator.data.AppState
import arturs.suhomiro.translator.interactor.MainInteractorImpl
import kotlinx.coroutines.*

class MainViewModel (
    private val mainInteractorImpl: MainInteractorImpl
): ViewModel() {

    private val liveDataForViewToObserve: MutableLiveData<AppState> = MutableLiveData()

    private val viewModelCoroutineScope = CoroutineScope(
        Dispatchers.Main
                + SupervisorJob()
                + CoroutineExceptionHandler { _, throwable -> handleError(throwable)
        })

    fun getData() = liveDataForViewToObserve

    fun getTranslationData(word: String) {
        liveDataForViewToObserve.value = AppState.Loading(null)
        cancelJob()
        viewModelCoroutineScope.launch { startInteractor(word) }
    }

    private suspend fun startInteractor(word: String) =
        withContext(Dispatchers.IO) {
        liveDataForViewToObserve.postValue(mainInteractorImpl.getData(word))
    }

    private fun cancelJob() {
        viewModelCoroutineScope.coroutineContext.cancelChildren()
    }

    private fun handleError(error: Throwable) {
        liveDataForViewToObserve.postValue(AppState.Error(error))
    }

    override fun onCleared() {
        liveDataForViewToObserve.value = AppState.Success(null)
        super.onCleared()
        cancelJob()
    }
}