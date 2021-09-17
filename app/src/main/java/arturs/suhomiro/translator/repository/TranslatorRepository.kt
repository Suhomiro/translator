package arturs.suhomiro.translator.repository

import arturs.suhomiro.translator.data.DataModel
import io.reactivex.Observable

interface TranslatorRepository {
    fun fetchWords(searchWords: String): Observable<List<DataModel>>
}