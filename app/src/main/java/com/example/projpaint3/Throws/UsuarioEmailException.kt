package com.example.projpaint3.Throws

class UsuarioEmailException : Throwable(){
    override val message: String?
        get() = "O email deve conter um @"
}
