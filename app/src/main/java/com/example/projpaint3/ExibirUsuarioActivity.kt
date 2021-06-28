package com.example.projpaint3

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.projpaint3.Model.Usuario
import com.example.projpaint3.Repositorio.Repos
import kotlinx.android.synthetic.main.activity_exibir_usuario.*

class ExibirUsuarioActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exibir_usuario)

        var usuario = intent.getSerializableExtra("usuario") as Usuario

        ft_perfil_exibir_usuario.setImageResource(Repos().iconeIdToResource(usuario.icone))
        nome_txt_perfil_exibir_usuario.setText(usuario.nome)
        txt_localidade_perfil_exibir_usuario.setText(usuario.localidade)
        partidas_totais_txt_perfil_exibir_usuario.setText(usuario.partidas_totais().toString())
        partidas_ganhas_txt_perfil2_exibir_usuario.setText(usuario.partidas_ganhas.toString())
        desempenho_txt_perfil2_exibir_usuario.setText(usuario.desempenho().toString())
        txt_sobre_perfil_exibir_usuario.setText(usuario.sobre)
        txt_nivel_exibir_usuario.setText(Repos().nivel_usuario_toString(usuario.nivel))
        txt_telefone_exibir_usuario.setText(usuario.telefone)

        btn_voltar_exibir_usuario.setOnClickListener {
            var intent = Intent(this,Main2Activity::class.java)
            startActivity(intent)
        }


    }
}
