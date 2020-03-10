package com.example.test.ui.screen.main.presentation.renderer

import android.content.Context
import android.view.View
import com.example.test.R
import com.example.test.ui.screen.main.presentation.model.MenuLevel1ViewModel
import kotlinx.android.synthetic.main.item_layout_menu_level1_vholder.view.*
import vn.minerva.core.base.presentation.mvp.android.model.ViewRenderer

class MenuLevel1ViewRenderer(context: Context): ViewRenderer<MenuLevel1ViewModel>(context){
    override fun getLayoutId(): Int {
        return R.layout.item_layout_menu_level1_vholder
    }

    override fun getModelClass(): Class<MenuLevel1ViewModel> = MenuLevel1ViewModel::class.java

    override fun bindView(model: MenuLevel1ViewModel, viewRoot: View) {
        viewRoot.tvMenuName.text = model.name
    }

}