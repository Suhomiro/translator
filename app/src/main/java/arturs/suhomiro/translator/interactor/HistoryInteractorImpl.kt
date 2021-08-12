package arturs.suhomiro.translator.interactor

import arturs.suhomiro.translator.model.data.AppState
import arturs.suhomiro.translator.model.repository.HistoryRepository
import arturs.suhomiro.translator.model.repository.TranslatorRepository

class HistoryInteractorImpl(
    private val repositoryRemote: TranslatorRepository,
    private val repositoryLocal: HistoryRepository
) : HistoryInteractor {

    override suspend fun getData(word: String, fromRemoteSource: Boolean): AppState {
        return AppState.Success(
            if (fromRemoteSource) {
                repositoryRemote.fetchWords(word)
            } else {
                repositoryLocal.fetchWords(word)
            }
        )
    }
}