import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.km.KmRegistro

@Database(entities = [KmRegistro::class], version = 1, exportSchema = false)
abstract class KmDatabase : RoomDatabase() {
    abstract fun kmRegistroDao(): KmRegistroDao

    companion object {
        @Volatile
        private var INSTANCE: KmDatabase? = null

        fun getDatabase(context: Context): KmDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    KmDatabase::class.java,
                    "km_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
