package com.example.projpaint3

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import com.example.projpaint3.Model.Evento
import com.example.projpaint3.Model.Usuario
import com.example.projpaint3.ViewModel.UsuarioViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_criar_evento.*

class CriarEventoActivity : AppCompatActivity() {
            lateinit var firebaseFirestore : FirebaseFirestore
            var firebaseAuth = FirebaseAuth.getInstance()
            lateinit var usuarioViewModel : UsuarioViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_criar_evento)

//        val firebaseAuth = FirebaseAuth.getInstance()
//        var email_usuario_cadastrado = firebaseAuth.currentUser!!.email
//        val firebaseFirestore = FirebaseFirestore.getInstance()
//        val caminho = firebaseFirestore.collection("Usuarios")
//        var taask = caminho.document(email_usuario_cadastrado.toString()).get()
//            .addOnSuccessListener {
//                var user = it.toObject(Usuario::class.java)
//                Toast.makeText(this,"host ok",Toast.LENGTH_SHORT).show()
//            }
//            .addOnFailureListener {
//                Toast.makeText(this,it.message,Toast.LENGTH_SHORT).show()
//            }
        btn_criar_evento_to_home.setOnClickListener {


            startActivity(Intent(this,Main2Activity::class.java))
        }
        btn_criarEvento.setOnClickListener {
            var evento = Evento(
                nome=edt_nome_evento_criar.text.toString(),
                local = edt_local_evento_criar.text.toString(),
                descricao = edt_descricao_evento_criar.text.toString(),
                data = edt_data_evento_criar.text.toString(),
                horario_inicio = edt_horario_evento_criar.text.toString(),
                host = firebaseAuth.currentUser!!.email.toString() ,
                time_A = null,
                time_B = null,
                id = null
            )
            firebaseFirestore = FirebaseFirestore.getInstance()
            var colecao = firebaseFirestore.collection("Eventos")
                .add(evento)
                .addOnSuccessListener {
                    Toast.makeText(this,"Documento salvo", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener {
                    Toast.makeText(this,it.message, Toast.LENGTH_SHORT).show()
                }
        }
    }
}
