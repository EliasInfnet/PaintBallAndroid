package com.example.projpaint3.Model

import android.provider.ContactsContract
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.projpaint3.Throws.UsuarioEmailException
import com.example.projpaint3.Throws.UsuarioNomeException
import com.example.projpaint3.Throws.UsuarioSenhaException
import java.io.Serializable
import java.math.RoundingMode
import java.text.DecimalFormat
import java.util.regex.Pattern


class Usuario(

    var nome : String? = null,
    var senha : String? = null,
    var email : String? = null,
    var nivel : Int = 1,
    var localidade : String = "Localidade não registrada",
    var sobre : String = "Sem descrição",
    var icone : Int = 1,
    var telefone : String = "Telefone não registrado",
    var partidas_ganhas : Int = 0,
    var partidas_perdidas : Int = 0

) : Serializable {
//    init {
//        if (nome.length !in 4..18) throw UsuarioNomeException()
//        if (senha.length !in 4..18) throw UsuarioSenhaException()
//        if(!email.contains("@")) throw UsuarioEmailException()
//    }

    fun partidas_totais() : Int{
        return partidas_ganhas + partidas_perdidas
    }

    fun desempenho() : String{
        return "%.0f".format((partidas_ganhas.toDouble() / partidas_totais().toDouble()) * 100) + "%"
    }
}

