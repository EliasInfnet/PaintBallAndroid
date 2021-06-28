package com.example.projpaint3.FragmentsInicial

import android.opengl.Visibility
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.example.projpaint3.Model.Usuario

import com.example.projpaint3.R
import com.example.projpaint3.ViewModel.UsuarioViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.fragment_cadastro.*

/**
 * A simple [Fragment] subclass.
 */
class CadastroFragment : Fragment() {

    private lateinit var usuarioViewModel_cadastro : UsuarioViewModel
    lateinit var firebaseFirestore : FirebaseFirestore

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cadastro, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btn_cadastro_to_login.setOnClickListener {
            findNavController().navigate(R.id.action_cadastroFragment_to_loginFragment)
        }




        btn_cadastrar.setOnClickListener {

            Verificar_Dados_Invalidos()

//          Esse if verifica se todas os dados foram preenchidos corretamente e navega para a pagina de login

            if(Verificar_Email(edt_email_cadastro.text.toString())
                && (edt_confirmaSenha_cadastro.text.toString() == edt_senha_cadastro.text.toString())
                && Verificar_string(edt_senha_cadastro.text.toString())
                && Verificar_string(edt_usuario_cadastro.text.toString())
            ){

                activity?.let{
                    usuarioViewModel_cadastro = ViewModelProviders.of(it).get(UsuarioViewModel::class.java)
                }

                var usuario = Usuario(
                    nome = edt_usuario_cadastro.text.toString(),
                    senha = edt_senha_cadastro.text.toString(),
                    email = edt_email_cadastro.text.toString()
                )

                    firebaseFirestore = FirebaseFirestore.getInstance()

                  var firebaseAuth = FirebaseAuth.getInstance()
                    firebaseAuth.createUserWithEmailAndPassword(edt_email_cadastro.text.toString(),edt_senha_cadastro.text.toString())
                    .addOnSuccessListener {
                        Toast.makeText(context,"Ok",Toast.LENGTH_SHORT).show()
                        var colecao = firebaseFirestore.collection("Usuarios")
                        colecao.document(usuario.email.toString()).set(usuario)
                            .addOnSuccessListener {
                                Toast.makeText(context,"Documento salvo",Toast.LENGTH_SHORT).show()
                            }
                            .addOnFailureListener {
                                Toast.makeText(context,it.message,Toast.LENGTH_SHORT).show()
                            }
                    }
                    .addOnFailureListener {
                        Toast.makeText(context,it.message,Toast.LENGTH_SHORT).show()
                    }
                usuarioViewModel_cadastro.usuario = usuario

                Toast.makeText(activity!!.baseContext,"Cadastro Realizado",Toast.LENGTH_SHORT).show()

                object : CountDownTimer(1000,250){
                    override fun onFinish() {

                        findNavController().navigate(R.id.action_cadastroFragment_to_loginFragment)

                    }
                    override fun onTick(millisUntilFinished: Long) {
                    }

                }.start()


            }


        }
    }

    fun Verificar_Email(email : String) : Boolean{
        return Patterns.EMAIL_ADDRESS.toRegex().matches(email)
    }


    fun Verificar_Dados_Invalidos(){
        //          Torna as animacoes invisiveis quando apertado o botao, como se estivessem sendo reiniciadas

        anim_edt_usuario_cadastro.isVisible = false
        anim_edt_senha_cadastro.isVisible = false
        anim_edt_confirmarSenha_cadastro.isVisible = false
        anim_edt_email_cadastro.isVisible = false

//          Os quatro ifs a seguir sao para ativar as animacoes de alerta caso os dados preenchidos sejam invalidos

        if(!Verificar_string(edt_usuario_cadastro.text.toString())){
            anim_edt_usuario_cadastro.playAnimation()
            anim_edt_usuario_cadastro.isVisible = true
        }

        if(!Verificar_string(edt_senha_cadastro.text.toString())){
            anim_edt_senha_cadastro.playAnimation()
            anim_edt_senha_cadastro.isVisible = true
        }

        if(edt_confirmaSenha_cadastro.text.toString() != edt_senha_cadastro.text.toString()){
            anim_edt_confirmarSenha_cadastro.playAnimation()
            anim_edt_confirmarSenha_cadastro.isVisible = true
        }

        if(!Verificar_Email(edt_email_cadastro.text.toString())){
            anim_edt_email_cadastro.playAnimation()
            anim_edt_email_cadastro.isVisible = true
        }
    }

//  Verifica se a palavra atende aos requisitos do cadastro
    fun Verificar_string(palavra : String) : Boolean{

        return !(palavra.contains(";")||palavra.contains(".")||
                palavra.contains(",")|| palavra.contains(" ")||
                palavra.contains("!")|| palavra.contains("?")||
                palavra.contains("/")|| !((palavra.length>=4) && (palavra.length <=18))
                )
    }


}
