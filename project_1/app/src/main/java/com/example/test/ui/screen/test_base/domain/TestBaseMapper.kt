package com.example.test.ui.screen.test_base.domain

import com.example.test.ui.screen.test_base.presentation.model.TestBaseViewModel
import com.github.vivchar.rendererrecyclerviewadapter.ViewModel
import vn.minerva.core.base.domain.mapper.Mapper

class TestBaseMapper : Mapper<String, MutableList<ViewModel>>{
    override fun map(input: String): MutableList<ViewModel> {
        val listReturn = mutableListOf<ViewModel>()

        listReturn.add(TestBaseViewModel(
            name = "asfasf"
        ))
        return listReturn

        listReturn.add(TestBaseViewModel(
            name = "asfasf"
        ))

        listReturn.add(TestBaseViewModel(
            name = "asfasf"
        ))

        listReturn.add(TestBaseViewModel(
            name = "asfasf"
        ))
        return listReturn
    }

}