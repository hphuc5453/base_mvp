package com.example.test

import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Build
import android.os.Bundle
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import androidx.appcompat.app.AppCompatDelegate
import com.example.test.core.app.common.AppConfigs

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
        app = this
        //Paper.init(this)
        setupApplication()
        initLogger()
        initLogActivity()
        //initFabricCrashlytics()
        //Mapbox.getInstance(applicationContext, "pk.eyJ1Ijoibm9uMjM1IiwiYSI6ImNpbzI5YzRhbzFheGF1a20za2JzODNndDMifQ.ono5LA8H3OlJUo5jjDCi2Q")
        //Fresco.initialize(this)
        //FirebaseMessaging.getInstance().isAutoInitEnabled = true

//        getTokenFirebase()
    }

//    private fun initFabricCrashlytics() {
//        Fabric.with(this, Crashlytics.Builder().core(
//                CrashlyticsCore.Builder()
//                        .disabled(BuildConfig.DEBUG)
//                        .build())
//                .build())
//    }

    private fun initLogger() {
        Logger.addLogAdapter(object : AndroidLogAdapter() {
            override fun isLoggable(priority: Int, tag: String?): Boolean {
                return BuildConfig.DEBUG
            }
        })
    }

    private fun initLogActivity() {
        if (BuildConfig.DEBUG) {
            this.registerActivityLifecycleCallbacks(object : ActivityLifecycleCallbacks {
                override fun onActivityPaused(activity: Activity?) {
                    activity?.let {
                        Logger.d("onActivityPaused ${activity.javaClass.simpleName}")
                    }
                }

                override fun onActivityResumed(activity: Activity?) {
                    activity?.let {
                        Logger.d("onActivityResumed ${activity.javaClass.simpleName}")
                    }
                }

                override fun onActivityStarted(activity: Activity?) {
                    activity?.let {
                        Logger.d("onActivityStarted ${activity.javaClass.simpleName}")
                    }
                }

                override fun onActivityDestroyed(activity: Activity?) {
                    activity?.let {
                        Logger.d("onActivityDestroyed ${activity.javaClass.simpleName}")
                    }
                }

                override fun onActivitySaveInstanceState(activity: Activity?, outState: Bundle?) {
                    activity?.let {
                        Logger.d("onActivitySaveInstanceState ${activity.javaClass.simpleName}")
                    }
                }

                override fun onActivityStopped(activity: Activity?) {
                    activity?.let {
                        Logger.d("onActivityStopped ${activity.javaClass.simpleName}")
                    }
                }

                override fun onActivityCreated(activity: Activity?, savedInstanceState: Bundle?) {
                    activity?.let {
                        Logger.d("onActivityCreated ${activity.javaClass.simpleName}")
                    }
                }

            })
        }
    }

    private fun setupApplication() {
        AppConfigs.getInstanceApp().setBaseApplication(this)
    }

//    override fun attachBaseContext(base: Context) {
//        super.attachBaseContext(base)
//        MultiDex.install(this) // this is the key code
//    }

    companion object {
        init {
            AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
        }

        @JvmStatic
        var app: App? = null
            private set

        @JvmStatic
        fun isPhoneDevice(): Boolean {
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.CUPCAKE) {
                !AppConfigs.instance.getBaseApplication().resources.getBoolean(R.bool.tablet)
            } else {
                return true
            }
        }
    }

}