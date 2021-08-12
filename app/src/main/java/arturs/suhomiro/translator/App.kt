package arturs.suhomiro.translator

import android.app.Application
import arturs.suhomiro.translator.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(application + mainScreen + historySearchScreen + historyScreen + favoritesScreen)
        }
    }
}