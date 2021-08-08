package arturs.suhomiro.translator.repository

import arturs.suhomiro.translator.data.DataModel

interface TranslatorRepository {
    suspend fun fetchWords(searchWords: String): List<DataModel>
}