package arturs.suhomiro.translator.data_source

import arturs.suhomiro.translator.data.DataModel
import io.reactivex.Observable

interface TranslatorDataSource {
    fun fetchWords(searchWord: String): Observable<List<DataModel>>
}