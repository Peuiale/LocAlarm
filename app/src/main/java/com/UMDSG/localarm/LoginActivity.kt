package com.UMDSG.localarm

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)

        val btnIniciar : Button =findViewById(R.id.btn_iniciar_sesion)


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val btnRegistro : Button =findViewById(R.id.btn_registro)
        btnRegistro.setOnClickListener {
            val intent = Intent(this,RegistroActivity::class.java)
            startActivity(intent)
        }
    }

    fun iniciarSesion(view: View) {

        val etUsuario : EditText = findViewById(R.id.et_user_add)
        val etPass : EditText = findViewById(R.id.et_pass_add)

        val admin = BaseDatosApp(this,"bd",null,1)
        val bd =admin.writableDatabase

        val fila =bd.rawQuery("SELECT NOMBRE, PASSWORD FROM Usuarios WHERE NOMBRE='${etUsuario.text.toString()}' AND PASSWORD = '${etPass.text.toString()}'",null)

        var user = ""
        var pass = ""

        if(fila.moveToFirst()){
            user = fila.getString(0)
            pass = fila.getString(1)
        }
        if (etUsuario.text.toString() == user){
            if (etPass.text.toString() == pass ){
                val intent = Intent(this, AlarmasActivity::class.java)
                startActivity(intent)
            }
        }else{
            Toast.makeText(this, "Usuario o Contraseña Incorrecta", Toast.LENGTH_LONG).show()
        }

    }
}