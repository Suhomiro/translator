package arturs.suhomiro.translator.di

import arturs.suhomiro.translator.data_source.CloudTranslatorDataSource
import arturs.suhomiro.translator.data_source.TranslatorDataSource
import arturs.suhomiro.translator.repository.TranslatorRepository
import arturs.suhomiro.translator.repository.TranslatorRepositoryImpl
import arturs.suhomiro.translator.retrofit.TranslatorApiFactory
import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Provides
    @Singleton
    @Named(NAME_REMOTE)
    internal fun provideRepositoryRemote(@Named(NAME_REMOTE) cloudTranslatorDataSource: CloudTranslatorDataSource): TranslatorRepository =
        TranslatorRepositoryImpl(cloudTranslatorDataSource)


    @Provides
    @Singleton
    @Named(NAME_REMOTE)
    internal fun provideDataSourceRemote(): TranslatorDataSource =
        CloudTranslatorDataSource(TranslatorApiFactory.create())

}