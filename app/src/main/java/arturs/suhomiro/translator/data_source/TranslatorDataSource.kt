package arturs.suhomiro.translator.data_source

import arturs.suhomiro.translator.data.DataModel

interface TranslatorDataSource {
    suspend fun fetchWords(searchWord: String): List<DataModel>
}