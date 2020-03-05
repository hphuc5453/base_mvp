package vn.minerva.core.base.domain.manager

import android.content.res.Resources
import android.graphics.drawable.Drawable
import androidx.annotation.*
import androidx.core.content.ContextCompat
import vn.minerva.core.app.common.AppConfigs


class AndroidResourceManager : ResourceManager {
    override fun getString(@StringRes stringId: Int): String =
        AppConfigs.instance.getBaseApplication().getString(stringId)

    override fun getInt(@IntegerRes intId: Int): Int = AppConfigs.instance.getBaseApplication().resources?.getInteger(intId)!!

    override fun getDrawable(@DrawableRes drawableId: Int): Drawable? = ContextCompat.getDrawable(AppConfigs.instance.getBaseApplication(), drawableId)

    @ColorInt
    override fun getColor(@ColorRes colorId: Int): Int = ContextCompat.getColor(AppConfigs.instance.getBaseApplication(), colorId)

    override fun pxToDp(px: Int): Int = (px.toFloat() / Resources.getSystem().displayMetrics.density).toInt()

    override fun dpToPx(dp: Int): Int = (dp.toFloat() * Resources.getSystem().displayMetrics.density).toInt()

    override fun getDimensionPixelSize(@DimenRes dimenRes: Int): Int {
        return AppConfigs.instance.getBaseApplication().resources?.getDimensionPixelSize(dimenRes)!!
    }
}