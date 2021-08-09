package arturs.suhomiro.translator.main_screen

import arturs.suhomiro.translator.data.DataModel

interface MainView {
    fun init(dataModel: List<DataModel>)
    fun showError(error: Throwable)
}