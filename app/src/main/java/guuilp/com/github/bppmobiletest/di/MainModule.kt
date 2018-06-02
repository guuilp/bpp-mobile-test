package guuilp.com.github.bppmobiletest.di

import dagger.Module
import dagger.Provides
import guuilp.com.github.bppmobiletest.data.BPPRepository
import guuilp.com.github.bppmobiletest.data.local.BPPDatabase
import guuilp.com.github.bppmobiletest.data.local.BPPLocal
import guuilp.com.github.bppmobiletest.data.remote.BPPService
import guuilp.com.github.bppmobiletest.ui.login.LoginViewModelFactory
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
class MainModule{
    @Provides
    @Singleton
    fun providesLoginViewModelFactory(repository: BPPRepository): LoginViewModelFactory = LoginViewModelFactory(repository)

    @Provides
    @Singleton
    fun providesBPPLocal(database: BPPDatabase): BPPLocal = BPPLocal(database)

    @Provides
    @Singleton
    fun providesBPPService(retrofit: Retrofit): BPPService = retrofit.create(BPPService::class.java)

    @Provides
    @Singleton
    fun providesRepository(local: BPPLocal, remote: BPPService): BPPRepository = BPPRepository(local, remote)
}