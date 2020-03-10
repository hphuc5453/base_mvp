package com.example.test.ui.screen.main.menu

import com.example.test.ui.screen.main.domain.ListMenuMapper
import com.example.test.ui.screen.main.presentation.MainContract
import vn.minerva.core.base.domain.provider.AndroidResourceProvider
import vn.minerva.sunnyworld.app.presentation.navigater.AndroidScreenNavigator

class ListMenuPresenter (private val screenNavigator: AndroidScreenNavigator): MainContract.MenuPresenter(){
    override fun generateImageAvatar(title: String) {

    }

    override fun loadListMenu() {
        view?.renderListMenu(ListMenuMapper().map("menu"))
    }
}