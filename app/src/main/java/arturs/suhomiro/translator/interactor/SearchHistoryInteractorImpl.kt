package arturs.suhomiro.translator.interactor

import arturs.suhomiro.translator.model.data.AppState
import arturs.suhomiro.translator.model.repository.HistoryRepository

class SearchHistoryInteractorImpl(
    private val repositoryLocal: HistoryRepository
): SearchHistoryInteractor {

    override suspend fun getDataByWords(word: String, fromRemoteSource: Boolean): AppState {
         val appState: AppState
         appState = AppState.Success(
             repositoryLocal.searchByWords(searchWords = word)
         )
        return appState
    }
}