package com.UMDSG.localarm

import android.annotation.SuppressLint
import android.content.ContentValues
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class RegistroActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_registro)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val etusuario : EditText =findViewById(R.id.et_user_add)
        val etpass1 : EditText =findViewById(R.id.et_pass_add)
        val etpass2 : EditText =findViewById(R.id.et_pass_confirm)
        val btnadduser : Button =findViewById(R.id.btn_adduser)

        btnadduser.setOnClickListener {
            val admin =BaseDatosApp(this,"bd", null, 1)
            val bd = admin.writableDatabase
            val reg = ContentValues()


            reg.put("NOMBRE",etusuario.text.toString())
            if(etpass1.text.toString() == etpass2.text.toString()){
6
                reg.put("PASSWORD", etpass1.text.toString())
                bd.insert("Usuarios", null,reg)
                bd.close()

                etusuario.setText("")
                etpass1.setText("")
                etpass2.setText("")

                Toast.makeText(this, "Se ha agregado el usuario correctamente", Toast.LENGTH_LONG).show()
            }else{
                Toast.makeText(this, "La contraseña no coincide", Toast.LENGTH_LONG).show()
            }




        }
    }
}