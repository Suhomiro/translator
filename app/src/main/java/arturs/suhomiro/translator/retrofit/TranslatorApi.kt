package arturs.suhomiro.translator.retrofit

import arturs.suhomiro.translator.data.DataModel
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface TranslatorApi {

    @GET("words/search")
    fun search(@Query("search") wordToSearch: String): Observable<List<DataModel>>
}
