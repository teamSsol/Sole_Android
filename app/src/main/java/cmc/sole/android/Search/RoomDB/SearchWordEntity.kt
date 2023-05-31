package cmc.sole.android.Search.RoomDB

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "SearchTable")
data class SearchWord(
    @ColumnInfo val searchWord: String
) {
    @PrimaryKey(autoGenerate = true)
    var searchWordIndex: Int = 0
}