package guuilp.com.github.bppmobiletest.ui.invoice

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import guuilp.com.github.bppmobiletest.BPPApplication
import guuilp.com.github.bppmobiletest.R
import guuilp.com.github.bppmobiletest.data.local.Invoice
import guuilp.com.github.bppmobiletest.network.Resource
import guuilp.com.github.bppmobiletest.ui.login.LoginActivity
import guuilp.com.github.bppmobiletest.utils.PreferenceHelper
import guuilp.com.github.bppmobiletest.utils.PreferenceHelper.get
import guuilp.com.github.bppmobiletest.utils.PreferenceHelper.set
import guuilp.com.github.bppmobiletest.utils.Status
import kotlinx.android.synthetic.main.activity_invoice_list.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.design.snackbar
import org.jetbrains.anko.noButton
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.yesButton
import java.util.*
import javax.inject.Inject

class InvoiceListActivity: AppCompatActivity(){

    @Inject
    lateinit var viewModelFactory: InvoiceListViewModelFactory
    private lateinit var viewModel: InvoiceListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_invoice_list)

        BPPApplication.appComponent.inject(this)

        supportActionBar?.title = getString(R.string.invoice_screen_toolbar_name)

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(InvoiceListViewModel::class.java)

        val prefs = PreferenceHelper.defaultPrefs(this)
        val isUserLogged = prefs[PreferenceHelper.USER_LOGGED, false]

        if(isUserLogged != null && isUserLogged){
            continueLoggedUser()
        } else {
            startActivity<LoginActivity>()
            finish()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.invoice_list_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            R.id.logout -> {
                alert(getString(R.string.dialog_logout_text), getString(R.string.dialog_logout_title)) {
                    yesButton {
                        logUserOut()
                    }

                    noButton { }
                }.show()
            }
        }

        return true
    }

    private fun continueLoggedUser(){
        val invoiceListAdapter = InvoiceListAdapter(ArrayList(), this) { invoice, _ -> snackbar(clMain, "You clicked on ${invoice.transactionName}" ) }

        rvInvoiceList.adapter = invoiceListAdapter

        viewModel.setUserLogged(true)

        setupObservables(invoiceListAdapter)
    }

    private fun setupObservables(invoiceListAdapter: InvoiceListAdapter) {
        viewModel.invoiceList.observe(this, Observer {
            it?.let {
                pbInvoiceList.visibility = View.GONE
                invoiceListAdapter.updateDataSet(ArrayList())

                when (it.status) {
                    Status.SUCCESS -> {
                        if (it.data != null && it.data.isNotEmpty()) {
                            showInvoiceList(invoiceListAdapter, it)
                        } else {
                            rvInvoiceList.visibility = View.GONE
                            emptyState.visibility = View.VISIBLE
                        }
                    }
                    Status.ERROR -> {
                        if(it.data != null && it.data.isNotEmpty()){
                            showInvoiceList(invoiceListAdapter, it)
                        } else {
                            logUserOut()
                        }
                    }
                    Status.LOADING -> {
                        pbInvoiceList.visibility = View.VISIBLE
                    }
                }
            }
        })
    }

    private fun logUserOut() {
        val prefs = PreferenceHelper.defaultPrefs(this@InvoiceListActivity)
        prefs[PreferenceHelper.USER_LOGGED] = false

        startActivity<LoginActivity>()
        finish()
    }

    private fun showInvoiceList(invoiceListAdapter: InvoiceListAdapter, it: Resource<List<Invoice>>) {
        rvInvoiceList.visibility = View.VISIBLE
        invoiceListAdapter.updateDataSet(it.data as ArrayList<Invoice>)
    }
}