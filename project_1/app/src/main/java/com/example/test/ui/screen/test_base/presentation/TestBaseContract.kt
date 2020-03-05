package com.example.test.ui.screen.test_base.presentation

import com.github.vivchar.rendererrecyclerviewadapter.ViewModel
import vn.minerva.core.base.presentation.mvp.base.MvpPresenter
import vn.minerva.core.base.presentation.mvp.base.MvpView

interface TestBaseContract {
    interface View : MvpView {
        fun showLoading()

        fun hideLoading()

        fun showToast(message: String)

        fun showError(message: String)

        fun showData(list : MutableList<ViewModel>)
    }

    abstract class Presenter : MvpPresenter<View>(){
        abstract fun getData()
    }
}