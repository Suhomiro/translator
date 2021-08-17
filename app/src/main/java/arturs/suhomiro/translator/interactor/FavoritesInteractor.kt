package arturs.suhomiro.translator.interactor

import arturs.suhomiro.translator.model.data.FavoritesData

interface FavoritesInteractor {

    suspend fun getData(): List<Pair<FavoritesData,Boolean>>
    suspend fun saveData(favoritesData: FavoritesData)
    suspend fun updateData(favoritesData: FavoritesData)
    suspend fun deleteData(favoritesData: FavoritesData)
}