package com.app.splash.ui

import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import com.app.base.Navigator
import com.app.base.Navigator.Companion.ARG_GOTO
import com.app.base.Navigator.Companion.FROM_DEEP_LINK
import com.app.base.Navigator.Companion.NOTIFICATION_TYPE
import com.app.base.Navigator.Companion.RESOURCE_ID
import com.app.base.ui.BaseActivity
import com.app.splash.callback.SplashCallback
import dagger.hilt.android.AndroidEntryPoint
import com.app.splash.R
import com.app.splash.databinding.ActivitySplashBinding

@AndroidEntryPoint
class SplashActivity : BaseActivity(), SplashCallback {

    override fun onBackPressed(tag: String?) {
        onBackPressed()
    }

    private lateinit var binding: ActivitySplashBinding

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        hideSystemUI()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       /* requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )*/

        binding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_splash
        )
        val data: Uri? = intent.data
        if (data != null) {
            val pathSegments: List<String> = data.pathSegments

            val d = pathSegments.indexOf("detail")
            val p = pathSegments.indexOf("profile")
            if (d != -1) {
                val jobId = if (d + 1 < pathSegments.size) pathSegments[d + 1] else null
                jobId?.let {
                    navigator.startModule(
                        this,
                        Navigator.Modules.HOME,
                        bundleOf(
                            FROM_DEEP_LINK to true,
                            NOTIFICATION_TYPE to 1,
                            RESOURCE_ID to jobId.toInt()
                        ),
                        null,
                        true
                    )
                    finish()
                }
            } else if (p != -1) {
                val profileId = if (p + 1 < pathSegments.size) pathSegments[p + 1] else null
                profileId?.let {
                    navigator.startModule(
                        this,
                        Navigator.Modules.PROFILE_SETTINGS,
                        bundleOf(
                            FROM_DEEP_LINK to true,
                            ARG_GOTO to 3,
                            // Navigator.PROFILE_USER to UserShort(profileId.toInt(), "", "", null)
                        ),
                        null,
                        true
                    )
                    finish()
                }
            }
            Log.v("aydo.app", "path $data")
            Log.v("aydo.app", "params $pathSegments")
        }

        addFragment(R.id.flSplash, SplashFragment.newInstance())
    }


    override fun onStartActivity(module: Navigator.Modules, goto: Int?) {
        val bundle = intent.extras ?: Bundle()
        bundle.putBoolean(Navigator.IS_FROM_BACKGROUND, true)
        if (goto != null && module == Navigator.Modules.AUTH) {
            bundle.putInt(ARG_GOTO, goto)
            navigator.startModule(this, module, bundle)
        } else if (goto != null && module == Navigator.Modules.ONBOARDING) {
            bundle.putInt(Navigator.ONBOARDING_ARG_AUTH_GOTO, goto)
            navigator.startModule(this, module, bundle)
        } else {
            navigator.startModule(this, module, bundle)
        }
        finish()
    }

}
