package com.example.projpaint3.FragmentsApp

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projpaint3.CriarEventoActivity
import com.example.projpaint3.EventoConcluidoActivity
import com.example.projpaint3.ExibirEventoActivity
import com.example.projpaint3.Model.Evento
import com.example.projpaint3.Model.Usuario

import com.example.projpaint3.R
import com.example.projpaint3.ViewModel.EventoViewModel
import com.example.projpaint3.ViewModel.UsuarioViewModel
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.card_view_evento.view.*
import kotlinx.android.synthetic.main.fragment_meus_eventos.*

/**
 * A simple [Fragment] subclass.
 */
class MeusEventosFragment : Fragment() {

    lateinit var firebaseFirestore: FirebaseFirestore
    lateinit var eventoViewModel: EventoViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_meus_eventos, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity?.let {
            eventoViewModel= ViewModelProviders.of(it).get(EventoViewModel::class.java)
        }

        var usuarios = mutableListOf(
            Usuario("elias","qwert","dsoaods",icone = 6, nivel = 2),
            Usuario("Roberto","qwert","dsoaods",icone = 8, nivel = 1),
            Usuario("ZE","qwert","dsoaods",icone = 9, nivel = 2),
            Usuario("Rodrigo","qwert","dsoaods",icone = 3, nivel = 3))

        var usuarios2 = mutableListOf(
            Usuario("qpqpqpqpqpqpqpqpqp","qwert","dsoaods",icone = 11, nivel = 3),
            Usuario("Biel","qwert","dsoaods",icone = 12, nivel = 2),
            Usuario("Robs","qwert","dsoaods",icone = 1, nivel = 1),
            Usuario("elias1","qwert","dsoaods",icone = 5, nivel = 2),
            Usuario("elias2","qwert","dsoaods",icone = 7, nivel = 3),
            Usuario("elias3","qwert","dsoaods",icone = 2, nivel = 3),
            Usuario("elias2","qwert","dsoaods",icone = 4, nivel = 1))

//        var evento1 = Evento(
//            1,"Tijuquinha","Tijuca",usuarios,usuarios ,
//            "descricao1231gydr67tyuijovdr6tyuijjbhvgdrtfyguh23wsda",
//            , "27/2","12:00")

//        var evento2 = Evento(
//            2,"Paintball2","Botafogo",usuarios2,usuarios,
//            "descricaokbuterstdygihjojyt123123wsda",Usuario("elias",
//                "dasdsas","dasdasda"), "25/2","18:00")

//        var evento3 = Evento(
//            2,"Paintball26346","Barra",usuarios2,usuarios2,
//            "descrihguiuhihiuuhugvrdtgyuhijokoiutydrcao13wsda",Usuario("elias","elias",
//                "dasdsas",3), "7/2","10:00")

  //      var listaEventos : MutableList<Evento> = mutableListOf(evento1,evento2,evento3)

        class EventoAdapter(var lista_eventos : MutableList<Evento>) : RecyclerView.Adapter<EventoAdapter.EventoViewHolder>(){

            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventoViewHolder {
                var v = LayoutInflater.from(parent.context).inflate(R.layout.card_view_evento,parent,false)
                return EventoViewHolder(v)
            }

            override fun getItemCount(): Int = lista_eventos.size

            override fun onBindViewHolder(holder: EventoViewHolder, position: Int) {
                holder.bind(lista_eventos[position])
                holder.onClickItem(lista_eventos[position])
            }

            inner class EventoViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
                fun bind(evento : Evento){

                    itemView.evento_nome_txt.text = evento.nome
                    itemView.evento_local_txt.text = evento.local
                    itemView.data_evento_txt.text = evento.data
                    itemView.horario_evento_txt.text = evento.horario_inicio
//                    itemView.participantes_evento_txt.text = evento.participantesTotais().toString()

                }

                fun onClickItem(evento:Evento){
                    itemView.setOnClickListener {
                        var intent = Intent(activity!!.applicationContext,ExibirEventoActivity::class.java)
                        intent.putExtra("evento", evento)
                        startActivity(intent)
                    }
                }
            }
        }

        class EventoHostAdapter(var lista_eventos : MutableList<Evento>) : RecyclerView.Adapter<EventoHostAdapter.EventoHostViewHolder>(){

            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventoHostViewHolder {
                var v = LayoutInflater.from(parent.context).inflate(R.layout.card_view_evento,parent,false)
                return EventoHostViewHolder(v)
            }

            override fun getItemCount(): Int = lista_eventos.size

            override fun onBindViewHolder(holder: EventoHostViewHolder, position: Int) {
                holder.bind(lista_eventos[position])
                holder.onClickItem(lista_eventos[position])
            }

            inner class EventoHostViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
                fun bind(evento : Evento){

                    itemView.evento_nome_txt.text = evento.nome
                    itemView.evento_local_txt.text = evento.local
                    itemView.data_evento_txt.text = evento.data
                    itemView.horario_evento_txt.text = evento.horario_inicio
//                    itemView.participantes_evento_txt.text = evento.participantesTotais().toString()

                }

                fun onClickItem(evento:Evento){
                    itemView.setOnClickListener {
                        var intent = Intent(activity!!.applicationContext,EventoConcluidoActivity::class.java)

                        intent.putExtra("Host",evento.host.toString())
                        intent.putExtra("evento", evento)
                        startActivity(intent)
                    }
                }
            }
        }

        val firebaseFirestore = FirebaseFirestore.getInstance()
        val caminho = firebaseFirestore.collection("Eventos")
            .get()
            .addOnSuccessListener {
                var listEventos = it.toObjects(Evento::class.java)
                rcy_eventos_que_participo.layoutManager = LinearLayoutManager(activity!!.applicationContext,LinearLayoutManager.HORIZONTAL,false)
                rcy_eventos_que_participo.adapter = EventoAdapter(listEventos)
            }
            .addOnFailureListener{
                Toast.makeText(context,it.message,Toast.LENGTH_SHORT).show()
            }
        //rcy_eventos_que_participo.layoutManager = LinearLayoutManager(activity!!.applicationContext,LinearLayoutManager.HORIZONTAL,false)
        //rcy_eventos_que_participo.adapter = EventoAdapter(listaEventos)

      //  rcy_eventos_criados.layoutManager = LinearLayoutManager(activity!!.applicationContext,LinearLayoutManager.HORIZONTAL,false)
        //rcy_eventos_criados.adapter = EventoHostAdapter(listaEventos)

        btn_criar_evento.setOnClickListener {
            startActivity(Intent(activity!!.applicationContext,CriarEventoActivity::class.java))
        }

    }
}
