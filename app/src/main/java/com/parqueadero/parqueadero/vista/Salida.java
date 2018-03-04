package com.parqueadero.parqueadero.vista;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.parqueadero.parqueadero.R;
import com.parqueadero.parqueadero.modelo.Registro;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Salida extends AppCompatActivity {

    TextInputEditText edtPlaca;
    TextInputEditText edtNombre;
    TextInputEditText edtIdentificacion;
    TextInputEditText edtTelefono;
    TextInputEditText edtFechaIngreso;
    TextInputEditText edtFechaSalida;
    TextInputEditText edtObservacion;
    TextInputEditText edtTotal;
    long idRegistro = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_salida);
        edtPlaca = (TextInputEditText) findViewById(R.id.EdtPlaca);
        edtNombre = (TextInputEditText) findViewById(R.id.EdtNombre);
        edtIdentificacion = (TextInputEditText) findViewById(R.id.EdtIdentificacion);
        edtTelefono = (TextInputEditText) findViewById(R.id.EdtTelefono);
        edtFechaIngreso = (TextInputEditText) findViewById(R.id.EdtFechaIngreso);
        edtFechaSalida = (TextInputEditText) findViewById(R.id.EdtFechaSalida);
        edtObservacion = (TextInputEditText) findViewById(R.id.EdtObservaciones);
        edtTotal = (TextInputEditText) findViewById(R.id.EdtTotal);
    }

    public void btnIngresarSalida(View view) {
        if(validarCampos()){
            if(idRegistro!=0) {
                Date fecha = new Date();
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:SS");
                Registro objRegistro = new Registro(edtPlaca.getText().toString().toUpperCase(),
                        edtIdentificacion.getText().toString(),
                        edtNombre.getText().toString(),
                        edtTelefono.getText().toString(),
                        edtObservacion.getText().toString(),
                        format.format(fecha), "",Long.parseLong(edtTotal.getText().toString()));
                objRegistro.setId(idRegistro);
                objRegistro.update();
                Toast.makeText(this, "Se ingreso correctamente", Toast.LENGTH_LONG).show();
            }else {
                Toast.makeText(this, "Ingrese una placa", Toast.LENGTH_LONG).show();
            }
        }
    }

    public boolean validarCampos(){
        if(isEmpty(edtPlaca)) {
            Toast.makeText(this,"Campo Placa Vacio",Toast.LENGTH_LONG).show();
            return false;
        }else{
            if(!edtPlaca.getText().toString().matches("([a-zA-Z]{3}[0-9]{3}$)|([a-zA-Z]{3}[0-9]{2}[a-zA-Z]{1}$)")){
                Toast.makeText(this,"Placa incorrecta",Toast.LENGTH_LONG).show();
                return false;
            }
        }

        if(isEmpty(edtNombre)){
            Toast.makeText(this,"Campo Nombre Vacio",Toast.LENGTH_LONG).show();
            return false;
        }

        if(isEmpty(edtIdentificacion)){
            Toast.makeText(this,"Campo Identificacion Vacio",Toast.LENGTH_LONG).show();
            return false;
        }

        if(isEmpty(edtTelefono)) {
            Toast.makeText(this, "Campo Telefono Vacio", Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }

    private boolean isEmpty(TextInputEditText edtText) {
        if (edtText.getText().toString().trim().length() > 0)
            return false;

        return true;
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent().setClass(this,Menu.class);
        startActivity(intent);
        finish();
    }

    public void btnBusar(View view) {
        try {
            List<Registro> registros = null;
            if(isEmpty(edtPlaca)) {
                Toast.makeText(this, "Ingrese una placa", Toast.LENGTH_LONG).show();
            }else{
                if(validarPlaca()) {
                    registros = Registro.find(Registro.class, "placa = ? and fecha_Salida = ?", edtPlaca.getText().toString().toUpperCase(),"");
                }
            }
            if(registros == null) {
                Toast.makeText(this, "No se encontro ningun registro", Toast.LENGTH_LONG).show();
            }else{
                if(registros.size()==0){
                    Toast.makeText(this, "No se encontro ningun registro", Toast.LENGTH_LONG).show();
                }else {
                    llenarCampos(registros);
                }
            }

        }catch (Exception e){
            Log.e("Error",e.getMessage());
        }
    }

    private boolean validarPlaca() {
        if(isEmpty(edtPlaca)) {
            Toast.makeText(this,"Campo Placa Vacio",Toast.LENGTH_LONG).show();
            return false;
        }else{
            if(!edtPlaca.getText().toString().matches("([a-zA-Z]{3}[0-9]{3}$)|([a-zA-Z]{3}[0-9]{2}[a-zA-Z]{1}$)")){
                Toast.makeText(this,"Placa incorrecta",Toast.LENGTH_LONG).show();
                return false;
            }
        }
        return true;
    }

    private void llenarCampos(List<Registro> registros) {
        edtPlaca.setText(registros.get(0).getPlaca());
        edtNombre.setText(registros.get(0).getNombre());
        edtIdentificacion.setText(registros.get(0).getIdentificacion());
        edtTelefono.setText(registros.get(0).getTelefono());
        edtFechaIngreso.setText(registros.get(0).getFechaIngreso());
        edtObservacion.setText(registros.get(0).getObservacion());
        idRegistro = registros.get(0).getId();
    }
}
