package guuilp.com.github.bppmobiletest.ui.invoice

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import guuilp.com.github.bppmobiletest.BPPApplication
import guuilp.com.github.bppmobiletest.R
import guuilp.com.github.bppmobiletest.data.local.Invoice
import guuilp.com.github.bppmobiletest.ui.login.LoginActivity
import guuilp.com.github.bppmobiletest.ui.login.LoginViewModel
import guuilp.com.github.bppmobiletest.utils.PreferenceHelper
import guuilp.com.github.bppmobiletest.utils.PreferenceHelper.get
import guuilp.com.github.bppmobiletest.utils.PreferenceHelper.set
import guuilp.com.github.bppmobiletest.utils.Status
import kotlinx.android.synthetic.main.activity_invoice_list.*
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.design.longSnackbar
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import java.util.ArrayList
import javax.inject.Inject

class InvoiceListActivity: AppCompatActivity(){

    @Inject
    lateinit var viewModelFactory: InvoiceListViewModelFactory
    private lateinit var viewModel: InvoiceListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_invoice_list)

        BPPApplication.appComponent.inject(this)

        supportActionBar?.title = "Invoice List"

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(InvoiceListViewModel::class.java)

        val prefs = PreferenceHelper.defaultPrefs(this)
        val isUserLogged = prefs["logged", false]

        if(isUserLogged != null && isUserLogged){
            continueLoggedUser()
        } else {
            startActivity<LoginActivity>()
        }
    }

    private fun continueLoggedUser(){
        val invoiceListAdapter = InvoiceListAdapter(ArrayList(), this) { invoice, _ ->
            toast("Click on item")
        }

        rvInvoiceList.adapter = invoiceListAdapter

        viewModel.setUserLogged(true, true)

        viewModel.invoiceList.observe(this, Observer {
            it?.let {
                //                pbTweetList.visibility = View.GONE
                invoiceListAdapter.updateDataSet(ArrayList())

                when (it.status) {
                    Status.SUCCESS -> {
                        if (it.data != null && it.data.isNotEmpty()) {
                            rvInvoiceList.visibility = View.VISIBLE
                            invoiceListAdapter.updateDataSet(it.data as ArrayList<Invoice>)
                        } else {

                        }
                    }
                    Status.ERROR -> {
                        longSnackbar(clLogin, it.message.toString())
                    }
                    Status.LOADING -> {

                    }
                }
            }
        })
    }
}