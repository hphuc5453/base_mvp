package vn.minerva.core.base.bus

import java.util.concurrent.CopyOnWriteArrayList

class SimpleEventBusManager : EventBusManager {
    private val handlers = CopyOnWriteArrayList<EventBusHandler>()

    override fun register(handler: EventBusHandler) {
        if (!this.handlers.contains(handler)) {
            this.handlers.add(handler)
        }
    }

    override fun unregister(handler: EventBusHandler) {
        this.handlers.remove(handler)
    }

    override fun publishEvent(data: EventBusData) {
        for (handler in handlers) {
            handler?.onReceiveEvent(data)
        }
    }
}
