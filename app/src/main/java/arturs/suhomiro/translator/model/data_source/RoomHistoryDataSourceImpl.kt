package arturs.suhomiro.translator.model.data_source

import arturs.suhomiro.translator.model.data.AppState
import arturs.suhomiro.translator.model.data.DataModel
import arturs.suhomiro.translator.room.history_db.HistoryDao
import arturs.suhomiro.translator.utils.convertDataModelListToEntityList
import arturs.suhomiro.translator.utils.mapHistoryEntityToSearchResult

class RoomHistoryDataSourceImpl(
    private val historyDao: HistoryDao
    ) :
    RoomHistoryDataSource {

    override suspend fun fetchWords(searchWord: String): List<DataModel> {
        return mapHistoryEntityToSearchResult(historyDao.all())
    }

    override suspend fun saveToDB(appState: AppState) {
        convertDataModelListToEntityList(appState)?.let { historyDao.insertAll(it) }
    }

    override suspend fun getDataByWord(searchWord: String): List<DataModel> {
        return mapHistoryEntityToSearchResult(historyDao.getDataByWord(searchWord))
    }
}