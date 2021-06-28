package com.example.projpaint3.ViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.projpaint3.Model.Evento
import java.io.Serializable

class EventoViewModel : ViewModel(),Serializable{

    var evento : Evento? = null

}