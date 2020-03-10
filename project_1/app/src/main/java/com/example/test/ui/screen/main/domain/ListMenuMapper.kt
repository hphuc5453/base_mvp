package com.example.test.ui.screen.main.domain

import com.example.test.ui.screen.main.presentation.model.MenuLevel1ViewModel
import com.github.vivchar.rendererrecyclerviewadapter.ViewModel
import vn.minerva.core.base.domain.mapper.Mapper

class ListMenuMapper : Mapper<String, MutableList<ViewModel>>{
    override fun map(input: String): MutableList<ViewModel> {
        val listReturn = mutableListOf<ViewModel>()
        listReturn.add(MenuLevel1ViewModel(
            id = 0,
            name = "MENU 1"
        ))
        return listReturn
    }

}