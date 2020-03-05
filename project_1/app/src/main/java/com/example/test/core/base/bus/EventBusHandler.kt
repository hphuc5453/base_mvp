package vn.minerva.core.base.bus

interface EventBusHandler {
    fun onReceiveEvent(data: EventBusData)
}