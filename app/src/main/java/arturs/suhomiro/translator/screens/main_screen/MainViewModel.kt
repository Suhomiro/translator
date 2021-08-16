package arturs.suhomiro.translator.screens.main_screen

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import arturs.suhomiro.translator.model.data.AppState
import arturs.suhomiro.translator.interactor.MainInteractorImpl
import arturs.suhomiro.translator.utils.parseOnlineSearchResults
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

    fun getTranslationData(word: String, isOnline: Boolean) {
        liveDataForViewToObserve.value = AppState.Loading(null)
        cancelJob()
        viewModelCoroutineScope.launch { startInteractor(word, isOnline) }
    }

    private suspend fun startInteractor(word: String, isOnline: Boolean) =
        withContext(Dispatchers.IO) {
        liveDataForViewToObserve.postValue(
            parseOnlineSearchResults(mainInteractorImpl.getData(word, isOnline))
        )
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