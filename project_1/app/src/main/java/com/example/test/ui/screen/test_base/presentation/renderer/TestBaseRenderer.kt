package com.example.test.ui.screen.test_base.presentation.renderer

import android.content.Context
import android.view.View
import com.example.test.R
import com.example.test.ui.screen.test_base.presentation.model.TestBaseViewModel
import kotlinx.android.synthetic.main.item_layout_test_base.view.*
import vn.minerva.core.base.presentation.mvp.android.model.ViewRenderer

class TestBaseRenderer (context: Context): ViewRenderer<TestBaseViewModel>(context){
    override fun getLayoutId(): Int {
        return R.layout.item_layout_test_base
    }

    override fun getModelClass(): Class<TestBaseViewModel> = TestBaseViewModel::class.java

    override fun bindView(model: TestBaseViewModel, viewRoot: View) {
        viewRoot.tvTestBase.text = model.name
    }

}