package guuilp.com.github.bppmobiletest.di

import android.arch.persistence.room.Room
import android.content.Context
import dagger.Module
import dagger.Provides
import guuilp.com.github.bppmobiletest.data.local.BPPDatabase
import javax.inject.Singleton

@Module
class AppModule(private val context: Context) {

    @Provides
    fun providesAppContext() = context

    @Provides
    @Singleton
    fun providesAppDatabase(context: Context): BPPDatabase = Room.databaseBuilder(context, BPPDatabase::class.java, "bpp-db").build()

    @Provides
    @Singleton
    fun providesTweetDAO(database: BPPDatabase) = database.bppDao()

}