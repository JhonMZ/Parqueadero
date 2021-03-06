package com.parqueadero.parqueadero.vista;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.parqueadero.parqueadero.R;
import com.parqueadero.parqueadero.modelo.Registro;

public class Menu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
    }

    public void click(View view) {
        Intent intent;
        switch (view.getId()){
            case R.id.btnIngreso:
                intent = new Intent().setClass(this,Ingreso.class);
                startActivity(intent);
                finish();
                break;
            case R.id.btnConsulta:
                intent = new Intent().setClass(this,Consulta.class);
                startActivity(intent);
                finish();
                break;
            case R.id.btnSalida:
                intent = new Intent().setClass(this,Salida.class);
                startActivity(intent);
                finish();
                break;
            default:
                break;
        }
    }
}
