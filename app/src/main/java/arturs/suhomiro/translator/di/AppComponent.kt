package arturs.suhomiro.translator.di

import arturs.suhomiro.translator.main_screen.MainActivity
import dagger.Component
import javax.inject.Singleton

@Component(
    modules = [
        RepositoryModule::class,
        ViewModelModule::class]
)
@Singleton
interface AppComponent {

    @Component.Builder
    interface Builder {
        fun build(): AppComponent
    }

    fun inject(activity: MainActivity)
}