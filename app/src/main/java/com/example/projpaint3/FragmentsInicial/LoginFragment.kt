package com.example.projpaint3.FragmentsInicial

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.annotation.MainThread
import androidx.core.view.isInvisible
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.example.projpaint3.API.ApiClient
import com.example.projpaint3.API.ApiClient.getClimaService
import com.example.projpaint3.Main2Activity
import com.example.projpaint3.Model.Clima
import com.example.projpaint3.Model.Usuario

import com.example.projpaint3.R
import com.example.projpaint3.Repositorio.Repos
import com.example.projpaint3.ViewModel.UsuarioViewModel
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_login.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.FileOutputStream
import java.lang.Exception

/**
 * A simple [Fragment] subclass.
 */
class LoginFragment : Fragment() {

    private lateinit var usuarioViewModel: UsuarioViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


//        getClimaService().getRJ(
//        ).enqueue(
//            object : Callback<Clima> {
//                override fun onFailure(call: Call<Clima>, t: Throwable) {
//                    Toast.makeText(this@LoginFragment.activity!!.applicationContext,"Fail",Toast.LENGTH_LONG).show()
//                }
//
//                override fun onResponse(call: Call<Clima>, response: Response<Clima>)
//                {
//                    val clima = response.body()
//                    Toast.makeText(this@LoginFragment.activity!!.applicationContext,clima?.results!!.temp.toString(),Toast.LENGTH_LONG).show()
//                }
//            })




        anim_usuarioInvalido_login.isInvisible = true

        try {
            edt_usuario_login.setText(LerArquivoEmailSenha()[0])
            edt_senha_login.setText(LerArquivoEmailSenha()[1])
        }
        catch (e : Exception){
        }


        activity?.let{
            usuarioViewModel = ViewModelProviders.of(it).get(UsuarioViewModel::class.java)
        }

        try{
            edt_usuario_login.setText(usuarioViewModel.usuario!!.email.toString())
            edt_senha_login.setText(usuarioViewModel.usuario!!.senha.toString())
        }
        catch(e : Exception){
        }





        txt_cadastrar_login.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_cadastroFragment)
        }
            btn_acessar.setOnClickListener {

                if(!edt_usuario_login.text.isNullOrEmpty() && !edt_senha_login.text.isNullOrEmpty()){
                    var firebaseAuth = FirebaseAuth.getInstance()
                    firebaseAuth.signInWithEmailAndPassword(edt_usuario_login.text.toString(),edt_senha_login.text.toString())
                        .addOnSuccessListener {

                            RegistarLogin(
                                edt_usuario_login.text.toString(),
                                edt_senha_login.text.toString()
                            )

                            Usuario_Valido_Login()



                        }
                        .addOnFailureListener{

                            Usuario_Invalido_Login()
                        }
                }
                else{

                    Usuario_Invalido_Login()

                }

            }



        //btn_acessar.setOnClickListener {

//            try {
          //      var nome = usuarioViewModel.usuario!!.email.toString()
          //      var senha = usuarioViewModel.usuario!!.senha.toString()

                //if(edt_usuario_login.text.toString() == nome && edt_senha_login.text.toString() == senha){
//                var firebaseAuth = FirebaseAuth.getInstance()
//                firebaseAuth.signInWithEmailAndPassword(edt_usuario_login.text.toString(),edt_senha_login.text.toString())
//                    .addOnSuccessListener {

//                        startActivity(Intent(context,Main2Activity::class.java))
                       // Usuario_Valido_Login()

//                }
//                    .addOnFailureListener{
//                        Toast.makeText(context,it.message,Toast.LENGTH_SHORT).show()
//                  //  Usuario_Invalido_Login()
//                }
//            }
//            catch(e : Exception) {
//                Usuario_Invalido_Login()
//            }

        //}
    }

    fun Usuario_Valido_Login(){

        txt_usuario_invalido_login.isInvisible = true
        anim_usuarioInvalido_login.isInvisible = true

        anim_edt_usuario_login.playAnimation()

        object : CountDownTimer(500,250){
            override fun onFinish() {
                anim_edt_senha_login.playAnimation()
            }
            override fun onTick(millisUntilFinished: Long) {
            }

        }.start()

        object : CountDownTimer(2000,250){
            override fun onFinish() {
                activity?.let{
                    var intent = Intent(it, Main2Activity::class.java)

//                    var usuario = Usuario(
//
//                        usuarioViewModel.usuario!!.nome.toString(),
//                        usuarioViewModel.usuario!!.senha.toString(),
//                        usuarioViewModel.usuario!!.email.toString())
//
//                    intent.putExtra("usuario", usuario.toString())
                    startActivity(intent)

                    activity.let {
                        it!!.finish()
                    }

                }
            }
            override fun onTick(millisUntilFinished: Long) {
            }

        }.start()


    }

    fun Usuario_Invalido_Login(){

        val anim1 = AnimationUtils.loadAnimation(this.context,R.anim.anim_alpha_txt)
        txt_usuario_invalido_login.isInvisible = false
        anim_usuarioInvalido_login.isInvisible = false
        txt_usuario_invalido_login.startAnimation(anim1)
        anim_usuarioInvalido_login.playAnimation()

    }

    fun RegistarLogin(email : String, senha : String){

        var lista : List<String> = listOf("","")

        try {
            var lista = LerArquivoEmailSenha()
        }
        catch (e : Exception){
        }

        if (email != lista[0] || senha != lista[1]){
            EscreverArquivoEmailSenha(email, senha)
        }

    }

    fun EscreverArquivoEmailSenha(email : String, senha : String){

        activity.let {
            it!!.openFileOutput("emailSenha.txt",Context.MODE_APPEND)
                .bufferedWriter().use {
                    it.appendln("${email},${senha}")
                }
        }
    }

    fun LerArquivoEmailSenha() : List<String>{

        var emailSenha : String = ""

        activity.let {
            it!!.openFileInput("emailSenha.txt")
                .bufferedReader().use {
                    emailSenha = it.readLines().last()
                }
        }

        val lista = emailSenha.split(",")

        return lista
    }
}
