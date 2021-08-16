package arturs.suhomiro.translator.model.repository

import arturs.suhomiro.translator.model.data.AppState
import arturs.suhomiro.translator.model.data.DataModel

interface HistoryRepository{
    suspend fun fetchWords(searchWords: String): List<DataModel>
    suspend fun saveToDB(appState: AppState)
    suspend fun searchByWords(searchWords: String): List<DataModel>
}