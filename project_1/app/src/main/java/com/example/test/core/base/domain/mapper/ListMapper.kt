package vn.minerva.core.base.domain.mapper

class ListMapper<I, O> {
    fun map(lst: List<I>, mapper: Mapper<I, O>) : List<O> {
        return lst.map { mapper.map(it) }
    }
}