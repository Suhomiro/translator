package arturs.suhomiro.translator.interactor

import arturs.suhomiro.translator.model.data.AppState

interface MainInteractor {

    suspend fun getData(word: String, fromRemoteSource: Boolean): AppState
}