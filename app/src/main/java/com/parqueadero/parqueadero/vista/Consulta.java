package com.parqueadero.parqueadero.vista;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.parqueadero.parqueadero.R;
import com.parqueadero.parqueadero.modelo.Registro;

import java.util.Date;
import java.util.List;

public class Consulta extends AppCompatActivity {

    ListView listView;
    TextInputEditText edtPlaca;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta);
        edtPlaca = (TextInputEditText) findViewById(R.id.EdtPlacaC);
        listView = (ListView) findViewById(R.id.ltvConsulta);
        /*List<Registro> registros = Registro.listAll(Registro.class);
        AdaptadorRegistros adaptadorRegistros = new AdaptadorRegistros(this,Registro.listAll(Registro.class));
        listView.setAdapter(adaptadorRegistros);*/
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent().setClass(this,Menu.class);
        startActivity(intent);
        finish();
    }

    public void click(View view) {
        try {
            List<Registro> registros = null;
            if(isEmpty(edtPlaca)) {
                registros = Registro.listAll(Registro.class);
            }else{
                if(validarCampos()) {
                    registros = Registro.find(Registro.class, "placa = ?", edtPlaca.getText().toString().toUpperCase());
                }
            }
            if(registros != null) {
                AdaptadorRegistros adaptadorRegistros = new AdaptadorRegistros(this, registros);
                listView.setAdapter(adaptadorRegistros);
            }else{
                Toast.makeText(this, "No se encontro ningun registro", Toast.LENGTH_LONG).show();
            }

        }catch (Exception e){

        }
    }

    public boolean validarCampos() {
        if (!edtPlaca.getText().toString().matches("[a-zA-Z]{3}[0-9]{3}")) {
            Toast.makeText(this, "Placa incorrecta", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    private boolean isEmpty(TextInputEditText edtText) {
        if (edtText.getText().toString().trim().length() > 0)
            return false;

        return true;
    }
}
