package guuilp.com.github.bppmobiletest

import android.app.Application
import guuilp.com.github.bppmobiletest.di.AppComponent
import guuilp.com.github.bppmobiletest.di.AppModule
import guuilp.com.github.bppmobiletest.di.DaggerAppComponent

open class BPPApplication: Application(){
    companion object {
        lateinit var appComponent: AppComponent
    }

    override fun onCreate() {
        super.onCreate()

        initDI()
    }

    private fun initDI() {
        appComponent = DaggerAppComponent.builder()
                .appModule(AppModule(this))
                .build()
    }
}