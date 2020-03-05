package vn.minerva.core.base.domain.listener

import java.io.Serializable

@FunctionalInterface
interface Executable : Serializable {
    fun execute()
}
