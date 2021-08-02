package arturs.suhomiro.translator.main_screen

import arturs.suhomiro.translator.data.DataModel
import arturs.suhomiro.translator.repository.TranslatorRepository
import arturs.suhomiro.translator.scheduler.Schedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign

class MainPresenter(
    private val mainView: MainView,
    private val repository: TranslatorRepository,
    private val schedulers: Schedulers
) {
    private var disposables =  CompositeDisposable()

    fun loadData(searchWord: String){
        disposables +=
            repository
                .fetchWords(searchWord)
                .observeOn(schedulers.main())
                .subscribeOn(schedulers.background())
                .subscribe(
                    :: fetchWeatherSuccess,
                    :: fetchWeatherOnError
                )
    }

    private fun fetchWeatherSuccess(dataModel: List<DataModel>) {
        mainView.init(dataModel)
    }

    private fun fetchWeatherOnError(error: Throwable){
        mainView.showError(error)
    }

    fun destroy(){
        disposables.dispose()
    }
}