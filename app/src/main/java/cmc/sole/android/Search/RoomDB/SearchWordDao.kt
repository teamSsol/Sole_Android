package cmc.sole.android.Search.RoomDB

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface SearchWordDao {
    @Insert
    fun insert(searchWord: SearchWord)

    @Query("Delete from SearchTable")
    fun deleteAllSearchWord()
}