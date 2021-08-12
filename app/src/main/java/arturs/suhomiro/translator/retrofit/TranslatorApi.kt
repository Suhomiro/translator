package arturs.suhomiro.translator.retrofit

import arturs.suhomiro.translator.model.data.DataModel
import retrofit2.http.GET
import retrofit2.http.Query

interface TranslatorApi {

    @GET("words/search")
    suspend fun search(@Query("search") wordToSearch: String): List<DataModel>
}
