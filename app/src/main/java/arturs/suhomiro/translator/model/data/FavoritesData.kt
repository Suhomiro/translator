package arturs.suhomiro.translator.model.data

data class FavoritesData(
    var word: String,
    var description: String?,
    var note: String?
        )
fun getFavoritesData(): MutableList<Pair<FavoritesData, Boolean>> {
    return mutableListOf(
        Pair(FavoritesData("", "", ""), false)
    )
}