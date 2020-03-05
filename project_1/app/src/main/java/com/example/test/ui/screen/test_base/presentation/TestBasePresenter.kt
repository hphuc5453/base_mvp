package com.example.test.ui.screen.test_base.presentation

import com.example.test.ui.screen.test_base.domain.TestBaseMapper
import vn.minerva.sunnyworld.app.presentation.navigater.AndroidScreenNavigator

class TestBasePresenter (private val  screenNavigator: AndroidScreenNavigator): TestBaseContract.Presenter(){
    override fun getData() {
        view?.showData(TestBaseMapper().map(""))
    }

}