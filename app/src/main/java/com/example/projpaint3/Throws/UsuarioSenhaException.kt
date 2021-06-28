package com.example.projpaint3.Throws

class UsuarioSenhaException : Throwable(){
    override val message: String?
        get() = "A senha deve ter entre 4 e 18 caracteres"
}
