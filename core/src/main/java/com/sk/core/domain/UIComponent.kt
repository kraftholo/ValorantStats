package com.sk.core.domain

sealed class UIComponent {

    data class Dialog(
        val title: String,
        val description: String
    ) : UIComponent()

    //snackbars,toasts,notifications etc.

    data class None(
        val message: String
    ) : UIComponent()
}