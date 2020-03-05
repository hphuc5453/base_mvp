package vn.minerva.core.app.view

import androidx.recyclerview.widget.RecyclerView

abstract class BaseDividerItemDecoration : RecyclerView.ItemDecoration() {
    var startPositionOfNormalType: Int = -1
    abstract fun reset()
}