package com.example.test.ui.screen.test_base.domain

import com.github.vivchar.rendererrecyclerviewadapter.ViewModel
import vn.minerva.core.base.domain.mapper.Mapper

class TestBaseMapper : Mapper<String, MutableList<ViewModel>>{
    override fun map(input: String): MutableList<ViewModel> {
        val listReturn = mutableListOf<ViewModel>()
        return listReturn
    }

}