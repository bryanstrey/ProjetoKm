package com.example.km

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.km.KmRegistro
import com.example.km.KmRegistroDao

@Database(entities = [KmRegistro::class], version = 4, exportSchema = false)
abstract class KmDatabase : RoomDatabase() {
    abstract fun kmRegistroDao(): KmRegistroDao

    companion object {
        @Volatile
        private var INSTANCE: KmDatabase? = null

        @Synchronized
        fun getDatabase(context: Context): KmDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    KmDatabase::class.java,
                    "km_database"
                )
                    .fallbackToDestructiveMigration() // apaga e recria o banco se a versão mudar
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
