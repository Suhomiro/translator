package arturs.suhomiro.translator.model.data_source

import arturs.suhomiro.translator.model.data.FavoritesData
import arturs.suhomiro.translator.room.favorites_db.FavoritesDao
import arturs.suhomiro.translator.utils.convertFavoriteDataToFavoriteEntity
import arturs.suhomiro.translator.utils.mapFavoriteEntityToFavoriteData

class RoomFavoritesDataSourceImpl(
    private val favoritesDao: FavoritesDao
): RoomFavoritesDataSource {
    override suspend fun fetchWords(): List<Pair<FavoritesData,Boolean>> {
        return mapFavoriteEntityToFavoriteData(favoritesDao.all())
    }

    override suspend fun saveToDB(favoritesData: FavoritesData) {
        favoritesDao.insert(convertFavoriteDataToFavoriteEntity(favoritesData))
    }

    override suspend fun updateData(favoritesData: FavoritesData) {
        favoritesDao.update(convertFavoriteDataToFavoriteEntity(favoritesData))
    }

    override suspend fun deleteData(favoritesData: FavoritesData) {
        favoritesDao.delete(convertFavoriteDataToFavoriteEntity(favoritesData))
    }
}