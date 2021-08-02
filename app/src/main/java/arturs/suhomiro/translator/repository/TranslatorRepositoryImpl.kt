package arturs.suhomiro.translator.repository

import arturs.suhomiro.translator.data.DataModel
import arturs.suhomiro.translator.data_source.CloudTranslatorDataSource
import arturs.suhomiro.translator.retrofit.TranslatorApi
import io.reactivex.Observable

class TranslatorRepositoryImpl(private val cloudTranslatorDataSource: CloudTranslatorDataSource): TranslatorRepository {

    override fun fetchWords(searchWords: String): Observable<List<DataModel>> =
        cloudTranslatorDataSource.fetchWords(searchWords)

}