package com.example.projpaint3

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projpaint3.Model.Evento
import com.example.projpaint3.Model.Usuario
import com.example.projpaint3.Repositorio.Repos
import kotlinx.android.synthetic.main.activity_evento_concluido.*
import kotlinx.android.synthetic.main.card_view_timea.view.*
import kotlinx.android.synthetic.main.card_view_timeb.view.*

class EventoConcluidoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_evento_concluido)

        var evento = intent.getSerializableExtra("evento") as Evento

        txt_nome_evento_concluido.setText(evento.nome.toString())
        txt_data_evento_concluido.setText(evento.data)
        txt_descricao_evento_concluido.setText(evento.descricao)
        txt_local_evento_concluido.setText(evento.local)
        txt_host_evento_concluido.setText("evento.host!!.nome")
        txt_horario_evento_concluido.setText(evento.horario_inicio)

        class TimeA_concluido_Adapter(var lista_Time_A : MutableList<Usuario>) : RecyclerView.Adapter<TimeA_concluido_Adapter.TimeA_concluidoViewHolder>(){

            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimeA_concluidoViewHolder {
                var v = LayoutInflater.from(parent.context).inflate(R.layout.card_view_timea,parent,false)
                return TimeA_concluidoViewHolder(v)
            }

            override fun getItemCount(): Int = lista_Time_A.size

            override fun onBindViewHolder(holder: TimeA_concluidoViewHolder, position: Int) {
                holder.bind(lista_Time_A[position])
            }

            inner class TimeA_concluidoViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
                fun bind(usuario : Usuario){
                    itemView.icone_usuario_timea.setImageResource(Repos().iconeIdToResource(usuario.icone))
                    itemView.nome_usuario_timea.text = usuario.nome
                    itemView.nivel_usuario_timea.text = Repos().nivel_usuario_toString(usuario.nivel)
                }
            }
        }

       // rcy_timeA_evento_concluido.adapter = TimeA_concluido_Adapter(evento.time_A!!)
        //rcy_timeA_evento_concluido.layoutManager = LinearLayoutManager(this)

        class TimeB_concluido_Adapter(var lista_Time_B : MutableList<Usuario>) : RecyclerView.Adapter<TimeB_concluido_Adapter.TimeB_concluidoViewHolder>(){

            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimeB_concluidoViewHolder {
                var v = LayoutInflater.from(parent.context).inflate(R.layout.card_view_timeb,parent,false)
                return TimeB_concluidoViewHolder(v)
            }

            override fun getItemCount(): Int = lista_Time_B.size

            override fun onBindViewHolder(holder: TimeB_concluidoViewHolder, position: Int) {
                holder.bind(lista_Time_B[position])
            }

            inner class TimeB_concluidoViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
                fun bind(usuario : Usuario){
                    itemView.icone_usuario_timeb.setImageResource(Repos().iconeIdToResource(usuario.icone))
                    itemView.nome_usuario_timeb.text = usuario.nome
                    itemView.nivel_usuario_timeb.text = Repos().nivel_usuario_toString(usuario.nivel)
                }
            }
        }

        //rcy_timeB_evento_concluido.adapter = TimeB_concluido_Adapter(evento.time_B!!)
        //rcy_timeB_evento_concluido.layoutManager = LinearLayoutManager(this)

        btn_voltar_evento.setOnClickListener {
            startActivity(Intent(this, Main2Activity::class.java))
        }

        var timeSelecionado = 1

        btn_timeA_evento_concuido.setOnClickListener {
            it.setBackgroundResource(R.drawable.ft_selected_edit_perfil)
            btn_timeB_evento_concuido.setBackgroundResource(R.drawable.edt_txt_format2)
            timeSelecionado = 1
        }

        btn_timeB_evento_concuido.setOnClickListener {
            it.setBackgroundResource(R.drawable.ft_selected_edit_perfil)
            btn_timeA_evento_concuido.setBackgroundResource(R.drawable.edt_txt_format2)
            timeSelecionado = 2
        }
    }


}
