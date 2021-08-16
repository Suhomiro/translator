package arturs.suhomiro.translator.screens

import androidx.lifecycle.ViewModel
import arturs.suhomiro.translator.interactor.FavoritesInteractorImpl
import arturs.suhomiro.translator.model.data.FavoritesData
import kotlinx.coroutines.*

class AdapterViewModel(
    private val favoritesInteractorImpl: FavoritesInteractorImpl
            ): ViewModel() {

    private val viewModelCoroutineScope = CoroutineScope(
        Dispatchers.Main
                + SupervisorJob()
                + CoroutineExceptionHandler { _, throwable -> handleError(throwable)
        })

    fun saveData(favoritesData: FavoritesData) {
        cancelJob()
        viewModelCoroutineScope.launch { startInteractorSave(favoritesData) }
    }

    fun deleteData(favoritesData: FavoritesData) {
        cancelJob()
        viewModelCoroutineScope.launch { startInteractorDelete(favoritesData) }
    }

    fun updateData(favoritesData: FavoritesData) {
        cancelJob()
        viewModelCoroutineScope.launch { startInteractorUpdate(favoritesData) }
    }

    private suspend fun startInteractorSave(favoritesData: FavoritesData) =
        withContext(Dispatchers.IO) {
                favoritesInteractorImpl.saveData(favoritesData)

        }

    private suspend fun startInteractorDelete(favoritesData: FavoritesData) =
        withContext(Dispatchers.IO) {
            favoritesInteractorImpl.deleteData(favoritesData)

        }

    private suspend fun startInteractorUpdate(favoritesData: FavoritesData) =
        withContext(Dispatchers.IO) {
            favoritesInteractorImpl.updateData(favoritesData)

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