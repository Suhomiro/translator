package arturs.suhomiro.translator.utils

import arturs.suhomiro.translator.model.data.AppState
import arturs.suhomiro.translator.model.data.DataModel
import arturs.suhomiro.translator.model.data.FavoritesData
import arturs.suhomiro.translator.model.data.Meanings
import arturs.suhomiro.translator.room.favorites_db.FavoritesEntity
import arturs.suhomiro.translator.room.history_db.HistoryEntity

fun parseOnlineSearchResults(state: AppState): AppState {
    return AppState.Success(mapResult(state, true))
}

fun parseLocalSearchResults(data: AppState): AppState {
    return AppState.Success(mapResult(data, false))
}

private fun mapResult(
    data: AppState,
    isOnline: Boolean
): List<DataModel> {
    val newSearchResults = arrayListOf<DataModel>()
    when (data) {
        is AppState.Success -> {
            getSuccessResultData(data, isOnline, newSearchResults)
        }
    }
    return newSearchResults
}

private fun getSuccessResultData(
    data: AppState.Success,
    isOnline: Boolean,
    newDataModels: ArrayList<DataModel>
) {
    val dataModels: List<DataModel> = data.data as List<DataModel>
    if (dataModels.isNotEmpty()) {
        if (isOnline) {
            for (searchResult in dataModels) {
                parseOnlineResult(searchResult, newDataModels)
            }
        } else {
            for (searchResult in dataModels) {
                newDataModels.add(DataModel(searchResult.text, arrayListOf()))
            }
        }
    }
}

private fun parseOnlineResult(dataModel: DataModel, newDataModels: ArrayList<DataModel>) {
    if (!dataModel.text.isNullOrBlank() && !dataModel.meanings.isNullOrEmpty()) {
        val newMeanings = arrayListOf<Meanings>()
        for (meaning in dataModel.meanings) {
            if (meaning.translation != null && !meaning.translation.translation.isNullOrBlank()) {
                newMeanings.add(Meanings(meaning.translation, meaning.imageUrl))
            }
        }
        if (newMeanings.isNotEmpty()) {
            newDataModels.add(DataModel(dataModel.text, newMeanings))
        }
    }
}

fun mapHistoryEntityToSearchResult(list: List<HistoryEntity>): List<DataModel> {
    val searchResult = ArrayList<DataModel>()
    if (!list.isNullOrEmpty()) {
        for (entity in list) {
            searchResult.add(DataModel(entity.word, null))
        }
    }
    return searchResult
}

fun mapFavoriteEntityToFavoriteData(list: List<FavoritesEntity>): List<Pair<FavoritesData,Boolean>> {
    return list.map {
        Pair(FavoritesData(
            it.word,
            it.description,
            it.note), false)
    }
}

fun convertDataModelListToEntityList(appState: AppState): List<HistoryEntity>? {
    return when (appState){
        is AppState.Success-> {
            appState.data?.map {
                HistoryEntity(
                    it.text,
                    it.meanings?.first()?.translation?.translation
                )
            }
        }
        else -> null
    }
}

fun convertFavoriteDataToFavoriteEntity(favoritesData: FavoritesData): FavoritesEntity {
    return FavoritesEntity(
        favoritesData.word,
        favoritesData.description,
        favoritesData.note
        )
}

fun converterDataModelToFavoritesData(dataModel: DataModel) : FavoritesData {
    return FavoritesData(
        dataModel.text,
        "",
        ""
    )
}

fun convertDataModelSuccessToEntity(appState: AppState): HistoryEntity? {
    return when (appState) {
        is AppState.Success -> {
            val searchResult = appState.data
            if (searchResult.isNullOrEmpty() || searchResult[0].text.isEmpty()) {
                null
            } else {
                HistoryEntity(searchResult[0].text, null)
            }
        }
        else -> null
    }
}


fun convertMeaningsToString(meanings: List<Meanings>): String {
    var meaningsSeparatedByComma = String()
    for ((index, meaning) in meanings.withIndex()) {
        meaningsSeparatedByComma += if (index + 1 != meanings.size) {
            String.format("%s%s", meaning.translation?.translation, ", ")
        } else {
            meaning.translation?.translation
        }
    }
    return meaningsSeparatedByComma

}