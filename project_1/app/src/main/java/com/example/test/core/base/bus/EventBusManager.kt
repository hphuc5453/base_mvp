package vn.minerva.core.base.bus

interface EventBusManager : EventBusPublisher {
    fun register(handler: EventBusHandler)
    fun unregister(handler: EventBusHandler)
}