package arturs.suhomiro.translator.model.data_source

import arturs.suhomiro.translator.model.data.AppState
import arturs.suhomiro.translator.model.data.DataModel

interface RoomHistoryDataSource {
    suspend fun fetchWords(searchWord: String): List<DataModel>
    suspend fun saveToDB(appState: AppState)
    suspend fun getDataByWord(searchWord: String): List<DataModel>
}