package com.example.test.ui.screen.test_base.presentation.renderer

import android.content.Context
import android.view.View
import com.example.test.R
import com.example.test.core.app.util.image.GlideImageHelper
import com.example.test.ui.screen.test_base.presentation.model.TestBaseViewModel
import kotlinx.android.synthetic.main.item_layout_test_base.view.*
import vn.minerva.core.app.domain.excecutor.EventFireUtil
import vn.minerva.core.base.domain.listener.OnActionData
import vn.minerva.core.base.presentation.mvp.android.model.ViewRenderer

class TestBaseRenderer (context: Context, private val onaction : OnActionData<TestBaseViewModel>): ViewRenderer<TestBaseViewModel>(context){
    override fun getLayoutId(): Int {
        return R.layout.item_layout_test_base
    }

    override fun getModelClass(): Class<TestBaseViewModel> = TestBaseViewModel::class.java

    override fun bindView(model: TestBaseViewModel, viewRoot: View) {
        viewRoot.tvTestBase.text = model.name
        EventFireUtil.fireEvent(onaction, model)
    }

}