package com.example.projpaint3.FragmentsApp

import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projpaint3.API.ApiClient
import com.example.projpaint3.ExibirEventoActivity
import com.example.projpaint3.ExibirUsuarioActivity
import com.example.projpaint3.Model.Clima
import com.example.projpaint3.Model.Evento
import com.example.projpaint3.Model.Usuario

import com.example.projpaint3.R
import com.example.projpaint3.Repositorio.Repos
import com.example.projpaint3.ViewModel.UsuarioViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.card_view_entidade.view.*
import kotlinx.android.synthetic.main.card_view_evento.view.*
import kotlinx.android.synthetic.main.fragment_home.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.time.Instant
import java.util.*

/**
 * A simple [Fragment] subclass.
 */
class HomeFragment : Fragment() {

    lateinit var firebaseFirestore: FirebaseFirestore
    lateinit var usuarioViewModel_cadastro: UsuarioViewModel
    var firebaseAuth = FirebaseAuth.getInstance()
    lateinit var listaHome : MutableList<Any>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ApiClient.getClimaService().getRJ(
        ).enqueue(
            object : Callback<Clima> {
                override fun onFailure(call: Call<Clima>, t: Throwable) {

                }

                override fun onResponse(call: Call<Clima>, response: Response<Clima>)
                {
                    val clima = response.body()
                    txt_temp.setText("${clima?.results!!.temp.toString()}°")
                }
            })




        var firebaseAuth = FirebaseAuth.getInstance()
        firebaseAuth.currentUser
        Toast.makeText(context, firebaseAuth.currentUser!!.email.toString(), Toast.LENGTH_SHORT)
            .show()

        var usuarios = mutableListOf(
            Usuario("elias", "qwert", "dsoaods", icone = 6, nivel = 2),
            Usuario("Roberto", "qwert", "dsoaods", icone = 8, nivel = 1),
            Usuario("ZE", "qwert", "dsoaods", icone = 9, nivel = 2),
            Usuario("Rodrigo", "qwert", "dsoaods", icone = 3, nivel = 3)
        )

        var usuarios2 = mutableListOf(
            Usuario("qpqpqpqpqpqpqpqpqp", "qwert", "dsoaods", icone = 11, nivel = 3),
            Usuario("Biel", "qwert", "dsoaods", icone = 12, nivel = 2),
            Usuario("Robs", "qwert", "dsoaods", icone = 1, nivel = 1),
            Usuario("elias1", "qwert", "dsoaods", icone = 5, nivel = 2),
            Usuario("elias2", "qwert", "dsoaods", icone = 7, nivel = 3),
            Usuario("elias3", "qwert", "dsoaods", icone = 2, nivel = 3),
            Usuario("elias2", "qwert", "dsoaods", icone = 4, nivel = 1)
        )

        var evento1 = Evento(
            1, "Tijuquinha", "Tijuca",
            //usuarios, usuarios,
            descricao="descricao1231gydr67tyuijovdr6tyuijjbhvgdrtfyguh23wsda"
//            Usuario(
//                "elias",
//                "dasdsas", "fasdad"
//            ), "27/2", "12:00"
        )

        var evento2 = Evento(
            2, "Paintball2", "Botafogo",
            //usuarios2, usuarios,
            descricao="descricaokbuterstdygihjojyt123123wsda"
//            Usuario(
//                "elias",
//                "dasdsas", "dasdasda"
//            ), "25/2", "18:00"
        )

        var evento3 = Evento(
            2,
            "Paintball26346",
            "Barra",
//            usuarios2,
//            usuarios2,
            descricao="WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW"
//            Usuario(
//                "elias", "elias",
//                "dasdsas", 3
//            ),
//            "7/2",
//            "10:00"
        )

        var elias = Usuario(
            "elias",
            "qwert",
            "dsoaods",
            icone = 3,
            nivel = 2,
            localidade = "RJ,Rio de Janeiro",
            sobre = "Ola meu nome e elias e sou um iniciante no paintball",
            telefone = "2199938493",
            partidas_perdidas = 10,
            partidas_ganhas = 20
        )
        var rodrigo = Usuario(
            "Rodrigo",
            "qwert",
            "dsoaods",
            icone = 7,
            nivel = 2,
            localidade = "RJ,Rio de Janeiro",
            sobre = "Ola meu nome e rodrigo e sou um iniciante no paintball",
            telefone = "21999342343",
            partidas_perdidas = 7,
            partidas_ganhas = 30
        )
        var Biel = Usuario(
            "Biel",
            "qwert",
            "dsoaods",
            icone = 2,
            nivel = 2,
            localidade = "RJ,Rio de Janeiro",
            sobre = "Ola meu nome e biel e sou um iniciante no paintball",
            telefone = "2199938493",
            partidas_perdidas = 2,
            partidas_ganhas = 15
        )

        listaHome = mutableListOf(evento1, elias, rodrigo, evento2, Biel, evento3)

        class EntidadeHomeAdapter(var entidades: MutableList<Any>) :
            RecyclerView.Adapter<EntidadeHomeAdapter.EntidadeViewHolder>() {

            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EntidadeViewHolder {
                var v = LayoutInflater.from(parent.context)
                    .inflate(R.layout.card_view_entidade, parent, false)
                return EntidadeViewHolder(v)
            }

            override fun getItemCount(): Int = entidades.size

            override fun onBindViewHolder(holder: EntidadeViewHolder, position: Int) {
                holder.bind(entidades[position])
                holder.onClickItem(entidades[position])
            }

            inner class EntidadeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
                fun bind(entidade: Any) {

                    if (entidade is Usuario) {
                        itemView.txt_titulo_entidade_rcy.text = entidade.nome
                        itemView.sub_titulo_entidade_rcy.setText("Perfil")
                        itemView.entidade_icon_rcy.setImageResource(
                            Repos().iconeIdToResource(
                                entidade.icone
                            )
                        )
                    } else if (entidade is Evento) {
                        itemView.txt_titulo_entidade_rcy.text = entidade.nome
                        itemView.sub_titulo_entidade_rcy.setText("Evento")
                        itemView.entidade_icon_rcy.setImageResource(R.drawable.ic_star_black3_24dp)
                        itemView.rlt_lyo_entidade_icon_rcy.setBackgroundResource(R.drawable.back_entidade_rcy_2)
                    }
                }

                fun onClickItem(entidade: Any) {
                    if (entidade is Evento) {
                        itemView.setOnClickListener {
                            var intent = Intent(
                                this@HomeFragment.requireContext(),
                                ExibirEventoActivity::class.java
                            )
                            intent.putExtra("evento", entidade)
                            startActivity(intent)
                        }
                    } else if (entidade is Usuario) {
                        itemView.setOnClickListener {
                            var intent = Intent(
                                this@HomeFragment.requireContext(),
                                ExibirUsuarioActivity::class.java
                            )
                            intent.putExtra("usuario", entidade)
                            startActivity(intent)
                        }
                    }
                }
            }
        }


        rcy_home_eventos.layoutManager = LinearLayoutManager(activity!!.applicationContext)
        rcy_home_eventos.adapter = EntidadeHomeAdapter(listaHome)


    }

}
