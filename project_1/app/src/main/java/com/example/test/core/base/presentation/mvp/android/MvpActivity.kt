package vn.minerva.core.base.presentation.mvp.android

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Rect
import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import android.view.MotionEvent
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.CallSuper
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AppCompatActivity
import com.example.test.R
import com.example.test.core.base.manager.LocaleManager
import com.orhanobut.logger.Logger
import vn.minerva.core.base.domain.listener.OnActionNotify
import vn.minerva.core.base.domain.manager.AndroidResourceManager
import vn.minerva.core.base.presentation.mvp.android.lifecycle.LifeCycleAndroidDispatcher
import vn.minerva.core.base.presentation.mvp.android.lifecycle.LifeCycleAndroidMvpView
import vn.minerva.core.base.presentation.mvp.android.lifecycle.PermissionsResult
import vn.minerva.core.base.presentation.mvp.android.lifecycle.ViewResult
import vn.minerva.core.base.presentation.mvp.base.lifecycle.LifeCycleDispatcherSetter

abstract class MvpActivity : AppCompatActivity(),
    LifeCycleDispatcherSetter<LifeCycleAndroidMvpView> {
    private val lifeCycleDispatcher = LifeCycleAndroidDispatcher()
    private var timeBackPressed: Long = 0
    private val timeDelayWhenClickBack = 2000
    //    private val resultSyncOrderOfflineReceiver = ResultSyncOrderOfflineReceiver()
    private lateinit var screenReceiver: ScreenReceiver

    override fun addLifeCycle(lifeCycle: LifeCycleAndroidMvpView) {
        lifeCycleDispatcher.addLifeCycle(lifeCycle)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initWindow()
        injectIntent()
        initScreenOnEvent()
        initCreateView()
        initMvpView()
    }

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(LocaleManager.setLocale(newBase))
    }

    private fun initWindow() {
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        val drawableId = getBackgroundScreen()
        if(drawableId!=0) {
            setBackgroundData(drawableId)
        }
    }

    private fun setBackgroundData(@DrawableRes id: Int) {
        val resource = AndroidResourceManager()
        val background = resource.getDrawable(id)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = resource.getColor(android.R.color.transparent)
            window.navigationBarColor = resource.getColor(android.R.color.transparent)
        }
        window.setBackgroundDrawable(background)
    }

    @DrawableRes
    open fun getBackgroundScreen(): Int {
        return 0
    }

    override fun onDestroy() {
        removeScreenOnEvent()
        super.onDestroy()
    }

    private fun initScreenOnEvent() {
        val filter = IntentFilter(Intent.ACTION_SCREEN_ON)
        filter.addAction(Intent.ACTION_SCREEN_OFF)
        screenReceiver = ScreenReceiver(object : OnActionNotify {
            override fun onActionNotify() {
                onScreenOn()
            }
        }, object : OnActionNotify {
            override fun onActionNotify() {
                onScreenOff()
            }
        })
        registerReceiver(screenReceiver, filter)
    }

    private fun removeScreenOnEvent() {
        unregisterReceiver(screenReceiver)
    }

    protected open fun onScreenOn() {
        Logger.d("onScreenOn")
    }


    protected open fun onScreenOff() {
        Logger.d("onScreenOff")
    }


    private fun initBroadcastReceiverAfterSyncOrderOffline() {
//        val intentFilter = IntentFilter()
//        intentFilter.addAction(ResultSyncOrderOfflineReceiver.EXTRA_ACTION_GET_RESULT_SYNC_ORDER)
//        registerReceiver(resultSyncOrderOfflineReceiver, intentFilter)
    }

    abstract fun createAndroidMvpView(): AndroidMvpView

    @CallSuper
    protected open fun initCreateView() {
        val androidMvpView = createAndroidMvpView()
        setupContentView(androidMvpView)
    }

    private fun setupContentView(setupMetaView: AndroidMvpView) {
        setupMetaView.let {
            val view = it.createView()
            setContentView(view)
            addLifeCycle(it)
        }
    }

    private fun initMvpView() {
        lifeCycleDispatcher.initMvpView()
    }

    override fun onResume() {
        super.onResume()
        lifeCycleDispatcher.startMvpView()
        initBroadcastReceiverAfterSyncOrderOffline()
    }


    override fun onPause() {
        super.onPause()
        lifeCycleDispatcher.stopMvpView()
//        unregisterReceiver(resultSyncOrderOfflineReceiver)
    }

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        lifeCycleDispatcher.dispatchTouchEvent(ev)
        if (ev.action == MotionEvent.ACTION_DOWN) {
            val v = currentFocus
            if (v is EditText) {
                val outRect = Rect()
                v.getGlobalVisibleRect(outRect)
                if (!outRect.contains(ev.rawX.toInt(), ev.rawY.toInt())) {
                    v.clearFocus()
                    val imm =
                        getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0)
                }
            }
        }
        return super.dispatchTouchEvent(ev)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        lifeCycleDispatcher.onViewResult(ViewResult(requestCode, resultCode, data))
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        lifeCycleDispatcher.onRequestPermissionsResult(
            PermissionsResult(
                requestCode,
                permissions,
                grantResults
            )
        )
    }

    private fun injectIntent() {
//        ActivityIntentInjector().injectIntent(this)
    }

    inline fun <reified T : Parcelable> getInjectIntentExtra(): T? {
        return if (intent != null) {
            intent?.extras?.getParcelable(T::class.java.simpleName)
        } else {
            null
        }
    }

    override fun onBackPressed() {
        if (!handleBackPressed()) {
            super.onBackPressed()
        }

    }

    private fun handleBackPressed(): Boolean {
        var isHandleBack = lifeCycleDispatcher.isHandleBackPressed()
        if (!isHandleBack) {
            if (this.isTaskRoot) {
                if (timeBackPressed + timeDelayWhenClickBack > System.currentTimeMillis()) {
                    isHandleBack = false
                } else {
                    isHandleBack = true
                    Toast.makeText(this, R.string.check_double_click_exit_app, Toast.LENGTH_SHORT)
                        .show()
                }
                timeBackPressed = System.currentTimeMillis()
            }
        }
        return isHandleBack
    }

    class ScreenReceiver(
        private val onScreenOn: OnActionNotify,
        private val onScreenOff: OnActionNotify
    ) :
        BroadcastReceiver() {

        override fun onReceive(context: Context, intent: Intent) {
            if (intent.action == Intent.ACTION_SCREEN_OFF) {
                onScreenOff.onActionNotify()
            } else if (intent.action == Intent.ACTION_SCREEN_ON) {
                onScreenOn.onActionNotify()
            }
        }
    }
}