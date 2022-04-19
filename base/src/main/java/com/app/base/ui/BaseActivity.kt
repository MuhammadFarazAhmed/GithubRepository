package com.app.base.ui

import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowInsets
import android.view.WindowInsetsController
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NavUtils
import androidx.core.app.TaskStackBuilder
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.app.base.Navigator
import com.app.base.R
import com.app.base.callback.ViewImageCallback
import com.app.base.utils.ProgressDialogCallback


abstract class BaseActivity : AppCompatActivity(), ProgressDialogCallback, ViewImageCallback {

    lateinit var navigator: Navigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        navigator = application as Navigator
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        }

        /* supportFragmentManager.addOnBackStackChangedListener {
             val f = supportFragmentManager.fragments.lastOrNull()
             f?.let {
                 if (f is DarkBaseFragment) {
                     doNormalStatusBar()
                 } else {
                     doLightStatusbar()
                 }
             }
         }*/
    }

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(newBase)
        val newOverride = Configuration(newBase?.resources?.configuration)
        newOverride.fontScale = 1.0f
        applyOverrideConfiguration(newOverride)
    }

    /*To be called in onAttachedToWindow*/
    protected fun hideSystemUI() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.setDecorFitsSystemWindows(false)
            window.insetsController?.let {
                it.hide(WindowInsets.Type.statusBars() or WindowInsets.Type.navigationBars())
                it.systemBarsBehavior = WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            }
        } else {
            @Suppress("DEPRECATION")
            window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION)
        }
    }

    protected fun openVideo(uri: Uri, isUrl: Boolean) {
        if (isUrl.not() && Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            // val videoFile = File(path)
            //val uri = FileProvider.getUriForFile(this, "$packageName.provider", videoFile)
            val intent = Intent(Intent.ACTION_VIEW)
            intent.setDataAndType(uri, "video/*")
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            startActivity(intent)
        } else {
            var intent = Intent(Intent.ACTION_VIEW)
            intent.setDataAndType(uri, "video/*")
            intent = Intent.createChooser(intent, "Open Video")
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            if (intent.resolveActivity(packageManager) != null) {
                startActivity(intent)
            } else {
                toast(getString(R.string.unable_to_open_file))
            }
        }
    }

    protected fun openPDF(uri: Uri, isUrl: Boolean) {
        if (isUrl) {
            try {
                startActivity(Intent(Intent.ACTION_VIEW, uri))
            } catch (e: Exception) {
                toast(getString(R.string.unable_to_open_file))
            }
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                //  val file = File(path)
                //val uri = FileProvider.getUriForFile(this, "$packageName.provider", file)
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = uri
                intent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
                startActivity(intent)
            } else {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.setDataAndType(uri, "application/pdf")
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                if (intent.resolveActivity(packageManager) != null) {
                    val chooserIntent = Intent.createChooser(intent, "Open File")
                    startActivity(chooserIntent)
                } else {
                    toast(getString(R.string.unable_to_open_file))
                }
            }
        }
    }

    protected fun showProgress(title: String = "", message: String = "") {
        ProgressDialogFragment.newInstance(title, message)
            .show(supportFragmentManager, "showProgress")
    }

    protected fun hideProgress() {
        supportFragmentManager.findFragmentByTag("showProgress")?.let {
            (it as DialogFragment).dismiss()
        }
    }

    fun onLoading(): (Boolean) -> Unit {
        return {
            if (it) {
                showProgress()
            } else {
                hideProgress()
            }
        }
    }

    override fun onProgressDialogCancelled() {

    }

    protected fun addFragment(
        @IdRes resId: Int, fragment: Fragment,
        addToBackStack: Boolean = false,
        animationType: AnimationType = AnimationType.NONE
    ) {
        commitFragment(
            resId,
            fragment,
            addToBackStack,
            animationType,
            supportFragmentManager,
            false
        )
    }

    protected fun replaceFragment(
        @IdRes resId: Int, fragment: Fragment,
        addToBackStack: Boolean = false,
        animationType: AnimationType = AnimationType.NONE
    ) {
        commitFragment(resId, fragment, addToBackStack, animationType, supportFragmentManager, true)
    }

    protected fun commitFragment(
        @IdRes resId: Int, fragment: Fragment,
        addToBackStack: Boolean,
        animationType: AnimationType,
        fragmentManager: FragmentManager,
        replaceFragment: Boolean
    ) {
        try {
            commit(
                getFragmentTransaction(
                    resId,
                    fragment,
                    addToBackStack,
                    animationType,
                    fragmentManager,
                    replaceFragment
                )
            )
        } catch (e: IllegalStateException) {
            Log.e("IllegalStateException", "Commit fragment: $e")
            e.message?.contains("onSaveInstanceState")?.let {
                commitAllowStateLoss(
                    getFragmentTransaction(
                        resId,
                        fragment,
                        addToBackStack,
                        animationType,
                        fragmentManager,
                        replaceFragment
                    )
                )
            }
        }

    }

    private fun getFragmentTransaction(
        @IdRes resId: Int, fragment: Fragment,
        addToBackStack: Boolean,
        animationType: AnimationType,
        fragmentManager: FragmentManager,
        replaceFragment: Boolean
    ): FragmentTransaction {
        val fragmentTransaction =
            createFragmentTransaction(
                resId,
                fragment,
                animationType,
                fragmentManager,
                replaceFragment
            )
        if (addToBackStack) {
            fragmentTransaction.addToBackStack(fragment.javaClass.simpleName)
        }
        return fragmentTransaction
    }

    protected fun showDialog(fragment: DialogFragment) {
        try {
            commit(createDialogFragmentTransaction(fragment))
        } catch (e: IllegalStateException) {
            Log.e("IllegalStateException", "Commit fragment: $e")
            e.message?.contains("onSaveInstanceState")?.let {
                commitAllowStateLoss(createDialogFragmentTransaction(fragment))
            }
        }

    }

    private fun createDialogFragmentTransaction(fragment: DialogFragment): FragmentTransaction {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(fragment, fragment.javaClass.simpleName)
        return transaction
    }

    @Throws(IllegalStateException::class)
    protected fun commit(fragmentTransaction: FragmentTransaction) {
        fragmentTransaction.commit()
    }

    @Throws(IllegalStateException::class)
    protected fun commitAllowStateLoss(fragmentTransaction: FragmentTransaction) {
        fragmentTransaction.commit()
    }


    protected fun createFragmentTransaction(
        @IdRes resId: Int, fragment: Fragment,
        animationType: AnimationType,
        fragmentManager: FragmentManager,
        replaceFragment: Boolean
    ): FragmentTransaction {
        val fragmentTransaction = fragmentManager.beginTransaction()

        setAnimation(animationType, fragmentTransaction)
        if (replaceFragment) {
            fragmentTransaction.replace(resId, fragment, fragment.javaClass.simpleName)
        } else {
            fragmentTransaction.add(resId, fragment, fragment.javaClass.simpleName)
        }
        return fragmentTransaction
    }


    private fun setAnimation(
        animationType: AnimationType,
        fragmentTransaction: FragmentTransaction
    ) {

        when (animationType) {
            AnimationType.NONE -> {
            }
            AnimationType.FADE -> fragmentTransaction.setCustomAnimations(
                android.R.anim.fade_in,
                android.R.anim.fade_out, android.R.anim.fade_in, android.R.anim.fade_out
            )
            AnimationType.SLIDE_LEFT -> {
            }
            AnimationType.SLIDE_RIGHT -> fragmentTransaction.setCustomAnimations(
                R.anim.slide_in_right,
                R.anim.slide_out_left,
                R.anim.slide_in_left,
                R.anim.slide_out_right
            )
            AnimationType.SLIDE_TOP -> fragmentTransaction.setCustomAnimations(
                R.anim.slide_in_up,
                R.anim.slide_out_down,
                R.anim.slide_in_up,
                R.anim.slide_out_down
            )
        }

    }

    enum class AnimationType {
        NONE, SLIDE_RIGHT, SLIDE_LEFT, SLIDE_TOP, FADE
    }

    protected fun findFragment(id: Int) = supportFragmentManager.findFragmentById(id)
    protected fun findFragment(tag: String?) = supportFragmentManager.findFragmentByTag(tag)
    protected fun popNow() {
        try {
            supportFragmentManager.popBackStackImmediate()
        } catch (e: Exception) {
            Log.v("FragmentManager", e.toString())
            /*try {
                supportFragmentManager.executePendingTransactions()
            } catch (e: Exception) {
                Log.v("FragmentManager",e.toString())
            }*/
        }
    }

    fun toast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    protected fun closeKeyboard(): Boolean {
        var isKeyboardOpen = false
        val view = this.currentFocus
        if (view != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            isKeyboardOpen = imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
        return isKeyboardOpen
    }

    override fun onDestroy() {
        hideProgress()
        super.onDestroy()
    }

    private val fromNotification by lazy {
        val notificationType =
            intent.getBundleExtra(Navigator.EXTRAS)?.getInt(Navigator.NOTIFICATION_TYPE, -1)
        val resourceId =
            intent.getBundleExtra(Navigator.EXTRAS)?.getInt(Navigator.RESOURCE_ID, -1)
        notificationType != -1 && resourceId != -1
    }

    override fun onBackPressed() {
        if (fromNotification) {
            val intent = NavUtils.getParentActivityIntent(this)
            if (intent != null) {
                if (NavUtils.shouldUpRecreateTask(this, intent)) {
                    TaskStackBuilder.create(this).addNextIntentWithParentStack(intent)
                        .startActivities()
                }
            }
        }
        super.onBackPressed()
    }
}