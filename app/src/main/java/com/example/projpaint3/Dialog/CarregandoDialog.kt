package com.example.projpaint3.Dialog

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import com.example.projpaint3.R

class CarregandoDialog {

    lateinit var activity : Activity
    lateinit var dialog : AlertDialog

    constructor(Mactivity: Activity){
        activity = Mactivity
    }

    fun startLoadingDialog(){

        var builder = AlertDialog.Builder(activity)
        var inflater = activity.layoutInflater
        builder.setView(inflater.inflate(R.layout.carregando_dialog,null))
        builder.setCancelable(false)

        dialog = builder.create()
        dialog.show()

    }

    fun dismissDialog(){
        dialog.dismiss()
    }

}