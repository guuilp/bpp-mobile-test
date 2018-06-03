package guuilp.com.github.bppmobiletest.ui.login

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import guuilp.com.github.bppmobiletest.BPPApplication
import guuilp.com.github.bppmobiletest.R
import guuilp.com.github.bppmobiletest.ui.invoice.InvoiceListActivity
import guuilp.com.github.bppmobiletest.utils.PreferenceHelper
import guuilp.com.github.bppmobiletest.utils.PreferenceHelper.set
import guuilp.com.github.bppmobiletest.utils.Status
import guuilp.com.github.bppmobiletest.utils.hideKeyboard
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.design.longSnackbar
import org.jetbrains.anko.inputMethodManager
import org.jetbrains.anko.startActivity
import javax.inject.Inject

class LoginActivity: AppCompatActivity(){

    @Inject lateinit var viewModelFactory: LoginViewModelFactory
    private lateinit var viewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        BPPApplication.appComponent.inject(this)

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(LoginViewModel::class.java)

        btLogin.setOnClickListener {
            viewModel.setUser(tietEmail.text.toString(), tietPassword.text.toString(), true)
        }

        viewModel.response.observe(this, Observer {
            it?.let {
                clLogin?.hideKeyboard(this.inputMethodManager)

                when (it.status) {
                    Status.SUCCESS -> {
                        val prefs = PreferenceHelper.defaultPrefs(this)
                        prefs[PreferenceHelper.USER_LOGGED] = true

                        startActivity<InvoiceListActivity>()
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