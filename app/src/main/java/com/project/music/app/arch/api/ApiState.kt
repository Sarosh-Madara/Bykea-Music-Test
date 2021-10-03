package com.project.music.app.arch.api

sealed class ApiState {
    object LOADING : ApiState()
    class ERROR(val msg: Throwable) : ApiState()
    class SUCCESS(val data: Any) : ApiState()
    object EMPTY : ApiState()
}
