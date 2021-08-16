package arturs.suhomiro.translator.model.repository

import arturs.suhomiro.translator.model.data.DataModel

interface TranslatorRepository {
    suspend fun fetchWords(searchWords: String): List<DataModel>
}