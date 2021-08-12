package arturs.suhomiro.translator.model.repository

import arturs.suhomiro.translator.model.data.DataModel
import arturs.suhomiro.translator.model.data_source.TranslatorDataSourceImpl

class TranslatorRepositoryImpl(
    private val translatorDataSource: TranslatorDataSourceImpl
    ): TranslatorRepository {

    override suspend fun fetchWords(searchWords: String): List<DataModel> =
        translatorDataSource.fetchWords(searchWords)

}