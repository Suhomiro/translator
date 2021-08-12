package arturs.suhomiro.translator.room.favorites_db

import androidx.room.*

@Dao
interface FavoritesDao {

    @Query("SELECT * FROM FavoritesEntity")
    suspend fun all(): List<FavoritesEntity>

    @Query("SELECT * FROM FavoritesEntity WHERE word LIKE :word")
    suspend fun getDataByWord(word: String): List<FavoritesEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(entity: FavoritesEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(entities: List<FavoritesEntity>)

    @Update
    suspend fun update(entity: FavoritesEntity)

    @Delete
    suspend fun delete(entity: FavoritesEntity)
}