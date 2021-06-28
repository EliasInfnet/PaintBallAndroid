package com.example.projpaint3.Model

import com.google.firebase.firestore.DocumentReference
import java.io.Serializable


class Evento(
    var id : Int? = null,
    var nome : String? = null,
    var local : String? = null,
    var time_A : MutableList<String>?=null,
    var time_B : MutableList<String>?=null,
    var descricao : String? = null,
    var host : String? = null,
    var data : String? = null,
    var horario_inicio : String? = null

) : Serializable{
    fun participantesTotais() : Int{
        return time_A!!.size + time_B!!.size
    }

//    fun eventoConcluido() : Boolean{
//        return
//    }
}

