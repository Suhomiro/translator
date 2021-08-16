package arturs.suhomiro.translator.room.favorites_db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [FavoritesEntity::class], version = 1, exportSchema = false)
abstract class FavoritesDataBase : RoomDatabase() {

    abstract fun favoritesDao(): FavoritesDao
}