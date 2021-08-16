package arturs.suhomiro.translator.model.data_source

import arturs.suhomiro.translator.model.data.DataModel

interface TranslatorDataSource {
    suspend fun fetchWords(searchWord: String): List<DataModel>
}