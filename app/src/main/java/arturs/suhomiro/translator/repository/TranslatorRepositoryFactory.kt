package arturs.suhomiro.translator.repository

import arturs.suhomiro.translator.data_source.CloudTranslatorDataSource
import arturs.suhomiro.translator.data_source.TranslatorDataSourceFactory

object TranslatorRepositoryFactory {
    fun create(): TranslatorRepository = TranslatorRepositoryImpl(
        TranslatorDataSourceFactory
            .create() as CloudTranslatorDataSource
    )
}