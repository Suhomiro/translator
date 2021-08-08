package arturs.suhomiro.translator.interactor

import arturs.suhomiro.translator.data.AppState

interface MainInteractor {

    suspend fun getData(word: String): AppState
}