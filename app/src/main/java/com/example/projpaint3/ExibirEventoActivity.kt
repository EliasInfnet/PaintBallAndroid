package com.example.projpaint3

import android.content.Intent
import android.graphics.drawable.GradientDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projpaint3.Model.Evento
import com.example.projpaint3.Model.Usuario
import com.example.projpaint3.Repositorio.Repos
import com.example.projpaint3.ViewModel.EventoViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_exibir_evento.*
import kotlinx.android.synthetic.main.card_view_evento.*
import kotlinx.android.synthetic.main.card_view_evento.view.*
import kotlinx.android.synthetic.main.card_view_timea.view.*
import kotlinx.android.synthetic.main.card_view_timeb.view.*

class ExibirEventoActivity : AppCompatActivity() {

    lateinit var firebaseFirestore: FirebaseFirestore
    var firebaseAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exibir_evento)
//            eventoViewModel= ViewModelProviders.of(FragmentActivity()).get(EventoViewModel::class.java)
        var evento = intent.getSerializableExtra("evento") as Evento
           var firebaseFirestore = FirebaseFirestore.getInstance()
            firebaseFirestore.collection("Usuarios").document(evento.host!!).get()
                .addOnSuccessListener {
                    var host = it.toObject(Usuario::class.java)
                    txt_nome_evento_exibir.setText(evento.nome)
                    txt_data_evento_exibir.setText(evento.data)
                    txt_descricao_evento_exibir.setText(evento.descricao)
                    txt_local_evento_exibir.setText(evento.local)
                    txt_host_evento_exibir.setText(host!!.nome)
                    txt_horario_evento_exibir.setText(evento.horario_inicio)
                }
        class TimeA_Adapter(var lista_Time_A : MutableList<Usuario>) : RecyclerView.Adapter<TimeA_Adapter.TimeAViewHolder>(){
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimeAViewHolder {
                var v = LayoutInflater.from(parent.context).inflate(R.layout.card_view_timea,parent,false)
                return TimeAViewHolder(v)
            }
            override fun getItemCount(): Int = lista_Time_A.size
            override fun onBindViewHolder(holder: TimeAViewHolder, position: Int) {
                holder.bind(lista_Time_A[position])
            }
            inner class TimeAViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
                fun bind(usuario : Usuario){
                    itemView.icone_usuario_timea.setImageResource(Repos().iconeIdToResource(usuario.icone))
                    itemView.nome_usuario_timea.text = usuario.nome
                    itemView.nivel_usuario_timea.text = Repos().nivel_usuario_toString(usuario.nivel)
                }
            }
        }
        class TimeB_Adapter(var lista_Time_B : MutableList<Usuario>) : RecyclerView.Adapter<TimeB_Adapter.TimeBViewHolder>(){
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimeBViewHolder {
                var v = LayoutInflater.from(parent.context).inflate(R.layout.card_view_timeb,parent,false)
                return TimeBViewHolder(v)
            }
            override fun getItemCount(): Int = lista_Time_B.size

            override fun onBindViewHolder(holder: TimeBViewHolder, position: Int) {
                holder.bind(lista_Time_B[position])
            }
            inner class TimeBViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
                fun bind(usuario : Usuario){
                    itemView.icone_usuario_timeb.setImageResource(Repos().iconeIdToResource(usuario.icone))
                    itemView.nome_usuario_timeb.text = usuario.nome
                    itemView.nivel_usuario_timeb.text = Repos().nivel_usuario_toString(usuario.nivel)
                }
            }
        }
        //rcy_timeB_evento_exibir.adapter = TimeB_Adapter(evento.time_B!!)
        //rcy_timeB_evento_exibir.layoutManager = LinearLayoutManager(this)
        btnEntrarTimeA.setOnClickListener {
            //if usuario nao esta em nenhuma lista, add na lista A
            //else if usuario esta na listaA -> tira ele da lista A
            // se ele esta na lista B -> tira ele da B e bota na A
            var usuario_id = firebaseAuth.currentUser!!.email
            firebaseFirestore = FirebaseFirestore.getInstance()
            firebaseFirestore.collection("Usuarios").document(usuario_id!!).get()
                .addOnSuccessListener {
                    var user = it.toObject(Usuario::class.java)
                    val listatime = mutableListOf<Usuario>()
                    listatime.add(user!!)
                    rcy_timeA_evento_exibir.adapter = TimeA_Adapter(listatime)
                    rcy_timeA_evento_exibir.layoutManager = LinearLayoutManager(this)
                }
        }
        btnEntrarTimeB.setOnClickListener {
            //if usuario nao esta em nenhuma lista, add na lista B
            //else if usuario esta na listaB -> tira ele da lista B
            // se ele esta na lista A -> tira ele da A e bota na Ba
            var usuario_id = firebaseAuth.currentUser!!.email
            firebaseFirestore = FirebaseFirestore.getInstance()
            firebaseFirestore.collection("Usuarios").document(usuario_id!!).get()
                .addOnSuccessListener {
                    var user = it.toObject(Usuario::class.java)
                    val listatime = mutableListOf<Usuario>()
                    listatime.add(user!!)
                    rcy_timeB_evento_exibir.adapter = TimeB_Adapter(listatime)
                    rcy_timeB_evento_exibir.layoutManager = LinearLayoutManager(this)
                }
        }
        btn_voltar_evento_exibir.setOnClickListener {
            var intent = Intent(this, Main2Activity ::class.java)
            startActivity(intent)
        }
    }

}