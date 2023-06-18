package haken.dokemi.andoku.base
import android.app.ProgressDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import haken.dokemi.andoku.R

abstract class BaseActivity<VM: BaseViewModel> : AppCompatActivity() {
    protected lateinit var mViewModel: VM

    private var progressDialog: ProgressDialog? = null

    abstract fun onCreateStart(savedInstanceState: Bundle?)
    abstract fun setupViewComponent()
    abstract fun setupViewModel(): VM
    abstract fun setupView()
    abstract fun setupCommonBinding()
    abstract fun setupBinding()
    abstract fun onCreatedDone()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewModel = setupViewModel()
        onCreateStart(savedInstanceState)
        setupViewComponent()
        setupView()
        setupCommonBinding()
        setupBinding()
        onCreatedDone()
    }

    protected open fun showProgressDialog() {
        if (progressDialog == null) {
            progressDialog = ProgressDialog.show(this, null, null, true)
            progressDialog?.setContentView(R.layout.custom_progress_dialog)
            progressDialog?.isIndeterminate = true
            progressDialog?.setCancelable(false)
            progressDialog?.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
        if (progressDialog != null && !progressDialog!!.isShowing) {
            progressDialog!!.show()
        }
    }

    protected open fun dismissProgressDialog() {
        if (progressDialog != null && progressDialog!!.isShowing) {
            progressDialog!!.dismiss()
        }
    }


    fun replaceFragment(fragment: Fragment, containerViewId: Int) {
        val fragmentTransaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(containerViewId, fragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }
}