package com.consoli.githubrepository

import android.app.Activity
import android.app.Application
import android.content.*
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.TaskStackBuilder
import androidx.hilt.work.HiltWorkerFactory
import androidx.multidex.MultiDex
import androidx.work.Configuration
import com.app.auth.ui.AuthActivity
import com.app.base.Navigator
import com.app.home.ui.HomeActivity
import com.app.repositories.utils.PreferencesHelper
import com.app.search.ui.SearchActivity
import com.app.splash.ui.SplashActivity
import com.consoli.githubrepository.di.qualifier.Base
import dagger.hilt.android.HiltAndroidApp
import okhttp3.OkHttpClient
import javax.inject.Inject

@HiltAndroidApp class GithubApplication : Application(), Configuration.Provider, Navigator {
    
    companion object {
        private var application: GithubApplication? = null
        fun getInstance(): GithubApplication? {
            return application
        }
    }
    
    @Inject
    lateinit var helper: PreferencesHelper
    
    @Inject
    @Base
    lateinit var okHttpClient: OkHttpClient
    
    @Inject
    lateinit var workerFactory: HiltWorkerFactory
    
    
    override fun onCreate() {
        super.onCreate()
        //AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
//        ValiFi.install(applicationContext)
        application = this
        
    }
    
    override fun getWorkManagerConfiguration() =
            Configuration.Builder().setWorkerFactory(workerFactory).build()
    
    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }
    
    
    //  private val isLoggedInCallback = Observer<Boolean> { setHeaders() }
    override fun startModule(activity: Activity,
                             modules: Navigator.Modules,
                             bundle: Bundle?,
                             startForResult: Int?,
                             fromNotification: Boolean) {
        val intent = Intent()
        when (modules) {
            Navigator.Modules.SPLASH -> intent.setClass(activity, SplashActivity::class.java)
            Navigator.Modules.AUTH -> intent.setClass(activity, AuthActivity::class.java)
            Navigator.Modules.HOME -> intent.setClass(activity, HomeActivity::class.java)
            Navigator.Modules.SEARCH -> intent.setClass(activity, SearchActivity::class.java)
        }
        if (bundle != null) {
            intent.putExtra(Navigator.EXTRAS, bundle)
        }
        try {
            if (startForResult == null) {
                if (fromNotification) {
                    TaskStackBuilder.create(this).addNextIntentWithParentStack(intent)
                            .startActivities()
                } else activity.startActivity(intent)
            } else {
                activity.startActivityForResult(intent, startForResult)
            }
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(this,
                    getString(R.string.this_feature_is_under_development),
                    Toast.LENGTH_SHORT).show()
        }
    }
}