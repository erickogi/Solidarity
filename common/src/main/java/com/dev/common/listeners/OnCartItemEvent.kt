package com.dev.common.listeners


interface OnCartItemEvent {
    fun delete(pos: Int)
    fun add(pos: Int)
    fun remove(pos: Int)


}
