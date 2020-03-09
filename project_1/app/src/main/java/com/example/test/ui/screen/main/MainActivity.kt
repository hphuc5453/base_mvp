package com.example.test.ui.screen.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.test.ui.screen.main.presentation.MainView
import vn.minerva.core.base.presentation.mvp.android.AndroidMvpView
import vn.minerva.core.base.presentation.mvp.android.MvpActivity

class MainActivity : MvpActivity(){
    override fun createAndroidMvpView(): AndroidMvpView {
        return MainView(this, MainView.ViewCreator(this, null))
    }

}
