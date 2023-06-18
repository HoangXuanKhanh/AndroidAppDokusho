package haken.dokemi.andoku.auth

import AuthViewModel
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import haken.dokemi.andoku.R
import haken.dokemi.andoku.base.BaseActivity
import haken.dokemi.andoku.common.StorageService
import haken.dokemi.andoku.ui.MainHomeActivity

class LoginActivity : BaseActivity<AuthViewModel>() {

    private lateinit var edit_email: EditText
    private lateinit var edit_password: EditText
    private lateinit var text_forgot_password: TextView
    private lateinit var button_login: Button
    private lateinit var button_register: Button

    private val SECOND_ACTIVITY_REQUEST_CODE = 0

    override fun onCreateStart(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_login)
        supportActionBar?.hide()

    }

    override fun setupViewComponent() {

    }

    override fun setupViewModel(): AuthViewModel {
        return ViewModelProvider(this)[AuthViewModel::class.java]
    }

    override fun setupView() {
        edit_email = findViewById(R.id.edit_email)
        edit_password = findViewById(R.id.edit_password)
        text_forgot_password = findViewById(R.id.text_forgot_password)
        button_login = findViewById(R.id.button_login)
        button_register = findViewById(R.id.button_register)


        button_login.setOnClickListener {
            val tmpEmail = edit_email.text.toString().trim()
            val tmpPassword = edit_password.text.toString().trim()

            if (tmpEmail.isNullOrEmpty()) {
                edit_email.error = "Không được để trống"
            } else if (tmpPassword.isNullOrEmpty()) {
                edit_password.error = "Không được để trống"
            } else {
                mViewModel.callLoginAccount(tmpEmail, tmpPassword)
            }

        }

        button_register.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)

            startActivityForResult(intent, SECOND_ACTIVITY_REQUEST_CODE)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == SECOND_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                val returnEmailString: String? = data!!.getStringExtra("EMAIL")
                val returnPasswordString: String? = data.getStringExtra("PASSWORD")

                edit_email.setText(returnEmailString)
                edit_password.setText(returnPasswordString)
            }
        }
    }

    override fun setupCommonBinding() {

    }

    override fun setupBinding() {
        mViewModel.loginResponseData.observe(this) {
            if (it.code_status == 1) {
                Log.d("ddd", it.message)
                StorageService.instance?.token = "Bearer ${it.token}"
                Toast.makeText(this@LoginActivity, it.message, Toast.LENGTH_SHORT).show()
                startActivity(Intent(this@LoginActivity, MainHomeActivity::class.java))
                finish()
            } else {
                Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
            }
        }
        mViewModel.isShowLoading.observe(this) { isShowLoading ->
            if (isShowLoading) showProgressDialog() else dismissProgressDialog()
        }


    }

    override fun onCreatedDone() {

    }

}