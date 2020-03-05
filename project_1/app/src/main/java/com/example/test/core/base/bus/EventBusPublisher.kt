package vn.minerva.core.base.bus

interface EventBusPublisher {
    fun publishEvent(data: EventBusData)
}
