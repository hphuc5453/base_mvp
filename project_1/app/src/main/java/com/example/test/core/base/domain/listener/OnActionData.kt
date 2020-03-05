package vn.minerva.core.base.domain.listener

import java.io.Serializable

@FunctionalInterface
interface OnActionData<in T> : Serializable {
    fun onAction(data: T)
}
