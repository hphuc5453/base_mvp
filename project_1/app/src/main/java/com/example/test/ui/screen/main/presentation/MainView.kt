package com.example.test.ui.screen.main.presentation

import android.content.Context
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.test.R
import com.example.test.core.app.view.loading.Loadinger
import com.example.test.ui.screen.main.data.ActionMenu
import com.github.vivchar.rendererrecyclerviewadapter.ViewModel
import vn.minerva.core.base.presentation.mvp.android.AndroidMvpView
import vn.minerva.core.base.presentation.mvp.android.MvpActivity

class MainView (mvpActivity: MvpActivity, viewCreator: AndroidMvpView.ViewCreator): AndroidMvpView(mvpActivity, viewCreator),
    MainContract.View {

    class ViewCreator(context: Context, viewGroup: ViewGroup?) :
        AndroidMvpView.LayoutViewCreator(R.layout.layout_main, context, viewGroup)

    private val loadingView = Loadinger.create(mvpActivity, mvpActivity.window)
    private var actionMenuCurrent = ActionMenu.ACTION_DEFAULT
    private var currentFragment: Fragment? = null


    override fun initCreateView() {

    }

    override fun showError(msgError: String) {
    }

    override fun showLoading() {
    }

    override fun hideLoading() {
    }

    override fun resetActionDefault() {
    }

    override fun handlerLogout() {
    }

    override fun updateSideMenu(listMenu: MutableList<ViewModel>) {
    }

}