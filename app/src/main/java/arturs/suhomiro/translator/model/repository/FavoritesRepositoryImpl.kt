package arturs.suhomiro.translator.model.repository

import arturs.suhomiro.translator.model.data.FavoritesData
import arturs.suhomiro.translator.model.data_source.RoomFavoritesDataSource

class FavoritesRepositoryImpl(
    private val roomFavoritesDataSource: RoomFavoritesDataSource
): FavoritesRepository {
    override suspend fun fetchWords(): List<Pair<FavoritesData,Boolean>> =
        roomFavoritesDataSource.fetchWords()

    override suspend fun saveToDB(favoritesData: FavoritesData) {
        roomFavoritesDataSource.saveToDB(favoritesData)
    }

    override suspend fun updateData(favoritesData: FavoritesData) {
        roomFavoritesDataSource.updateData(favoritesData)
    }

    override suspend fun deleteData(favoritesData: FavoritesData) {
        roomFavoritesDataSource.deleteData(favoritesData)
    }
}