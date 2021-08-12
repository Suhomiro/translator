package arturs.suhomiro.translator.model.data_source

import arturs.suhomiro.translator.model.data.AppState
import arturs.suhomiro.translator.model.data.DataModel
import arturs.suhomiro.translator.retrofit.TranslatorApi

class TranslatorDataSourceImpl(private val translatorApi: TranslatorApi?): TranslatorDataSource {

    override suspend fun fetchWords(searchWord: String): List<DataModel> =
        translatorApi!!.search(searchWord)

}