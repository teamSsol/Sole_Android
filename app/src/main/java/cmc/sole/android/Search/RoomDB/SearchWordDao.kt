package cmc.sole.android.Search.RoomDB

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface SearchWordDao {
    @Query("Select * from SearchTable")
    fun getAllWord(): List<SearchWord>

    @Insert
    fun insert(searchWord: SearchWord)

    @Query("Delete from SearchTable")
    fun deleteAllRecentWord()

    @Query("Delete from SearchTable WHERE searchWord = :searchWord")
    fun deleteWord(searchWord: String)
    
    /*
    MEMO: 최근 검색어 저장, 최근 검색어 하나 삭제, 전체 삭제
     */
    @Query("Delete from SearchTable WHERE searchWordIndex = :searchWordIndex")
    fun deleteIndexRecentWord(searchWordIndex: Int)
}