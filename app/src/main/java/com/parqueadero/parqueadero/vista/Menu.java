package com.parqueadero.parqueadero.vista;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.parqueadero.parqueadero.R;
import com.parqueadero.parqueadero.modelo.Registro;

import java.util.List;

public class Menu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        int motos = Registro.find(Registro.class, " tipo = ? and fecha_Salida = ?", "MOTO","").size();
        TextView txvMotos = (TextView)findViewById(R.id.txvMoto);
        txvMotos.setText("Motos sin salida:"+motos);

        int carros = Registro.find(Registro.class, " tipo = ? and fecha_Salida = ?", "CARRO","").size();
        TextView txvcarro = (TextView)findViewById(R.id.txvCarro);
        txvcarro .setText("Carros sin salida:"+carros);
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
