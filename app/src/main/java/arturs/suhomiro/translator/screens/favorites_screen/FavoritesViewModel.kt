package arturs.suhomiro.translator.screens.favorites_screen

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import arturs.suhomiro.translator.interactor.FavoritesInteractorImpl
import arturs.suhomiro.translator.model.data.FavoritesData
import kotlinx.coroutines.*

class FavoritesViewModel (
    private val favoritesInteractorImpl: FavoritesInteractorImpl
): ViewModel() {

    private val liveDataForViewToObserve: MutableLiveData<List<Pair<FavoritesData, Boolean>>> = MutableLiveData()

    private val viewModelCoroutineScope = CoroutineScope(
        Dispatchers.Main
                + SupervisorJob()
                + CoroutineExceptionHandler { _, throwable -> handleError(throwable)
        })

    fun getData() = liveDataForViewToObserve

    fun getFavoritesData() {
        cancelJob()
        viewModelCoroutineScope.launch { startInteractor() }
    }

    private suspend fun startInteractor() =
        withContext(Dispatchers.IO) {
            liveDataForViewToObserve.postValue(
                favoritesInteractorImpl.getData()
            )
        }

    private fun cancelJob() {
        viewModelCoroutineScope.coroutineContext.cancelChildren()
    }

    private fun handleError(error: Throwable) {

    }

    override fun onCleared() {
        super.onCleared()
        cancelJob()
    }
}