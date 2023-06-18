package haken.dokemi.andoku.auth

import AuthViewModel
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import haken.dokemi.andoku.base.BaseActivity
import com.google.android.material.textfield.TextInputEditText
import haken.dokemi.andoku.R

class RegisterActivity : BaseActivity<AuthViewModel>() {

    lateinit var text_view_terms: TextView
    lateinit var button_account: Button
    lateinit var buttonRegister: Button

    lateinit var edit_name: TextInputEditText
    lateinit var edit_email: TextInputEditText
    lateinit var edit_password: TextInputEditText
    lateinit var edit_confirm_password: TextInputEditText

    override fun onCreateStart(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_register)
        supportActionBar?.hide()
    }

    override fun setupViewComponent() {

    }

    override fun setupViewModel(): AuthViewModel {
        return ViewModelProvider(this)[AuthViewModel::class.java]
    }

    override fun setupView() {
        button_account = findViewById(R.id.button_accounted)
        text_view_terms = findViewById(R.id.text_view_terms)
        edit_name = findViewById(R.id.text_input_edit_text_name)
        edit_email = findViewById(R.id.text_input_edit_text_email)
        edit_password = findViewById(R.id.text_input_edit_text_password)
        edit_confirm_password = findViewById(R.id.text_input_edit_text_confirm_password)
        buttonRegister = findViewById(R.id.button_register)

        text_view_terms.paint.isUnderlineText = true

        button_account.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        //register
        buttonRegister.setOnClickListener {
            val tmpName = edit_name.text.toString().trim()
            val tmpEmail = edit_email.text.toString().trim()
            val tmpPassword = edit_password.text.toString().trim()
            val tmpConfirmPassword = edit_confirm_password.text.toString().trim()

            if (tmpName.isNullOrEmpty()) {
                edit_name.error = "Không được để trống"
                edit_name.requestFocus()
            } else if (tmpEmail.isNullOrEmpty()) {
                edit_email.error = "Không được để trống"
                edit_email.requestFocus()
            } else if (tmpPassword.isNullOrEmpty()) {
                edit_password.error = "Không được để trống"
                edit_password.requestFocus()
            } else if (tmpConfirmPassword.isNullOrEmpty()) {
                edit_confirm_password.error = "Không được để trống"
                edit_confirm_password.requestFocus()
            } else if (tmpPassword != tmpConfirmPassword) {
                edit_password.isEnabled = true
                edit_password.error = "Mật khẩu không khớp"
                edit_confirm_password.isEnabled = true
                edit_confirm_password.error = "Mật khẩu không khớp"
            } else {
                mViewModel.callRegister(tmpName, tmpEmail, tmpPassword)

            }

        }

    }

    override fun setupCommonBinding() {

    }

    override fun setupBinding() {
        mViewModel.registerResponseData.observe(this) {
            if (it.code_status == 1) {
                val intent = Intent()
                val tmpEmail = edit_email.text.toString().trim()
                val tmpPassword = edit_password.text.toString().trim()
                intent.putExtra("EMAIL", tmpEmail)
                intent.putExtra("PASSWORD", tmpPassword)
                setResult(RESULT_OK, intent)

                val mDialog = Dialog(this)
                mDialog.setCancelable(true)
                mDialog.setContentView(R.layout.custom_success_layout)
                val mWindow = mDialog.window
                mWindow!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                mDialog.show()

                finish()
            } else {

                val mDialog = Dialog(this)
                mDialog.setCancelable(true)
                mDialog.setContentView(R.layout.custom_email_register)
                val mWindow = mDialog.window
                mWindow!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                mDialog.show()
            }
        }
    }

    override fun onCreatedDone() {

    }
}