package com.dev.common.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.dev.common.listeners.ReplaceFragmentListener
import com.agriclinic.common.models.oauth.Oauth
import com.agriclinic.common.ui.auth.AuthViewModel
import com.dev.common.ui.auth.OnBoardLoginFragment
import com.dev.common.utils.viewUtils.ViewUtils
import com.dev.common.R
import com.dev.common.data.FRAGMENTS_NAV_KEYS
import com.dev.common.data.local.PrefrenceManager
import com.dev.common.ui.auth.OnBoardReg1Fragment
import com.dev.common.ui.auth.OnBoardNewPasswordFragment


class AuthActivity : AppCompatActivity(), ReplaceFragmentListener {
    override fun <T>replace(currentFragment: FRAGMENTS_NAV_KEYS, nextFragment: FRAGMENTS_NAV_KEYS, data: T?) {
        val oauth = data as Oauth
        var fragment: Fragment? = null
        var fragmentTag: String? = null
        var addToBackStack: Boolean? = true

        when (nextFragment) {
            FRAGMENTS_NAV_KEYS.ONBOARD_LOGIN -> {
                fragment = OnBoardLoginFragment.newInstance()
                fragmentTag = "OnBoardLoginFragment"
                addToBackStack = true
            }
            FRAGMENTS_NAV_KEYS.ONBOARD_REGISTER_1 -> {

                fragment = OnBoardReg1Fragment.newInstance()
                fragmentTag = "OnBoardRegister1Fragment"
                addToBackStack = true

            }
            FRAGMENTS_NAV_KEYS.ONBOARD_REGISTER_2 -> {

//                fragment = OnBoardReg2Fragment.newInstance()
//                fragmentTag = "OnBoardRegister2Fragment"
//                addToBackStack = false

            }

            FRAGMENTS_NAV_KEYS.ONBOARD_NEW_PASSWORD -> {
                fragment = OnBoardNewPasswordFragment.newInstance()
                fragmentTag = "OnBoardNewPasswordFragment"
                addToBackStack = true

            }
        }
        setOauthObject(oauth)
        if (addToBackStack == true) {
            if (fragment != null) {
                supportFragmentManager.beginTransaction().replace(R.id.container, fragment)
                    .addToBackStack(fragmentTag).commit()
            }
        } else {
            if (fragment != null) {
                supportFragmentManager.beginTransaction().replace(R.id.container, fragment)
                    .commit()
            }
        }

    }

    override fun <T>toActivity(currentFragment: FRAGMENTS_NAV_KEYS, data: T?, finishPrevious: Boolean) {
        when (currentFragment) {
            FRAGMENTS_NAV_KEYS.ONBOARD_LOGIN -> {
                setOauthObject(data as Oauth)

            }

            FRAGMENTS_NAV_KEYS.ONBOARD_NEW_PASSWORD -> {
                setOauthObject(data as Oauth)

            }
        }
        prefrenceManager.setLoginStatus(1)
        val data = Intent()
        data.putExtra("data", getOauthObject())
        setResult(RESULT_OK, data)
        finish()
    }

    var roleId: Int = 1
   private lateinit var viewModel: AuthViewModel

    private lateinit var prefrenceManager: PrefrenceManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.auth_activity)
        prefrenceManager = PrefrenceManager(this)
        viewModel = ViewModelProviders.of(this).get(AuthViewModel::class.java)


        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, OnBoardLoginFragment.newInstance())
                .commitNow()
        }

    }
    override fun onResume() {
        super.onResume()
        ViewUtils().makeFullScreen(this)

    }

    fun getOauthObject(): Oauth {
        return viewModel.getProfile()
    }

    fun setOauthObject(oauth: Oauth) {
        prefrenceManager.saveProfile(oauth)
        viewModel.saveProfile(oauth)


    }


}



