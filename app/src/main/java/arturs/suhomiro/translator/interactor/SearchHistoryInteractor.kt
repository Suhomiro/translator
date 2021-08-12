package arturs.suhomiro.translator.interactor

import arturs.suhomiro.translator.model.data.AppState

interface SearchHistoryInteractor {
    suspend fun getDataByWords(word: String, fromRemoteSource: Boolean): AppState
}