package haken.dokemi.andoku.base

import android.app.ProgressDialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import haken.dokemi.andoku.R

abstract class BaseFragment<A : Context> : Fragment() {

    abstract fun setupLayout(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View
    var hostedActivity: A? = null

    abstract fun setupView()
    abstract fun setupBinding()
    abstract fun onCreatedDone()
    private var progressDialog: ProgressDialog? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
        setupBinding()
        onCreatedDone()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return setupLayout(inflater, container, savedInstanceState)
    }

    protected open fun showProgressDialog() {
        if (progressDialog == null) {
            progressDialog = ProgressDialog.show(activity, null, null, true)
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

}