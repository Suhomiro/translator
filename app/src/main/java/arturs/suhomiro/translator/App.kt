package arturs.suhomiro.translator

import android.app.Application
import arturs.suhomiro.translator.di.application
import arturs.suhomiro.translator.di.mainScreen
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(application + mainScreen)
        }
    }
}