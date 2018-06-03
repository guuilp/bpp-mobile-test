package guuilp.com.github.bppmobiletest.di

import android.content.Context
import dagger.Component
import guuilp.com.github.bppmobiletest.BPPApplication
import guuilp.com.github.bppmobiletest.ui.invoice.InvoiceListActivity
import guuilp.com.github.bppmobiletest.ui.login.LoginActivity
import javax.inject.Singleton

@Component(modules = [(AppModule::class), (NetworkModule::class), (MainModule::class)])
@Singleton
interface AppComponent{

    fun inject(application: BPPApplication)

    fun inject(loginActivity: LoginActivity)

    fun inject(invoiceListActivity: InvoiceListActivity)

    fun context(): Context
}