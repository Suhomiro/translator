package arturs.suhomiro.translator.data_source

import arturs.suhomiro.translator.data.DataModel
import arturs.suhomiro.translator.retrofit.TranslatorApi
import io.reactivex.Observable

class CloudTranslatorDataSource(private val translatorApi: TranslatorApi?): TranslatorDataSource {

    override fun fetchWords(searchWord: String): Observable<List<DataModel>> =
        translatorApi!!.search(searchWord)
}