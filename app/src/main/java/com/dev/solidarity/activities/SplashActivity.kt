package com.dev.solidarity.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.dev.common.data.local.PrefrenceManager
import com.dev.common.ui.AuthActivity
import com.dev.common.utils.Constants
import com.dev.common.utils.viewUtils.ViewUtils
import com.dev.solidarity.MainActivity
import com.dev.solidarity.R

class SplashActivity : AppCompatActivity() {
    private var mDelayHandler: Handler? = null
    private val SPLASH_DELAY: Long = 1000
    private val SP_SIGN_IN: Int = 10001

    private val mRunnable: Runnable = Runnable {
        if (!isFinishing) {

            if(PrefrenceManager(this).getLoginStatus()==0) {

                var intent: Intent = Intent(this@SplashActivity, AuthActivity::class.java)
                intent.putExtra(Constants().roleID, Constants().KUDHEIHA)
                startActivityForResult(intent, SP_SIGN_IN)


            }else{
                val intent = Intent(applicationContext, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        ViewUtils().makeFullScreen(this)


    }
    public override fun onDestroy() {

        if (mDelayHandler != null) {
            mDelayHandler!!.removeCallbacks(mRunnable)
        }

        super.onDestroy()
    }

    override fun onResume() {
        super.onResume()
        ViewUtils().makeFullScreen(this)
        mDelayHandler = Handler()
        mDelayHandler!!.postDelayed(mRunnable, SPLASH_DELAY)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==SP_SIGN_IN){
            if(resultCode== Activity.RESULT_OK){
                val intent = Intent(applicationContext, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }else{

            finish()
        }
    }

}
