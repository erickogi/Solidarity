package com.dev.common.utils.viewUtils

import android.app.Activity
import android.content.Context
import android.text.InputType
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.FragmentActivity
import com.dev.common.listeners.OnInputViewItemClick
import com.dev.common.listeners.OnViewItemClick
import com.dev.common.models.custom.Status
import com.dev.common.utils.AppException
import com.dev.common.R
import com.google.android.material.snackbar.Snackbar
import com.wang.avi.AVLoadingIndicatorView

class ViewUtils {
    val checkIn=1001
    val checkOut=1002
    fun makeFullScreen(activity: Activity) {
        try {
            activity.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
            if (activity.actionBar != null) {
                activity.actionBar?.hide()
            }
        } catch (x: Exception) {
            x.printStackTrace()
        }


    }

    companion object {

        private var cdialog: AlertDialog? = null
        private var edtConsumerKey: EditText? = null
        fun simpleYesNoDialog(
            context: Context,
            title: String,
            message: String,
            simpleDialogModel: SimpleDialogModel,
            onclick: OnViewItemClick
        ) {
            if (cdialog != null) {
                cdialog!!.dismiss()
            }
            val builder = AlertDialog.Builder(context)

            builder.setTitle(title)

            builder.setMessage(message)

            if (simpleDialogModel.positive != null) {

                builder.setPositiveButton(simpleDialogModel.positive) { dialog, which ->
                    cdialog?.dismiss()
                    try {
                        onclick.onPositiveClick()
                        cdialog = null
                    } catch (e: Exception) {
                    }
                }
            }

            if (simpleDialogModel.negative != null) {

                builder.setNegativeButton(simpleDialogModel.negative) { dialog, which ->
                    cdialog?.dismiss()
                    onclick.onNegativeClick()
                    cdialog = null
                }
            }

            if (simpleDialogModel.neutral != null) {
                builder.setNeutralButton(simpleDialogModel.neutral) { dialog, which ->
                    cdialog?.dismiss()
                    onclick.onNeutral()
                    cdialog = null
                }
            }


            val dialog: AlertDialog = builder.create()
            cdialog = dialog


            dialog.show()
        }


        fun simpleYesNoInputDialog(
            context: Context,
            title: String,
            message: String,
            simpleDialogModel: SimpleDialogModel, hint: String,
            onclick: OnInputViewItemClick
        ) {
            val layout = LinearLayout(context)
            layout.orientation = LinearLayout.VERTICAL

            if (cdialog != null) {
                cdialog!!.dismiss()
            }
            val builder = AlertDialog.Builder(context)

            builder.setTitle(title)

            builder.setMessage(message)


            edtConsumerKey = EditText(context)
            edtConsumerKey?.hint = hint
            edtConsumerKey?.setPadding(8, 4, 8, 4)
            edtConsumerKey?.inputType = InputType.TYPE_TEXT_FLAG_AUTO_CORRECT
            layout.addView(edtConsumerKey)
            builder.setView(layout)



            if (simpleDialogModel.positive != null) {

                builder.setPositiveButton(simpleDialogModel.positive) { dialog, which ->
                    cdialog?.dismiss()
                    try {
                        onclick.onPositiveClick(edtConsumerKey?.text.toString())
                        cdialog = null
                    } catch (e: Exception) {
                    }
                }
            }

            if (simpleDialogModel.negative != null) {

                builder.setNegativeButton(simpleDialogModel.negative) { dialog, which ->
                    cdialog?.dismiss()
                    onclick.onNegativeClick()
                    cdialog = null
                }
            }

            if (simpleDialogModel.neutral != null) {
                builder.setNeutralButton(simpleDialogModel.neutral) { dialog, which ->
                    cdialog?.dismiss()
                    onclick.onNeutral()
                    cdialog = null
                }
            }


            val dialog: AlertDialog = builder.create()
            cdialog = dialog


            dialog.show()
        }

        fun hideSoftKeyBoard(context: Context, view: View) {
            try {
                val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(view.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
            } catch (e: Exception) {
                // TODO: handle exception
                e.printStackTrace()
            }

        }

        fun showKeyboard(context: Context, view: View) {
            try {
                val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
            } catch (e: Exception) {
                // TODO: handle exception
                e.printStackTrace()
            }

        }

        fun setStatus(
            activity: FragmentActivity?,
            view: View?,
            status: Status,
            message: String?,
            fullPage: Boolean,
            errorView: ErrorViewTypes, securityException: AppException?
        ) {
            val avi: AVLoadingIndicatorView? = view?.findViewById(R.id.avi)
            if (status == Status.LOADING) {
                avi?.visibility = View.VISIBLE
                avi?.smoothToShow()
                activity?.window?.setFlags(
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
                )
            } else if (status == Status.ERROR || status == Status.SUCCESS) {
                avi?.smoothToHide()
                avi?.visibility = View.GONE
                activity?.window?.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
            }
            if (status == Status.ERROR) {
                if (message != null) {
                    if (errorView == ErrorViewTypes.TOAST) {
                        Toast.makeText(activity, securityException?.errorMessage+"\n"+securityException?.errors?.joinToString { "\'${it}\'" }, Toast.LENGTH_LONG).show()
                    } else if (errorView == ErrorViewTypes.SNACK) {
                        view?.let { Snackbar.make(it, securityException?.errorMessage+"\n"+securityException?.errors?.joinToString { "\'${it}\'" }, Snackbar.LENGTH_LONG).show() }
                    }
                }
                if (fullPage) {
                    val mainView: View? = view?.findViewById(R.id.main_view)
                    val errorView: View? = view?.findViewById(R.id.error_view)
                    val errorText: TextView? = view?.findViewById(R.id.error_text)
                    val errorButton: Button? = view?.findViewById(R.id.error_btn)

                    if (status == Status.ERROR) {
                        if (mainView != null && errorView != null) {
                            mainView.visibility = View.GONE
                            errorView.visibility = View.VISIBLE
                            errorButton?.text = activity?.getString(R.string.retry)
                            if (message != null) {
                                errorText?.text = securityException?.errorMessage+"\n"+securityException?.errors?.joinToString { "\'${it}\'" }
                            } else {
                                errorText?.visibility = View.GONE
                            }
                        }


                    } else {
                        if (mainView != null && errorView != null) {

                            mainView.visibility = View.VISIBLE
                            errorView.visibility = View.GONE
                        }

                    }
                }

            }


        }

    }

    enum class ErrorViewTypes {
        TOAST,
        SNACK,
        NON,
        BOTH,
        ERROR_VIEW
    }
}
