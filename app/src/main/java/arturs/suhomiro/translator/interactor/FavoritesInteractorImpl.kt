package arturs.suhomiro.translator.interactor

import arturs.suhomiro.translator.model.data.FavoritesData
import arturs.suhomiro.translator.model.repository.FavoritesRepository

class FavoritesInteractorImpl(
    private val favoritesRepository: FavoritesRepository
): FavoritesInteractor {
    override suspend fun getData(): List<Pair<FavoritesData,Boolean>> =
        favoritesRepository.fetchWords()

    override suspend fun saveData(favoritesData: FavoritesData) {
        favoritesRepository.saveToDB(favoritesData)
    }

    override suspend fun updateData(favoritesData: FavoritesData) {
        favoritesRepository.updateData(favoritesData)
    }

    override suspend fun deleteData(favoritesData: FavoritesData) {
        favoritesRepository.deleteData(favoritesData)
    }
}