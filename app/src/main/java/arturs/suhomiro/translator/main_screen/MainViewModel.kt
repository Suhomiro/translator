package arturs.suhomiro.translator.main_screen

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import arturs.suhomiro.translator.data.AppState
import arturs.suhomiro.translator.repository.TranslatorRepository
import arturs.suhomiro.translator.scheduler.DefaultSchedulers
import arturs.suhomiro.translator.scheduler.Schedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val translatorRepository: TranslatorRepository,
    private val liveDataForViewToObserve: MutableLiveData<AppState> = MutableLiveData(),
    private val compositeDisposable: CompositeDisposable = CompositeDisposable(),
    private val schedulerProvider: Schedulers = DefaultSchedulers
): ViewModel() {

    fun getData() = liveDataForViewToObserve

    fun getTranslationData(word: String) {
        liveDataForViewToObserve.value = AppState.Loading(progress = null)
            compositeDisposable +=
                translatorRepository
                    .fetchWords(word)
                    .observeOn(schedulerProvider.main())
                    .subscribeOn(schedulerProvider.background())
                    .subscribe({
                       liveDataForViewToObserve.value = AppState.Success(it)
                    }, {
                        liveDataForViewToObserve.value = AppState.Error(it)
                        }
                    )
    }

    override fun onCleared() {
        compositeDisposable.clear()
    }
}