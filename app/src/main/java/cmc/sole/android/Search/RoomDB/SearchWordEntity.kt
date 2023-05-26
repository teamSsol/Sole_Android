package cmc.sole.android.Search.RoomDB

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "SearchTable")
data class SearchWord(
    @PrimaryKey val searchWord: String
)