package com.example.test.ui.screen.test_base

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.test.ui.screen.test_base.presentation.TestBaseView
import vn.minerva.core.base.presentation.mvp.android.AndroidMvpView
import vn.minerva.core.base.presentation.mvp.android.MvpActivity

class TestBaseActivity : MvpActivity(){
    override fun createAndroidMvpView(): AndroidMvpView {
        return TestBaseView(
            this,
            TestBaseView.ViewCreator(this, null)
        )
    }

}
