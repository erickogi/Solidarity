package com.dev.common.listeners


interface BackPressRegister {
    fun registerHandler(handler: BackPressHandler)
    fun unregisterHandler(handler: BackPressHandler)
}