package com.example.test.ui.screen.test_base.presentation

import android.graphics.drawable.Drawable
import com.example.test.R
import vn.minerva.core.base.domain.provider.AndroidResourceProvider

class TestBaseResource : AndroidResourceProvider(){
    fun getString(): String{
        return String.format(resourceManager.getString(R.string.ACTION_CANCEL))
    }

    fun getColor(): Int{
        return resourceManager.getColor(R.color.colorAccent)
    }

    fun getDrawable(): Drawable?{
        return resourceManager.getDrawable(R.drawable.ic_launcher_background)
    }
}