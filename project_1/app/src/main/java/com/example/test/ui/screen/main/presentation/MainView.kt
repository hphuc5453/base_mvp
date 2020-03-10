package com.example.test.ui.screen.main.presentation

import android.content.Context
import android.view.ViewGroup
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import com.example.test.R
import com.example.test.core.app.view.loading.Loadinger
import com.example.test.ui.screen.main.data.ActionMenu
import com.example.test.ui.screen.main.menu.ListMenuView
import com.github.vivchar.rendererrecyclerviewadapter.ViewModel
import kotlinex.view.gone
import kotlinx.android.synthetic.main.layout_main.view.*
import kotlinx.android.synthetic.main.view_layout_toolbar.view.*
import vn.minerva.core.base.domain.listener.OnActionNotify
import vn.minerva.core.base.presentation.mvp.android.AndroidMvpView
import vn.minerva.core.base.presentation.mvp.android.MvpActivity
import vn.minerva.sunnyworld.app.presentation.navigater.AndroidScreenNavigator

class MainView (mvpActivity: MvpActivity, viewCreator: AndroidMvpView.ViewCreator): AndroidMvpView(mvpActivity, viewCreator),
    MainContract.View {

    class ViewCreator(context: Context, viewGroup: ViewGroup?) :
        AndroidMvpView.LayoutViewCreator(R.layout.layout_main, context, viewGroup)

    private val loadingView = Loadinger.create(mvpActivity, mvpActivity.window)
    private var actionMenuCurrent = ActionMenu.ACTION_DEFAULT
    private var currentFragment: Fragment? = null
    private var listMenuView: ListMenuView? = null

    private val resource = MainResource()
    private val presenter = MainPresenter(screenNavigator = AndroidScreenNavigator(mvpActivity))

    override fun initCreateView() {
        initMenuView()
        initView()
    }

    override fun showError(msgError: String) {
    }

    override fun showLoading() {
        loadingView.show()
    }

    override fun hideLoading() {
        loadingView.hide()
    }

    override fun resetActionDefault() {
    }

    override fun handlerLogout() {
    }

    override fun updateSideMenu(listMenu: MutableList<ViewModel>) {
    }

    private fun initMenuView(){
        listMenuView = ListMenuView(mvpActivity, view.rvMenuItem)
        listMenuView?.let { listMenuView ->
            listMenuView.createView()
            addLifeCycle(listMenuView)
        }
    }

    private fun initView(){
        view.btnTextEnd.gone()
        setIconLeft(resource.getIconMenu())
        view.ivCloseDrawer.setOnClickListener {
            runWithCheckMultiTouch("onActionCloseDrawerClick", object : OnActionNotify {
                override fun onActionNotify() {
                    closeMenu()
                }
            })
        }
    }

    private fun setIconLeft(icon: Int) {
        view.btnBack.setImageResource(icon)
        view.btnBack.setOnClickListener {
            runWithCheckMultiTouch("onActionIconLeft", object : OnActionNotify {
                override fun onActionNotify() {
                    openMenu()
                }
            })
        }
    }

    private fun openMenu() {
        view.drawerLayout.openDrawer(GravityCompat.START)
    }

    private fun closeMenu() {
        if (view.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            view.drawerLayout.closeDrawer(GravityCompat.START)
        }
    }

}