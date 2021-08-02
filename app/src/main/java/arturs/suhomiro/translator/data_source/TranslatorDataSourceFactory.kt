package arturs.suhomiro.translator.data_source

import arturs.suhomiro.translator.retrofit.TranslatorApiFactory

object TranslatorDataSourceFactory{

    fun create(): TranslatorDataSource = CloudTranslatorDataSource(TranslatorApiFactory.create())
}