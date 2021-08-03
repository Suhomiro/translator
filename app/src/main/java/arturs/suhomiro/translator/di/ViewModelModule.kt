package arturs.suhomiro.translator.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import arturs.suhomiro.translator.main_screen.MainViewModel
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module
internal class ViewModelModule {

    @Provides
    internal fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory = factory

    @Provides
    @IntoMap
    @ViewModelKey(MainViewModel::class)
     internal fun mainViewModel(mainViewModel: MainViewModel): ViewModel = mainViewModel
}