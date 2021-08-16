package arturs.suhomiro.translator.interactor

import arturs.suhomiro.translator.model.data.AppState
import arturs.suhomiro.translator.model.repository.HistoryRepository
import arturs.suhomiro.translator.model.repository.TranslatorRepository

class MainInteractorImpl(
    private val repositoryRemote: TranslatorRepository,
    private val repositoryLocal: HistoryRepository
) : MainInteractor {

    override suspend fun getData(word: String, fromRemoteSource: Boolean): AppState {
        val appState: AppState
        if (fromRemoteSource) {
            appState = AppState.Success(repositoryRemote.fetchWords(word))
            repositoryLocal.saveToDB(appState)
        } else {
            appState = AppState.Success(repositoryLocal.fetchWords(word))
        }
        return appState
    }
}