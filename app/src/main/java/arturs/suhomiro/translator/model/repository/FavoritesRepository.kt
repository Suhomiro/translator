package arturs.suhomiro.translator.model.repository

import arturs.suhomiro.translator.model.data.FavoritesData

interface FavoritesRepository {
    suspend fun fetchWords(): List<Pair<FavoritesData,Boolean>>
    suspend fun saveToDB(favoritesData: FavoritesData)
    suspend fun updateData(favoritesData: FavoritesData)
    suspend fun deleteData(favoritesData: FavoritesData)
}