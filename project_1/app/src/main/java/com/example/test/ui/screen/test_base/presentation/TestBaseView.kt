package com.example.test.ui.screen.test_base.presentation

import android.content.Context
import android.view.ViewGroup
import android.widget.Toast
import com.example.test.R
import com.example.test.core.app.view.loading.Loadinger
import com.github.vivchar.rendererrecyclerviewadapter.ViewModel
import kotlinex.mvpactivity.showErrorAlert
import vn.minerva.core.base.presentation.mvp.android.AndroidMvpView
import vn.minerva.core.base.presentation.mvp.android.MvpActivity
import vn.minerva.sunnyworld.app.presentation.navigater.AndroidScreenNavigator

class TestBaseView (mvpActivity: MvpActivity, viewCreator: AndroidMvpView.ViewCreator): AndroidMvpView(mvpActivity, viewCreator),
    TestBaseContract.View {

    class ViewCreator(context: Context, viewGroup: ViewGroup?) :
        AndroidMvpView.LayoutViewCreator(R.layout.loadinger_loading_view, context, viewGroup)

    private val loadingView = Loadinger.create(mvpActivity, mvpActivity.window)
    private val mPresenter = TestBasePresenter(screenNavigator = AndroidScreenNavigator(mvpActivity))
    private val mResource = TestBaseResource()

    override fun initCreateView() {

    }

    override fun showLoading() {
        loadingView.show()
    }

    override fun hideLoading() {
        loadingView.hide()
    }

    override fun showToast(message: String) {
        Toast.makeText(mvpActivity, message, Toast.LENGTH_LONG).show()
    }

    override fun showError(message: String) {
        mvpActivity.showErrorAlert(message)
    }

    override fun showData(list: MutableList<ViewModel>) {

    }

    override fun initData() {
        super.initData()
    }

    override fun startMvpView() {
        mPresenter.attachView(this)
        super.startMvpView()
    }

    override fun stopMvpView() {
        mPresenter.detachView()
        super.stopMvpView()
    }
}