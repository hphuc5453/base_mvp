package vn.minerva.core.base.domain.listener

@FunctionalInterface
interface OnActionDataWithPos<in T> {
    fun onAction(data: T, pos: Int)
}
