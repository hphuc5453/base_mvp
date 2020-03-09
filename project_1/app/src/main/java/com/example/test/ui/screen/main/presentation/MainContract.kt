package com.example.test.ui.screen.main.presentation

import android.graphics.Bitmap
import com.github.vivchar.rendererrecyclerviewadapter.ViewModel
import vn.minerva.core.base.presentation.mvp.base.MvpPresenter
import vn.minerva.core.base.presentation.mvp.base.MvpView

interface MainContract {
    interface View: MvpView{
        fun showError(msgError: String)
        fun showLoading()
        fun hideLoading()
        fun resetActionDefault()
        fun handlerLogout()
        fun updateSideMenu(listMenu: MutableList<ViewModel>)
    }

    abstract class Presenter : MvpPresenter<View>(){

    }

    interface MenuView : MvpView {
        fun renderListMenu(menuViewModels: MutableList<ViewModel>)
        fun showAvatarGenerate(bitmap: Bitmap)
        fun updateMenu(listMenu: MutableList<ViewModel>)
    }

    abstract class MenuPresenter : MvpPresenter<MenuView>() {
        //abstract fun loadListMenu(userModel : MenuHeaderViewModel)
        abstract fun generateImageAvatar(title: String)
    }
}