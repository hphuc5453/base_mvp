package vn.minerva.core.base.domain.listener

@FunctionalInterface
interface OnWorking<out T> {
    fun work(): T
}
