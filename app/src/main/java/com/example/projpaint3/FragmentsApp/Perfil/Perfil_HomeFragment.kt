package com.example.projpaint3.FragmentsApp.Perfil

import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.example.projpaint3.Dialog.CarregandoDialog
import com.example.projpaint3.MainActivity
import com.example.projpaint3.Model.Usuario

import com.example.projpaint3.R
import com.example.projpaint3.Repositorio.Repos
import com.example.projpaint3.ViewModel.UsuarioViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_perfil__home.*

/**
 * A simple [Fragment] subclass.
 */
class Perfil_HomeFragment : Fragment() {

    lateinit var firebaseFirestore : FirebaseFirestore
    lateinit var usuarioViewModel: UsuarioViewModel
    lateinit var carregandoDialog : CarregandoDialog


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_perfil__home, container, false)


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        carregandoDialog = CarregandoDialog(this.requireActivity())
        carregandoDialog.startLoadingDialog()

        activity?.let {
            usuarioViewModel=ViewModelProviders.of(it).get(UsuarioViewModel::class.java)
        }

        btn_editar_prefil.setOnClickListener{
            findNavController().navigate(R.id.action_perfil_HomeFragment_to_perfil_EditFragment)
        }
        busca().execute()

        btn_logout.setOnClickListener {
            startActivity(Intent(this.requireActivity(),MainActivity::class.java))
            activity.let {
                activity.let {
                    it!!.finish()
                }
            }
        }

//        var firebaseFirestore = FirebaseFirestore.getInstance()
//        var caminho = firebaseFirestore.collection("Usuarios")
//         var taask = caminho.document("gabrielt@oi.com").get()
//            taask.addOnSuccessListener {
//                if (it!= null) {
//                    Toast.makeText(context, "busca user , ok", Toast.LENGTH_SHORT)
//                        .show()
//                }
//           }
//            taask.addOnFailureListener{
//                Toast.makeText(context,it.message,Toast.LENGTH_SHORT).show()
//            }





//        var usuario = Usuario("Roberto","qwerty","roberto@gmail.com",3,
//            sobre = "Ola meu nome eh roberto e sou avancado no paintball", icone = 7,telefone = "3192381102")




    }
    inner class busca : AsyncTask<Unit, Unit, Usuario>() {
        override fun doInBackground(vararg params: Unit?): Usuario {
            val firebaseAuth = FirebaseAuth.getInstance()
            var email_usuario_cadastrado = firebaseAuth.currentUser!!.email
            val firebaseFirestore = FirebaseFirestore.getInstance()
            val caminho = firebaseFirestore.collection("Usuarios")
            var taask = caminho.document(email_usuario_cadastrado.toString()).get()
                .addOnSuccessListener {
                    var user = it.toObject(Usuario::class.java)
                        Toast.makeText(context,"${user!!.email},${user.telefone},",Toast.LENGTH_SHORT).show()
                    ft_perfil.setImageResource(Repos().iconeIdToResource(user.icone))
                    nome_txt_perfil.setText(user.nome)
                    txt_localidade_perfil.setText(user.localidade)
                    partidas_totais_txt_perfil.setText(user.partidas_totais().toString())
                    partidas_ganhas_txt_perfil.setText(user.partidas_ganhas.toString())
                    desempenho_txt_perfil2.setText(user.desempenho().toString())
                    txt_sobre_perfil.setText(user.sobre)
                    txt_nivel.setText(Repos().nivel_usuario_toString(user.nivel))
                    txt_telefone.setText(user.telefone)

                    usuarioViewModel.usuario = user

                    carregandoDialog.dismissDialog()

                }
                .addOnFailureListener {

                }
            return Usuario("bla","blbal","belao")
        }

        override fun onPostExecute(result: Usuario?) {
            Toast.makeText(context,result!!.nome,Toast.LENGTH_SHORT)
        }
    }
}
