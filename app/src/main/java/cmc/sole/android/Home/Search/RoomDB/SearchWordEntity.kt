package cmc.sole.android.Home.Search.RoomDB

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "SearchTable")
data class SearchWord(
    @PrimaryKey val searchWord: String
)