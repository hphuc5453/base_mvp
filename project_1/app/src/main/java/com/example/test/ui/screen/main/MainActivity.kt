package com.example.test.ui.screen.test_base

import com.example.test.ui.screen.main.presentation.MainView
import vn.minerva.core.base.presentation.mvp.android.AndroidMvpView
import vn.minerva.core.base.presentation.mvp.android.MvpActivity

class TestBaseActivity : MvpActivity(){
    override fun createAndroidMvpView(): AndroidMvpView {
        return MainView(this, MainView.ViewCreator(this, null))
    }
}
