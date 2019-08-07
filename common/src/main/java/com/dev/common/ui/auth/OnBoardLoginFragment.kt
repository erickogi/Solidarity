package com.dev.common.ui.auth


import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.dev.common.data.FRAGMENTS_NAV_KEYS
import com.dev.common.listeners.ReplaceFragmentListener
import com.dev.common.models.custom.Status
import com.agriclinic.common.models.oauth.Oauth
import com.agriclinic.common.ui.auth.AuthViewModel
import com.dev.common.utils.Validator

import com.dev.common.utils.viewUtils.ViewUtils
import com.dev.common.R
import com.dev.common.utils.textWatchers.PhoneTextWatcher
import com.dev.common.utils.textWatchers.PinTextWatcher
import kotlinx.android.synthetic.main.onboard_login.*


class OnBoardLoginFragment : Fragment(), View.OnClickListener {
    override fun onClick(p0: View?) {
        if (p0 == btn_login) {
            if (Validator.isValidPin(edt_password)&&Validator.isValidPhone(edt_phone)) {
                verifyPassword(p0)

            }

        }
        if (p0 == register) {
            verifyPassword(p0)

        }
    }

    private fun verifyPassword(p0: View?) {
        if (p0 == btn_login) {
            viewModel.signIn(getOauth())
        } else if (p0 == register) {

            replaceFragmentListener.replace(FRAGMENTS_NAV_KEYS.ONBOARD_LOGIN,FRAGMENTS_NAV_KEYS.ONBOARD_REGISTER_1,getOauth())

        }

    }

    private fun getOauth(): Oauth {
        val oauth = viewModel.getProfile()
        oauth.profile?.password = Validator.getText(edt_password)
        oauth.profile?.phone_number = Validator.getPhoneNumber(edt_phone)
        oauth.profile?.username = Validator.getPhoneNumber(edt_phone)

        return oauth

    }


    private lateinit var replaceFragmentListener: ReplaceFragmentListener

    companion object {
        fun newInstance() = OnBoardLoginFragment()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        replaceFragmentListener = activity as ReplaceFragmentListener
    }

    private lateinit var viewModel: AuthViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.onboard_login, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(AuthViewModel::class.java)
        initWidgets()
        viewModel.observeSignIn().observe(this, Observer {
            ViewUtils.setStatus(activity,view,it.status,it.message,false, ViewUtils.ErrorViewTypes.TOAST,it.exception)
            if(it.status== Status.SUCCESS){
                replaceFragmentListener.toActivity(FRAGMENTS_NAV_KEYS.ONBOARD_LOGIN,it.data,true)

            }
        })

    }

    private fun initWidgets() {


        edt_password.requestFocus()
        edt_phone.requestFocus()
        register.setOnClickListener(this)
        btn_login.setOnClickListener(this)
        edt_password.addTextChangedListener(PinTextWatcher(edt_password))
        edt_phone.addTextChangedListener(PhoneTextWatcher(edt_phone,txt_ke))


    }





}
