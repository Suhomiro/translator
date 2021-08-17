package arturs.suhomiro.translator.model.data_source

import arturs.suhomiro.translator.model.data.FavoritesData

interface RoomFavoritesDataSource {
    suspend fun fetchWords(): List<Pair<FavoritesData,Boolean>>
    suspend fun saveToDB(favoritesData: FavoritesData)
    suspend fun updateData(favoritesData: FavoritesData)
    suspend fun deleteData(favoritesData: FavoritesData)
}