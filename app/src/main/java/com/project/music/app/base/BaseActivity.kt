package com.project.music.app.base


import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import com.project.music.app.R
import com.project.music.app.arch.api.ApiState
import com.project.music.app.databinding.BaseLayoutBinding
import com.project.music.app.utils.snack
import kotlinx.android.synthetic.main.dialog_progress.*

abstract class BaseActivity : AppCompatActivity() {
    protected abstract fun initViewBinding()
    protected abstract fun subscribeCallbackFromSuccessStatus()

    private lateinit var baseActivity: BaseActivity
    lateinit var context: Context
    private var mProgressDialog: Dialog? = null
    private lateinit var binding: BaseLayoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = BaseLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)
        baseActivity = this@BaseActivity
        context = this
        initViewBinding() // abstract function should be override in child classes
        subscribeCallbackFromSuccessStatus() // abstract function should be override in child classes

        mProgressDialog = Dialog(this)
        mProgressDialog?.setCancelable(false)
        mProgressDialog?.setContentView(R.layout.dialog_progress)
        mProgressDialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))


//        val tvMsg = mProgressDialog?.findViewById<TextView>(R.id.tv_msg)
//        tvMsg?.text = "loading"

        mProgressDialog?.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )


    }

    fun showProgressDialog() {
        if (this.isFinishing.not() && mProgressDialog != null) {
            mProgressDialog?.show()
        }
    }

    fun hideProgressDialog() {
        if (this.isFinishing.not() && mProgressDialog != null) {
            mProgressDialog?.dismiss()
        }
    }

    override fun onResume() {
        super.onResume()
//        baseActivity?.window?.statusBarColor = Color.GRAY
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            baseActivity?.window?.statusBarColor = Color.parseColor("#6C297B")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            baseActivity?.window?.statusBarColor = Color.TRANSPARENT
        }
    }


    //-----------------------------------------------
    //         CHECKING API CALLING STATUS
    //-----------------------------------------------

    private var response = MutableLiveData<Any>()

    fun setResponse(mRes: Any) {
        response.value = mRes
    }

    fun getResponse(): MutableLiveData<Any> {
        return response
    }

    fun cosumeApiResponse(response: ApiState, showLoader: Boolean = true) {
        when (response) {
            is ApiState.LOADING -> {
                if (showLoader) showProgressDialog()
            }
            is ApiState.ERROR -> {
                hideProgressDialog()
                onApiErrorShowMessage(response.msg.localizedMessage)
            }
            is ApiState.SUCCESS -> {
                hideProgressDialog()
                Log.e("SUCCESS -> ", " ${response.data}")
                response.data?.let {
                    try {
                        setResponse(it)
                    } catch (e: Exception) {
                        onApiErrorShowMessage(e.localizedMessage)
                    }


                }
            }
            is ApiState.EMPTY -> {
                Log.e("EMPTY -> ", " ${response}")
            }
        }
    }

    fun onApiErrorShowMessage(message: String?) {
        try {
            binding.mainView.snack(message ?: "error")
        } catch (e: Exception) {
        }

    }
}