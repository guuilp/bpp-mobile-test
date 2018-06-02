package guuilp.com.github.bppmobiletest.data.local

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase

@Database(entities = [(Invoice::class)], version = 1, exportSchema = false)
abstract class BPPDatabase: RoomDatabase(){

    abstract fun bppDao(): BPPDao

}