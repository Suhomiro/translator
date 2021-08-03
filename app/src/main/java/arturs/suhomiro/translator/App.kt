package arturs.suhomiro.translator

import android.app.Application
import arturs.suhomiro.translator.di.AppComponent

class App : Application() {

companion object{
    lateinit var component: AppComponent
}
    override fun onCreate() {
        super.onCreate()
            //component = DaggerAppComponent.builder().build()
    }
}