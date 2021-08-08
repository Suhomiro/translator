package arturs.suhomiro.translator.repository

import arturs.suhomiro.translator.data.DataModel
import arturs.suhomiro.translator.data_source.CloudTranslatorDataSource

class TranslatorRepositoryImpl(
    private val cloudTranslatorDataSource: CloudTranslatorDataSource
    ): TranslatorRepository {

    override suspend fun fetchWords(searchWords: String): List<DataModel> =
        cloudTranslatorDataSource.fetchWords(searchWords)

}