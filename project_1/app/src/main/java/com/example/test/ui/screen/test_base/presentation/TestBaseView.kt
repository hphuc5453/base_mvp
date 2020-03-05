package com.example.test.ui.screen.test_base.presentation

import android.content.Context
import android.view.ViewGroup
import android.widget.Toast
import com.example.test.R
import com.example.test.core.app.view.loading.Loadinger
import com.example.test.ui.screen.test_base.presentation.renderer.TestBaseRenderer
import com.github.vivchar.rendererrecyclerviewadapter.ViewModel
import kotlinex.mvpactivity.showErrorAlert
import kotlinx.android.synthetic.main.layout_test_base.view.*
import vn.minerva.core.base.presentation.mvp.android.AndroidMvpView
import vn.minerva.core.base.presentation.mvp.android.MvpActivity
import vn.minerva.core.base.presentation.mvp.android.list.LinearRenderConfigFactory
import vn.minerva.core.base.presentation.mvp.android.list.ListViewMvp
import vn.minerva.sunnyworld.app.presentation.navigater.AndroidScreenNavigator

class TestBaseView (mvpActivity: MvpActivity, viewCreator: AndroidMvpView.ViewCreator): AndroidMvpView(mvpActivity, viewCreator),
    TestBaseContract.View {

    class ViewCreator(context: Context, viewGroup: ViewGroup?) :
        AndroidMvpView.LayoutViewCreator(R.layout.layout_test_base, context, viewGroup)

    private val loadingView = Loadinger.create(mvpActivity, mvpActivity.window)
    private val mPresenter = TestBasePresenter(screenNavigator = AndroidScreenNavigator(mvpActivity))
    private val mResource = TestBaseResource()

    private val listProject = mutableListOf<ViewModel>()
    private var rvListProject: ListViewMvp? = null

    private val renderInputMenu = LinearRenderConfigFactory.Input(
        context = mvpActivity,
        orientation = LinearRenderConfigFactory.Orientation.VERTICAL
    )
    private val renderConfigMenu = LinearRenderConfigFactory(renderInputMenu).create()

    override fun initCreateView() {
        rvListProject = ListViewMvp(mvpActivity, view.rvTestBase, renderConfigMenu)
        rvListProject?.addViewRenderer(TestBaseRenderer(mvpActivity))
        rvListProject?.createView()
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
        listProject.addAll(list)
        rvListProject?.setItems(this.listProject)
        rvListProject?.notifyDataChanged()
    }

    override fun initData() {
        mPresenter.getData()
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