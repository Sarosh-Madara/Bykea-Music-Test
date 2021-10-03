package com.project.music.app.base

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.project.music.app.R
import com.project.music.app.arch.api.ApiState
import com.project.music.app.utils.snack
import kotlinx.android.synthetic.main.base_layout.*

open class BaseFragment : Fragment() {
    private var mProgressDialog: Dialog? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.base_layout, container, false)
        initDialog()
        return view
    }

    fun initDialog() {
        activity?.let { activity ->
            mProgressDialog = Dialog(activity)
            mProgressDialog?.setCancelable(false)
            mProgressDialog?.setContentView(R.layout.dialog_progress)
            mProgressDialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            //val tvMsg = mProgressDialog?.findViewById<TextView>(R.id.tv_msg)
            //?.text = "loading"

            mProgressDialog?.window?.setLayout(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT
            )
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    private fun showProgressDialog() {
        if (mProgressDialog == null) {
            initDialog()
        }
        mProgressDialog?.show()
    }

    private fun hideProgressDialog() {
        if (mProgressDialog != null) {
            mProgressDialog?.dismiss()
        }
    }

    //-----------------------------------------------
    //         CHECKING API CALLING STATUS
    //------------------------------------------------
    private var response = MutableLiveData<Any>()

    private fun setResponse(data: Any) {
        response.value = data
    }

    fun getResponse(): LiveData<Any> {
        return response
    }

    fun consumeApiResponse(response: ApiState, showLoader: Boolean = true) {
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
                Log.e("EMPTY -> ", " $response")
            }
        }
    }

    private fun onApiErrorShowMessage(message: String?) {
        try {
            main_view?.snack(message ?: "error")
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}