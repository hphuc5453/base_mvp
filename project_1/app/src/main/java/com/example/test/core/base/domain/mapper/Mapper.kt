package vn.minerva.core.base.domain.mapper

interface Mapper<in I, out O> {
    fun map(input: I) : O
}