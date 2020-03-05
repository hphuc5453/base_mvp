package vn.minerva.core.base.domain.manager

import android.graphics.drawable.Drawable
import androidx.annotation.*

interface ResourceManager {
    fun getString(@StringRes stringId: Int) : String

    fun getInt(@IntegerRes intId:  Int) : Int

    fun getDrawable(@DrawableRes drawableId: Int): Drawable?

    @ColorInt
    fun getColor(@ColorRes colorId: Int): Int

    fun pxToDp(px: Int): Int

    fun dpToPx(dp: Int): Int

    fun getDimensionPixelSize(@DimenRes dimenRes: Int): Int
}