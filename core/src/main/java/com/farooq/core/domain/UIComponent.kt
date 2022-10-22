package com.farooq.core.domain

sealed class UIComponent {
    data class Dialog(val title: String, val description: String) : UIComponent()
    data class ISDialog(val title: Int, val description: String) : UIComponent()
    data class IDialog(val title: Int, val description: Int) : UIComponent()
    data class None(val message: String) : UIComponent()
    data class INone(val message: Int) : UIComponent()
}