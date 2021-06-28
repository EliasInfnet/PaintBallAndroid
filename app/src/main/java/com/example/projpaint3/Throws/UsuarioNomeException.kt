package com.example.projpaint3.Throws

class UsuarioNomeException : Throwable(){
    override val message: String?
        get() = "O nome deve ter entre 4 e 18 caracteres"
}
