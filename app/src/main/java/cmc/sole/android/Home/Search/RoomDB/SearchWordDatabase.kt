package cmc.sole.android.Home.Search.RoomDB

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [SearchWord::class], version = 1)
abstract class SearchWordDatabase: RoomDatabase() {
    abstract fun searchWordDao(): SearchWordDao

    companion object {
        private var instance: SearchWordDatabase? = null

        @Synchronized
        fun getDBInstance(context: Context) : SearchWordDatabase? {
            if (instance == null) {
                synchronized(SearchWordDatabase::class) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        SearchWordDatabase::class.java,
                        "search-word-database"
                    ).fallbackToDestructiveMigration().build()
                }
            }
            return instance
        }
    }
}