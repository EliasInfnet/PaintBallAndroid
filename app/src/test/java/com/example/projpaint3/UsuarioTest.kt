package com.example.projpaint3

import com.example.projpaint3.Model.Usuario
import com.example.projpaint3.Throws.UsuarioEmailException
import com.example.projpaint3.Throws.UsuarioNomeException
import com.example.projpaint3.Throws.UsuarioSenhaException
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class UsuarioTest {

    lateinit var usuario : Usuario

    @Before fun setupUsuario(){
      usuario = Usuario(
            id = 1,
            nome = "elias",
            email = "elias@qwert.com",
            senha = "qwertyu"
        )
    }



    @Test fun verificar_instancia_usuario(){
        assertTrue(usuario is Usuario)
    }

    @Test fun verificar_usuario_nome(){
        try {
            usuario = Usuario(1,"rod","rodrigo123","rodrigo@qwert.com")
            assertTrue(false)
        }
        catch (e : UsuarioNomeException){
            assertEquals(UsuarioNomeException().message , e.message)
        }
    }

    @Test fun verificar_usuario_senha(){
        try {
            usuario = Usuario(1,"rodrigo","rod","rodrigo@qwert.com")
            assertTrue(false)
        }
        catch (e : UsuarioSenhaException){
            assertEquals(UsuarioSenhaException().message , e.message)
        }
    }

    @Test fun verificar_usuario_email(){
        try {
            usuario = Usuario(1,"rodrigo","rodrigo123","rodrigoqwert.com")
            assertTrue(false)
        }
        catch (e : UsuarioEmailException){
            assertEquals(UsuarioEmailException().message , e.message)
        }
    }
}