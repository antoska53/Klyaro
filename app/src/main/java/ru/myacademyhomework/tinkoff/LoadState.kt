package ru.myacademyhomework.tinkoff

sealed class LoadState
class Loading: LoadState()
class Ready: LoadState()
class ErrorLoad: LoadState()
class SuccessLoad: LoadState()