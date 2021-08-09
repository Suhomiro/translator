package arturs.suhomiro.translator.interactor

import arturs.suhomiro.translator.data.AppState
import arturs.suhomiro.translator.repository.TranslatorRepository

class MainInteractorImpl(
    private val repositoryRemote: TranslatorRepository
): MainInteractor {

    override suspend fun getData(word: String): AppState{
        return AppState.Success(
            repositoryRemote.fetchWords(word)
        )
    }

}