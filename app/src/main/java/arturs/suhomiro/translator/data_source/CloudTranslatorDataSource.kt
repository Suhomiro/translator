package arturs.suhomiro.translator.data_source

import arturs.suhomiro.translator.data.DataModel
import arturs.suhomiro.translator.retrofit.TranslatorApi

class CloudTranslatorDataSource(private val translatorApi: TranslatorApi?): TranslatorDataSource {

    override suspend fun fetchWords(searchWord: String): List<DataModel> =
        translatorApi!!.search(searchWord)
}