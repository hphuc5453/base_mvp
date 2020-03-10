package com.example.test.ui.screen.main.presentation

import com.example.test.R
import vn.minerva.core.base.domain.provider.AndroidResourceProvider

class MainResource : AndroidResourceProvider(){
    fun getIconMenu(): Int {
        return R.drawable.ic_side_menu_white
    }
}