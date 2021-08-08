package arturs.suhomiro.translator.di

import arturs.suhomiro.translator.data_source.CloudTranslatorDataSource
import arturs.suhomiro.translator.interactor.MainInteractorImpl
import arturs.suhomiro.translator.main_screen.MainViewModel
import arturs.suhomiro.translator.repository.TranslatorRepository
import arturs.suhomiro.translator.repository.TranslatorRepositoryImpl
import arturs.suhomiro.translator.retrofit.TranslatorApiFactory
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val application = module {

    single<TranslatorRepository>(named(NAME_REMOTE)) {
        TranslatorRepositoryImpl(
            CloudTranslatorDataSource(TranslatorApiFactory.create())
        )
    }
}
val mainScreen = module {

    single {
        MainInteractorImpl(
            repositoryRemote = get(qualifier = named(NAME_REMOTE))
        )
    }

    viewModel {
      MainViewModel(mainInteractorImpl = get())
    }
}