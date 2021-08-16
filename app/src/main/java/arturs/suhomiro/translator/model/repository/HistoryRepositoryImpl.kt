package arturs.suhomiro.translator.model.repository

import arturs.suhomiro.translator.model.data.AppState
import arturs.suhomiro.translator.model.data.DataModel
import arturs.suhomiro.translator.model.data_source.RoomHistoryDataSource

class HistoryRepositoryImpl(
    private val roomDataSource: RoomHistoryDataSource
): HistoryRepository {

    override suspend fun fetchWords(searchWords: String): List<DataModel> =
        roomDataSource.fetchWords(searchWords)

    override suspend fun saveToDB(appState: AppState) {
        roomDataSource.saveToDB(appState)
    }

    override suspend fun searchByWords(searchWords: String): List<DataModel> =
        roomDataSource.getDataByWord(searchWords)


}