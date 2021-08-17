package arturs.suhomiro.translator.di

import androidx.room.Room
import arturs.suhomiro.translator.interactor.FavoritesInteractorImpl
import arturs.suhomiro.translator.interactor.HistoryInteractorImpl
import arturs.suhomiro.translator.interactor.MainInteractorImpl
import arturs.suhomiro.translator.interactor.SearchHistoryInteractorImpl
import arturs.suhomiro.translator.model.data_source.RoomFavoritesDataSourceImpl
import arturs.suhomiro.translator.model.data_source.RoomHistoryDataSourceImpl
import arturs.suhomiro.translator.model.data_source.TranslatorDataSourceImpl
import arturs.suhomiro.translator.model.repository.*
import arturs.suhomiro.translator.retrofit.TranslatorApiFactory
import arturs.suhomiro.translator.room.favorites_db.FavoritesDataBase
import arturs.suhomiro.translator.room.history_db.HistoryDataBase
import arturs.suhomiro.translator.screens.favorites_screen.FavoritesViewModel
import arturs.suhomiro.translator.screens.history_screen.HistoryViewModel
import arturs.suhomiro.translator.screens.main_screen.MainViewModel
import arturs.suhomiro.translator.screens.search_history_screen.SearchHistoryViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val application = module {

    single { Room.databaseBuilder(get(), HistoryDataBase::class.java, "HistoryDB").build() }
    single { get<HistoryDataBase>().historyDao() }

    single { Room.databaseBuilder(get(), FavoritesDataBase::class.java, "FavoritesDB").build() }
    single { get<FavoritesDataBase>().favoritesDao() }

    single<TranslatorRepository>(named(NAME_REMOTE)) {
        TranslatorRepositoryImpl(
            TranslatorDataSourceImpl(TranslatorApiFactory.create())
        )
    }
    single<HistoryRepository> (named(NAME_LOCAL)) {
        HistoryRepositoryImpl(
            RoomHistoryDataSourceImpl(get())
        )
    }
    single<FavoritesRepository>(named(NAME_FAVORITES)) {
        FavoritesRepositoryImpl(
            RoomFavoritesDataSourceImpl(get())
        )
    }
    single<FavoritesRepository>(named(NAME_FAVORITES_NAME)) {
        FavoritesRepositoryImpl(
            RoomFavoritesDataSourceImpl(get())
        )
    }
}
val mainScreen = module {

    single {
        MainInteractorImpl(
            repositoryRemote = get(qualifier = named(NAME_REMOTE)),
            repositoryLocal = get(qualifier = named(NAME_LOCAL))
        )
    }

    viewModel {
      MainViewModel(mainInteractorImpl = get())
    }
}
val historySearchScreen = module {
    single {
        SearchHistoryInteractorImpl(
            repositoryLocal = get(qualifier = named(NAME_LOCAL))
        )
    }

    viewModel {
        SearchHistoryViewModel(searchHistoryInteractorImpl = get())
    }
}
val historyScreen = module {
    single {
        HistoryInteractorImpl(
            repositoryRemote = get(qualifier = named(NAME_REMOTE)),
            repositoryLocal = get(qualifier = named(NAME_LOCAL))
        )
    }

    viewModel {
        HistoryViewModel(historyInteractorImpl = get())
    }
}
val favoritesScreen = module {

    single {
        FavoritesInteractorImpl(
            favoritesRepository = get(qualifier = named(NAME_FAVORITES))
        )
    }

    viewModel {
        FavoritesViewModel(favoritesInteractorImpl = get())
    }

}