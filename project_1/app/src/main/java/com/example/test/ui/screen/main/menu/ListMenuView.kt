package com.example.test.ui.screen.main.menu

import android.graphics.Bitmap
import androidx.recyclerview.widget.RecyclerView
import com.example.test.ui.screen.main.presentation.MainContract
import com.example.test.ui.screen.main.presentation.renderer.MenuLevel1ViewRenderer
import com.github.vivchar.rendererrecyclerviewadapter.ViewModel
import kotlinx.android.synthetic.main.layout_main.view.*
import vn.minerva.core.base.presentation.mvp.android.AndroidMvpView
import vn.minerva.core.base.presentation.mvp.android.MvpActivity
import vn.minerva.core.base.presentation.mvp.android.list.LinearRenderConfigFactory
import vn.minerva.core.base.presentation.mvp.android.list.ListViewMvp
import vn.minerva.sunnyworld.app.presentation.navigater.AndroidScreenNavigator

class ListMenuView (mvpActivity: MvpActivity, recyclerView: RecyclerView): AndroidMvpView(mvpActivity, ViewRootViewCreator(view = recyclerView)), MainContract.MenuView{

    private var lstMenuView: ListViewMvp? = null
    private var menuViewModels: MutableList<ViewModel> = mutableListOf()
    private val renderInputMenu = LinearRenderConfigFactory.Input(
        context = mvpActivity,
        orientation = LinearRenderConfigFactory.Orientation.VERTICAL
    )
    private val renderConfigMenu = LinearRenderConfigFactory(renderInputMenu).create()

    private val listMenuPresenter = ListMenuPresenter(screenNavigator = AndroidScreenNavigator(mvpActivity))
    private val listResource = ListMenuResource()

    override fun initCreateView() {
        lstMenuView = ListViewMvp(mvpActivity, view.rvMenuItem, renderConfigMenu)
        lstMenuView?.addViewRenderer(MenuLevel1ViewRenderer(mvpActivity))
        lstMenuView?.createView()
    }

    override fun renderListMenu(menuViewModels: MutableList<ViewModel>) {
        this.menuViewModels.addAll(menuViewModels)
        lstMenuView?.setItems(this.menuViewModels)
        lstMenuView?.notifyDataChanged()
    }

    override fun showAvatarGenerate(bitmap: Bitmap) {
    }

    override fun updateMenu(listMenu: MutableList<ViewModel>) {
    }

    override fun initData() {
        super.initData()
        listMenuPresenter.loadListMenu()
    }

    override fun startMvpView() {
        listMenuPresenter.attachView(this)
        super.startMvpView()
    }

    override fun stopMvpView() {
        listMenuPresenter.detachView()
        super.stopMvpView()
    }
}